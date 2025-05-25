package com.example.stockhive;

public class Product {
    private String name;
    private String description;
    private double price;
    private Image image;
    public Product(String name, String description, double price, Image image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }
    public void update(String name, String description, double price, Image image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }
    public String getName() {
        return this.name;
    }
    public String getDescription() {
        return this.description;
    }
    public double getPrice() {
        return this.price;
    }
    public Image getImage() {
        return this.image;
    }
}
