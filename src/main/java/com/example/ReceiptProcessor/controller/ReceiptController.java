package com.example.ReceiptProcessor.controller;

import com.example.ReceiptProcessor.models.Receipt;
import com.example.ReceiptProcessor.models.ResponseID;
import com.example.ReceiptProcessor.models.ResponsePoints;
import com.example.ReceiptProcessor.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;

    @PostMapping("/process")
    public ResponseEntity<ResponseID> processReceipt(@RequestBody Receipt receipt) {
        Receipt newReceipt = receipt;

        String  uuid = receiptService.saveReceipt(newReceipt);

        // Create a ResponseID object with the UUID
        ResponseID responseID = new ResponseID();
        responseID.setId(uuid);

        return new ResponseEntity<>(responseID, HttpStatus.OK);
    }

    @GetMapping("/{id}/points")
    public ResponseEntity<?> getRewardPoints(@PathVariable String id) {
        try {
            int points = receiptService.getRewardPoints(id);

            ResponsePoints responsePoints = new ResponsePoints();
            responsePoints.setPoints(points);

            return new ResponseEntity<>(responsePoints, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("UUID not found: " + id);
        }
    }
}
