package com.hoko.hokostore;

import android.graphics.drawable.Drawable;

/**
 * Created by pedrovieira on 21/07/15.
 */

public class Product {
    private int id;
    private String title;
    private String description;
    private int imageResource;
    private float price;

    public static Product getProductWithId(int id) {
        switch (id) {
            case 0:
                return new Product(id, "Awesome Book", "The best book ever made, trust us.",
                        R.drawable.book, 52.99f);
            case 1:
                return new Product(id, "Shiny Boots",
                        "Who wouldn't want to use this shiny hand-made boots?",
                        R.drawable.boots, 29.99f);
            case 2:
                return new Product(id, "Phone Case",
                        "Feels like silk in your hands. Your phone will love it, we guarantee you.",
                        R.drawable.phone_case, 39.99f);
            default:
                return null;
        }
    }

    private Product(int id, String title, String description, int imageResource, float price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageResource = imageResource;
        this.price = price;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public int getImageResource() { return this.imageResource; }

    public float getPrice() {
        return this.price;
    }
}
