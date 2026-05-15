package com.example.tendersystem.model;

import java.util.List;

public class Tender {
    private int id;
    private String name;
    private String description;
    private List<String> categories;
    private String status;
    private String ownerName;

    public Tender(){}

    public Tender(int id, String name, String description, List<String> categories, String status, String ownerName){
        this.id = id;
        this.name = name;
        this.description = description;
        this.categories = categories;
        this.status = status;
        this.ownerName = ownerName;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getCategories() { return categories; }
    public void setCategories(List<String> categories) { this.categories = categories; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
}