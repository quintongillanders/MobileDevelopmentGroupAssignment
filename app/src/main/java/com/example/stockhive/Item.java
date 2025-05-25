package com.example.stockhive;

import java.util.List;

public class Item {

    String productname;
    String description;
    int image;
    double price;



    private String itemname;

    private int itemquantity;

    private String id;


    public Item(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Item() {
    }

    public Item(String itemname, int itemquantity) {
        this.itemname = itemname;
        this.itemquantity = itemquantity;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public int getItemquantity() {
        return itemquantity;
    }

    public void setItemquantity(int itemquantity) {
        this.itemquantity = itemquantity;
    }

    public Item(String productname, String description, int image, double price)
    {
        this.productname = productname;
        this.description = description;
        this.image = image;
        this.price = price;

    }




    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
