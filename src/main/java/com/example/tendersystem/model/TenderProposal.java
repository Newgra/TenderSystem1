package com.example.tendersystem.model;

public class TenderProposal {
    private int id;
    private int tenderId;
    private int userId;
    private double price;
    private String description;

    public TenderProposal(){}

    public TenderProposal(int id, int tenderId, int userId, double price, String description){
        this.id = id;
        this.tenderId = tenderId;
        this.userId = userId;
        this.price = price;
        this.description = description;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getTenderId() { return tenderId; }
    public void setTenderId(int tenderId) { this.tenderId = tenderId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

}
