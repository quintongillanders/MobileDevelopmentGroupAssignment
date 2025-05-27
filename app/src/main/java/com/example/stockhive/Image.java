package com.example.stockhive;

import java.io.Serializable;

public class Image implements Serializable {
    public final int src;

    public Image(int src) {
        this.src = src;
    }
}
