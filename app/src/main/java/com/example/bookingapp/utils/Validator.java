package com.example.bookingapp.utils;

import com.example.bookingapp.data.model.*;
import java.util.Date;
import java.util.regex.Pattern;

public class Validator {

    // Regex pattern for validating latitude and longitude format "40.712776,-74.005974"
    private static final String LOCATION_REGEX = "^-?\\d{1,3}\\.\\d+,-?\\d{1,3}\\.\\d+$";
    private static final Pattern LOCATION_PATTERN = Pattern.compile(LOCATION_REGEX);

    // Regex pattern for email validation "john.doe@email.com"
    private static final String EMAIL_REGEX = "^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    // Validate User
    public static boolean validateUser(User user) {
        return user != null &&
                isValidId(user.getId()) &&
                isValidEmail(user.getEmail()) &&
                !isEmpty(user.getName()) &&
                !isEmpty(user.getPassword()) &&
                user.getPassword().length() >= 6 &&
                (user.getLocation() == null || isValidLocation(user.getLocation()));
    }

    // Validate Flight
    public static boolean validateFlight(Flight flight) {
        return flight != null &&
                isValidId(flight.getId()) &&
                !isEmpty(flight.getName()) &&
                flight.getPrice() > 0 &&
                isValidLocation(flight.getDepartureLocation()) &&
                isValidLocation(flight.getDestinationLocation()) &&
                flight.getDepartureTime().before(flight.getArrivalTime());
    }

    // Validate FlightBill
    public static boolean validateFlightBill(FlightBill flightBill) {
        return flightBill != null &&
                isValidId(flightBill.getId()) &&
                isValidId(flightBill.getIdFlight()) &&
                flightBill.getPrice() > 0 &&
                flightBill.getTicketNumber() > 0 &&
                isValidDate(flightBill.getDepartureTime()) &&
                isValidDate(flightBill.getArrivalTime()) &&
                flightBill.getDepartureTime().before(flightBill.getArrivalTime()); // Departure must be before Arrival
    }

    // Validate Hotel
    public static boolean validateHotel(Hotel hotel) {
        return hotel != null &&
                isValidId(hotel.getId()) &&
                !isEmpty(hotel.getName()) &&
                isValidLocation(hotel.getLocation()) &&
                hotel.getPrice() > 0 &&
                hotel.getRate() >= 0 && hotel.getRate() <= 5;
    }

    // Validate HotelBill
    public static boolean validateHotelBill(HotelBill hotelBill) {
        return hotelBill != null &&
                isValidId(hotelBill.getId()) &&
                isValidId(hotelBill.getIdHotel()) &&
                hotelBill.getPrice() > 0 &&
                !isEmpty(hotelBill.getRoomType()) &&
                hotelBill.getTime() > 0 &&
                isValidDate(hotelBill.getDate());
    }

    // Validate Place
    public static boolean validatePlace(Place place) {
        return place != null &&
                isValidId(place.getId()) &&
                !isEmpty(place.getName()) &&
                isValidLocation(place.getLocation()) &&
                place.getPrice() >= 0; // Free places allowed
    }

    // Validate PlaceBill
    public static boolean validatePlaceBill(PlaceBill placeBill) {
        return placeBill != null &&
                isValidId(placeBill.getId()) &&
                isValidId(placeBill.getIdPlace()) &&
                placeBill.getPrice() > 0 &&
                placeBill.getTicketNumber() > 0;
    }

    // Validate Bill
    public static boolean validateBill(Bill bill) {
        return bill != null &&
                isValidId(bill.getId()) &&
                isValidId(bill.getUserId()) &&
                bill.getTotalPrice() >= 0 &&
                isValidDate(bill.getDate());
    }

    // Helper Functions
    public static boolean isValidId(int id) {
        return true;
    }

    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidLocation(String location) {
        return location != null && LOCATION_PATTERN.matcher(location).matches();
    }

    public static boolean isValidDate(Date date) {
        return date != null && date.after(new Date(0)); // Ensure date is valid (after epoch)
    }
}
