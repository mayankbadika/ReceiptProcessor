package com.example.ReceiptProcessor.config;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    //@Async("webSocketTaskExecutor")
    /*
        For @Async to work correctly, it needs to be invoked through a proxy (i.e., from a different thread or context),
        so @Async annotations inside the same class (or non-public methods) might not work as expected.
        You will need to ensure that handleTextMessage is being called indirectly, such as from a service or controller method.
     */
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Handle incoming messages if needed
        System.out.println("Webscoket thread = "+ Thread.currentThread().getName());
        System.out.println("websocket session = "+session);
        System.out.println("websocket message = "+  message.getPayload());

        // Broadcast the received message to all connected clients
        broadcast("Message from client: " + message.getPayload());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    @Async("webSocketTaskExecutor")
    public void broadcast(String message) {
        System.out.println("Webscoket thread = "+ Thread.currentThread().getName());
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (Exception e) {
                    e.printStackTrace(); // Replace with proper logging
                }
            }
        }
    }
}

