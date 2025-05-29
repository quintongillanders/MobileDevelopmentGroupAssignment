package com.example.stockhive;

import java.io.Serializable;

public class CartItem implements Serializable {

    private final Product product;
    private int quantity = 1;

    public CartItem(Product product) {
        this.product = product;
    }

    public String getName() {
        return product.getName();
    }
    public String getDescription() {
        return product.getDescription();
    }
    public double getPrice() {
        return product.getPrice();
    }
    public Image getImage() {
        return product.getImage();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
