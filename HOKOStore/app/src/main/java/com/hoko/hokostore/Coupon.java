package com.hoko.hokostore;

/**
 * Created by pedrovieira on 22/07/15.
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
