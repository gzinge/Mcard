package com.tigercard.model;

public class ChargeAndDescriptonBean {
    private double fare;
    private String message;

    public ChargeAndDescriptonBean(double fare, String message) {
        this.fare = fare;
        this.message = message;
    }

    public double getFare() {
        return fare;
    }

    public String getMessage() {
        return message;
    }
}
