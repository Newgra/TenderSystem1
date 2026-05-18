package com.example.tendersystem.model;

public class TenderProposal {
    private int id;
    private int tenderId;
    private int executorId;
    private double price;
    private String description;

    public TenderProposal(){}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getTenderId() { return tenderId; }
    public void setTenderId(int tenderId) { this.tenderId = tenderId; }

    public int getExecutorId() { return executorId; }
    public void setExecutorId(int executorId) { this.executorId = executorId; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

}
