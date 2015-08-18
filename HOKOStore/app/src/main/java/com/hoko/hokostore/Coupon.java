package com.hoko.hokostore;

/**
 * Created by Pedro Vieira on 22/07/15.
 * Copyright © 2015 HOKO. All rights reserved.
 */
public class Coupon {
    private String mName;
    private double mValue;

    public Coupon(String name, double value) {
        mName = name;
        mValue = value;
    }

    public String getName() {
        return mName;
    }

    public double getValue() {
        return mValue;
    }
}
