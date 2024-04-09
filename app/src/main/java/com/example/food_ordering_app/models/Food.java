package com.example.food_ordering_app.models;

import java.util.Date;

public class Food {
    private String id;
    private String category;
    private double price;
    private String name;
    private String description;
    private Date timestamp;
    private String imgURL;

    public Food() {
    }

    public Food(String category, String name, String description, double price, String imgURL) {
        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgURL = imgURL;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id='" + id + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", timestamp=" + timestamp +
                ", imgURL='" + imgURL + '\'' +
                '}';
    }
}
