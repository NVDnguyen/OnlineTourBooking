package com.example.bookingapp.data.model;

import java.util.Date;

public class FlightBill {
    private int id;
    private float price;
    private Date arrivalTime;
    private Date departureTime;
    private int idFlight;
    private int ticketNumber;


    public FlightBill() {
    }

    public FlightBill(Date arrivalTime, Date departureTime, int id, int idFlight, float price, int ticketNumber) {
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.id = id;
        this.idFlight = idFlight;
        this.price = price;
        this.ticketNumber = ticketNumber;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getIdFlight() {
        return idFlight;
    }

    public void setIdFlight(int idFlight) {
        this.idFlight = idFlight;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }
}
