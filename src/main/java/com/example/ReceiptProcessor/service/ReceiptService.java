package com.example.ReceiptProcessor.service;

import com.example.ReceiptProcessor.models.Items;
import com.example.ReceiptProcessor.models.Receipt;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/*
    Assumptions
    We are treating each item separately even if the there are duplicates.
    1. so if we have 2 items for which description length is a multiple of 3,  we add the points for both the items.
    2. if we duplicate items we treat then as different, So we count them as 2.
*/

@Service
public class ReceiptService {
    private ConcurrentHashMap<String, Integer> pointsDB= new ConcurrentHashMap<>();
    // In-memory store for processed receipts
    private ConcurrentHashMap<Receipt, String> receiptStore = new ConcurrentHashMap<>();
    public String saveReceipt(Receipt receipt) {
        // Check if the receipt already exists
        if (receiptStore.containsKey(receipt)) {
            // Return the existing UUID
            return receiptStore.get(receipt);
        }

        // Generate a new UUID for the unique receipt
        String newUuid = UUID.randomUUID().toString();
        receiptStore.put(receipt, newUuid);

        calculatePoints(receipt, newUuid);

        return newUuid;
    }


    private void calculatePoints(Receipt receipt, String uuid) {
        int points = 0;

        //One point for every alphanumeric character in the retailer name.
        points += calculateAlphaNumericChar(receipt.getRetailer());

        //50 points if the total is a round dollar amount with no cents.
        if(receipt.getTotal() % 1 == 0) points += 50;

        //25 points if the total is a multiple of 0.25.
        if(receipt.getTotal()*4 % 1 == 0) points += 25;

        //5 points for every two items on the receipt.
        points += (5 * pointForItems(receipt.getItems())/2);

        //If the trimmed length of the item description is a multiple of 3,
        //multiply the price by 0.2 and round up to the nearest integer. The result is the number of points earned.
        points += pointsforItemDesc(receipt.getItems());

        //6 points if the day in the purchase date is odd.
        LocalDate purchaseDate = receipt.getPurchaseDate();
        if(purchaseDate.getDayOfMonth() %2 != 0) points += 6;

        //10 points if the time of purchase is after 2:00pm and before 4:00pm.
        LocalTime purchaseTime = receipt.getPurchaseTime();
        int hour = purchaseTime.getHour();

        if(hour >= 14 && hour < 16) points += 10;

        pointsDB.put(uuid, points);
    }

    public int getRewardPoints(String uuid) throws Exception {
        if (!pointsDB.containsKey(uuid)) {
            throw new Exception("UUID not found");
        }

        return pointsDB.get(uuid);
    }

    private int calculateAlphaNumericChar(String s) {
        int count = 0;
        for(char c : s.toCharArray()) {
            if(Character.isLetterOrDigit(c)) count++;
        }

        return count;
    }

    private int pointForItems(List<Items> items) {

        if (items.size() % 2 == 0) {
            return items.size();
        } else {
            // If size is odd, subtract the remainder when divided by 2
            return items.size() - items.size() % 2;
        }
    }

    private int pointsforItemDesc(List<Items> items) {
        int points = 0;

        for(Items item : items) {
            int length = item.getShortDescription().length();
            if(length % 3 == 0) {
                points += (int) Math.ceil(item.getPrice() * 0.2);
            }
        }

        return points;
    }
}
