package com.example.bookingapp.data.model;

import java.util.Date;

public class Bill {
    private int id;
    private String flightBillId;
    private String hotelBillId;
    private String placeBillId;
    private int userId;
    private float totalPrice;
    private Date date;

    public Bill(){}
    public Bill(Date date, String flightBillId, String hotelBillId, String placeBillId, float totalPrice, int userId) {
        this.date = date;
        this.flightBillId = flightBillId;
        this.hotelBillId = hotelBillId;
        this.placeBillId = placeBillId;
        this.totalPrice = totalPrice;
        this.userId = userId;
    }

    public Bill(Date date, String flightBillId, String hotelBillId, int id, String placeBillId, float totalPrice, int userId) {
        this.date = date;
        this.flightBillId = flightBillId;
        this.hotelBillId = hotelBillId;
        this.id = id;
        this.placeBillId = placeBillId;
        this.totalPrice = totalPrice;
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFlightBillId() {
        return flightBillId;
    }

    public void setFlightBillId(String flightBillId) {
        this.flightBillId = flightBillId;
    }

    public String getHotelBillId() {
        return hotelBillId;
    }

    public void setHotelBillId(String hotelBillId) {
        this.hotelBillId = hotelBillId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaceBillId() {
        return placeBillId;
    }

    public void setPlaceBillId(String placeBillId) {
        this.placeBillId = placeBillId;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "date=" + date +
                ", id='" + id + '\'' +
                ", flightBillId='" + flightBillId + '\'' +
                ", hotelBillId='" + hotelBillId + '\'' +
                ", placeBillId='" + placeBillId + '\'' +
                ", userId='" + userId + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
