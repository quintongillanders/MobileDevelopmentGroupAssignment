package com.example.stockhive;

public class ProductCategory {
    private String name;
    private String description;
    private Image image;
    public ProductCategory(String name, String description, Image image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }
    public void update(String name, String description, Image image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public Image getImage() {
        return image;
    }
}
