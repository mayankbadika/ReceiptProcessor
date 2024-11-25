package com.example.ReceiptProcessor.models;

public class Items {
    private String shortDescription;
    private Double price;

    public String getShortDescription() {
        return shortDescription.trim();
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
