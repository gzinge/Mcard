package com.tigercard.model;

import com.tigercard.strategy.enums.FareZoneEnum;
import com.tigercard.utils.DateUtils;

import java.util.Objects;

public class TransactionDayReportBean {
    private double fare;
    private String day;
    private boolean isSame;
    private FareZoneEnum fareZoneEnum;
    private String message;

    public TransactionDayReportBean(double fare, String day, boolean isSame, FareZoneEnum fareZoneEnum) {
        this.fare = fare;
        this.day = day;
        this.isSame = isSame;
        this.fareZoneEnum =fareZoneEnum;
    }

    public double getFare() {
        return fare;
    }

    public String getDay() {
        return day;
    }

    public boolean isSame() {
        return isSame;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public FareZoneEnum getFareZoneEnum() {
        return fareZoneEnum;
    }

    public void updateFare(double fare){
        this.fare=fare;
    }


    @Override
    public String toString() {

        StringBuilder build = new StringBuilder();
        build.append("| "+String.format("%-10s",DateUtils.getDay(day))+  "| "+
                String.format("%-12s",getZoneName()) +"| "+
                        String.format("%-5s",((fare == 0) ? "00.00" : fare)) +"| "+
                        String.format("%-72s",message)+"|");
        //build.append("\n|------------------------------------------------------------------------------------------------------------------------------------|");
        return build.toString();
    }

    public String getZoneName() {
        return isSame ? (fareZoneEnum + " - " + fareZoneEnum) :
                fareZoneEnum.equals(FareZoneEnum.EAST) ? (FareZoneEnum.EAST + " - " + FareZoneEnum.WEST) :
                        (FareZoneEnum.WEST + " - " + FareZoneEnum.EAST);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionDayReportBean that = (TransactionDayReportBean) o;
        boolean isEqual= Double.compare(that.fare, fare) == 0
                && Objects.equals(day, that.day)
                && fareZoneEnum == that.fareZoneEnum;

        return isEqual;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fare, day, isSame, fareZoneEnum);
    }
}
