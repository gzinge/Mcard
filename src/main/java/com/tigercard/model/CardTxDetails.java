package com.tigercard.model;

import com.tigercard.utils.DateUtils;

import java.util.Objects;

public class CardTxDetails implements Comparable{
    private long id;
    private String date;
    private String time;
    private Station from;
    private Station to;
    private double fare;
    private String description;
    private String cardNumber;
    private boolean isSameZone;

    public CardTxDetails(long id,String day, String time, Station from, Station to, double fare, String description,String cardNumber) {
        this.id = id;
        this.date = day;
        this.time = time;
        this.from = from;
        this.to = to;
        this.fare = fare;
        this.description = description;
        this.cardNumber = cardNumber;
        this.isSameZone = from.getZone().equals(to.getZone());
    }

    public long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public Station getFrom() {
        return from;
    }

    public Station getTo() {
        return to;
    }

    public double getFare() {
        return fare;
    }

    public String getDescription() {
        return description;
    }

    public String getCardNumber() { return  cardNumber; }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSameZone() {
        return isSameZone;
    }

    public void updateFare(double fare){
        this.fare = fare;
    }

    @Override
    public String toString() {
        StringBuilder build = new StringBuilder();


        build.append("| "+String.format("%-5s", cardNumber)+" | "+
                String.format("%-10s",DateUtils.getDay(date))+" | "+
                String.format("%-10s",time) + "| "+
                String.format("%-5s",from.getZone().name())+"| "+
                String.format("%-5s",to.getZone().name())+"| "+
                String.format("%-5s",fare)+"| " +
                String.format("%-72s",description)+"|");
//        build.append("\n|------------------------------------------------------------------------------------------------------------------------------------|");
        return build.toString();
    }

    @Override
    public int compareTo(Object o) {
        CardTxDetails cardTxDetails = (CardTxDetails) o;
        return cardNumber.compareTo(cardTxDetails.cardNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardTxDetails that = (CardTxDetails) o;
        return id == that.id && Double.compare(that.fare, fare) == 0 && isSameZone == that.isSameZone && Objects.equals(date, that.date) && Objects.equals(time, that.time) && from == that.from && to == that.to && Objects.equals(description, that.description) && Objects.equals(cardNumber, that.cardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, time, from, to, fare, description, cardNumber, isSameZone);
    }
}