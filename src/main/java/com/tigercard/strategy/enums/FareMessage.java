package com.tigercard.strategy.enums;

public enum FareMessage {
    PEAK_HOURS_SINGLE_FARE      ("Peak hours Single fare."),
    OFF_PEAK_SINGLE_FARE        ("Off-peak single fare."),
    DAILY_CAP_REACHED_CROSS_ZONE("The Daily cap reached %s for zone 1 - 2. Charged %s instead of %s."),

    DAILY_CAP_REACHED    ("Daily cap reached."),
    DAILY_CAP_NOT_REACHED("Daily cap not reached."),
    WEEKLY_CAP           ("A weekly cap of %s reached before the daily cap of %s."),
    WEEKLY_CAP_REACHED   ("A weekly cap of %s reached.");

    FareMessage(String message){
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    private String message;
}
