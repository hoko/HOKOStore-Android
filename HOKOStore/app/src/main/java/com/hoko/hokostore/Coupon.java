package com.hoko.hokostore;

/**
 * Created by Pedro Vieira on 22/07/15.
 * Copyright © 2015 HOKO. All rights reserved.
 */
public class Coupon {
    private String name;
    private double value;

    public Coupon(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }
}
