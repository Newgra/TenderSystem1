package com.example.tendersystem.model;

public class Tender {
    private int id;
    private String name;
    private String description;
    private String status;
    private int ownerId;
    private String ownerName;
    private int executorId;

    public Tender(){}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getOwnerId() { return ownerId; }
    public void setOwnerId(int ownerId) { this.ownerId = ownerId; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public int getExecutorId() { return executorId; }
    public void setExecutorId(int executorId) { this.executorId = executorId; }
}