package com.hoko.hokostore;


/**
 * Created by Pedro Vieira on 22/07/15.
 * Copyright © 2015 HOKO. All rights reserved.
 */
public class Product {
    private int mID;
    private String mTitle;
    private String mDescription;
    private int mImageResource;
    private float mPrice;

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
        mID = id;
        mTitle = title;
        mDescription = description;
        mImageResource = imageResource;
        mPrice = price;
    }

    public int getId() {
        return mID;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getImageResource() { return mImageResource; }

    public float getPrice() {
        return mPrice;
    }
}
