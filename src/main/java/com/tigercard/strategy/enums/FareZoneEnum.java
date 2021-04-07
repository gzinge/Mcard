package com.tigercard.strategy.enums;

public enum FareZoneEnum {
    EAST(30.0),
    WEST(25.0),
    EAST_WEST_CROSS_ZONE(35.0),

    EAST_DAILY_CAP(100),
    WEST_DAILY_CAP(80.00),
    EAST_WEST_CROSS_ZONE_DAILY_CAP(120.00),

    EAST_WEEKLY_CAP(500.00),
    WEST_WEEKLY_CAP(400.00),
    EAST_WEST_CROSS_ZONE_WEEKLY_CAP(600.00);

    private double fare;
    FareZoneEnum(double fare)
    {
        this.fare = fare;
    }

    public double getFare() {
        return fare;
    }
}
