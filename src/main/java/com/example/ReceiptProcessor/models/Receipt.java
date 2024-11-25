package com.example.ReceiptProcessor.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class Receipt {
    private String retailer;
    private LocalDate purchaseDate;
    private LocalTime purchaseTime;

    private List<Items> items;

    private double total;

    public String getRetailer() {
        return retailer;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public LocalTime getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(LocalTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Same object
        if (o == null || getClass() != o.getClass()) return false; // Check for null or class mismatch
        Receipt receipt = (Receipt) o;
        return Objects.equals(retailer, receipt.retailer) &&
                Objects.equals(purchaseDate, receipt.purchaseDate) &&
                Objects.equals(purchaseTime, receipt.purchaseTime) &&
                Objects.equals(total, receipt.total); // Compare relevant fields
    }

    @Override
    public int hashCode() {
        return Objects.hash(retailer, purchaseDate, purchaseTime, total); // Generate hash based on the same fields
    }

    public boolean isEmpty() {
        return (retailer == null || retailer.isBlank()) &&
                purchaseDate == null &&
                purchaseTime == null &&
                (items == null || items.isEmpty()) &&
                (total == 0.0);
    }

}
