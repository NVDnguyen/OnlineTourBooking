package com.example.bookingapp.data.model;

import java.util.Date;

public class HotelBill {
    private int id;
    private Date date;
    private float price;
    private String roomType;
    private int time;
    private int idHotel;

    private int idUser;
    public HotelBill(){}
    public HotelBill( Date date, float price, String roomType, int time, int idHotel, int idUser) {
        this.date = date;
        this.price = price;
        this.roomType = roomType;
        this.time = time;
        this.idHotel = idHotel;
        this.idUser = idUser;
    }
    public HotelBill(int id, Date date, float price, String roomType, int time, int idHotel,int idUser) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.roomType = roomType;
        this.time = time;
        this.idHotel = idHotel;
        this.idUser = idUser;
    }
    public int getIdUser() {
        return idUser;
    }
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
    public Integer getIdHotel() {
        return idHotel;
    }
    public void setIdHotel(int idHotel) {
        this.idHotel = idHotel;
    }

    @Override
    public String toString() {
        return "HotelBill{" +
                "date=" + date +
                ", id='" + id + '\'' +
                ", price=" + price +
                ", roomType='" + roomType + '\'' +
                ", time=" + time +
                '}';
    }
}
