package com.example.bookingapp.utils;

import static com.example.bookingapp.utils.Constant.DATE_TIME_FORMAT;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.example.bookingapp.data.model.Bill;
import com.example.bookingapp.data.model.Flight;
import com.example.bookingapp.data.model.FlightBill;
import com.example.bookingapp.data.model.Hotel;
import com.example.bookingapp.data.model.HotelBill;
import com.example.bookingapp.data.model.Place;
import com.example.bookingapp.data.model.PlaceBill;
import com.example.bookingapp.data.model.User;
import com.example.bookingapp.data.repository.BillRepository;
import com.example.bookingapp.data.repository.FlightBillRepository;
import com.example.bookingapp.data.repository.FlightRepository;
import com.example.bookingapp.data.repository.HotelBillRepository;
import com.example.bookingapp.data.repository.HotelRepository;
import com.example.bookingapp.data.repository.PlaceBillRepository;
import com.example.bookingapp.data.repository.PlaceRepository;
import com.example.bookingapp.data.repository.UserRepository;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Tool {
    public static double calculateDistance(String location1, String location2){
        // Parse latitude and longitude from input strings
        String[] loc1 = location1.split(",");
        String[] loc2 = location2.split(",");

        double lat1 = Double.parseDouble(loc1[0].trim());
        double lon1 = Double.parseDouble(loc1[1].trim());
        double lat2 = Double.parseDouble(loc2[0].trim());
        double lon2 = Double.parseDouble(loc2[1].trim());

        // Radius of the Earth in kilometers
        final double R = 6371.0;

        // Convert degrees to radians
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        // Haversine formula
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Compute the distance
        return R * c; // Distance in kilometers
    }

    public static void insertSampleData(Context context) {
        UserRepository userRepository = new UserRepository(context);
        FlightRepository flightRepository = new FlightRepository(context);
        HotelRepository hotelRepository = new HotelRepository(context);
        PlaceRepository placeRepository = new PlaceRepository(context);

        FlightBillRepository flightBillRepository = new FlightBillRepository(context);
        HotelBillRepository hotelBillRepository = new HotelBillRepository(context);
        PlaceBillRepository placeBillRepository = new PlaceBillRepository(context);
        BillRepository billRepository = new BillRepository(context);

        // Generate realistic dates
        Date departureDate = new Date(System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000); // 2 days ahead
        Date arrivalDate = new Date(System.currentTimeMillis() + 6 * 24 * 60 * 60 * 1000); // 6 days ahead
        Date billDate = new Date(System.currentTimeMillis()); //now

//        // Insert Users
        //userRepository.createUser(new User("alice@gmail.com", "51.509726437623065, -0.11671313005671248", "Alice", "pass123"));

        // Insert Flights
        flightRepository.createFlight(new Flight(
                arrivalDate, "51.509726437623065, -0.11671313005671248", departureDate, "40.69077691676316, -73.99462245946953", "British Airways BA205", 450.0f));

        // Insert Hotels
        hotelRepository.createHotel(new Hotel(
                Collections.singletonList("https://www.ahstatic.com/photos/c096_ho_00_p_1024x768.jpg"), "40.76310234923443, -73.97465305662261", "Aman New York", 200.0f, 4.5f));

        // Insert Places
        placeRepository.createPlace(new Place(
                "Eiffel Tower", "The Eiffel Tower is a wrought-iron lattice tower on the Champ de Mars in Paris, France. It is named after the engineer Gustave Eiffel, whose company designed and built the tower from 1887 to 1889", "48.85838418882046, 2.2944491111660015", 25.0f,
                Collections.singletonList("https://i.postimg.cc/52xdFFbJ/c6.jpg"),4.5f,4));

        placeRepository.createPlace(new Place(
                "Museum of Modern Art", "Works from Van Gogh to Warhol, a garden with many statues, 2 cafes and a Modern restaurant.", "40.77246750424053, -73.97402309481457", 25.0f,
                Collections.singletonList("https://i.postimg.cc/d34X25qK/Museum-of-Modern-Art-in-Warsaw-01-B.jpg"),4.0f,2));

        placeRepository.createPlace(new Place(
                "Sapa", "Sa Pa is a district-level town of Lào Cai Province in the Northwest region of Vietnam. The town has an area of 685 km² and a population of 70,663 in 2022. The town capital lies at Sa Pa ward.", "22.36015195227598, 103.8267228946122", 25.0f,
                Collections.singletonList("https://i.postimg.cc/d0w2Fw24/lao-chai-y-linh-ho-village-900x473.webp"),4.2f,1));

//        // Insert Flight Bills
//        flightBillRepository.createFlightBill(new FlightBill(arrivalDate, departureDate, 1, 450.0f, 2,1));
//
//        // Insert Hotel Bills
//        hotelBillRepository.createHotelBill(new HotelBill(departureDate, 200.0f, "Deluxe", 3, 1,1));
//
//        // Insert Place Bills
//        placeBillRepository.createPlaceBill(new PlaceBill(25.0f, 3, 1,1));
//
//        // Insert Bills
//        billRepository.createBill(new Bill(billDate, "1", "1", "1", 450.0f * 2 + 200.0f * 3 + 25.0f * 3, 1));
    }
    public static String convertToAddress(Context context,String location) {
        if (location == null || !location.contains(",")) {
            return "Invalid Location";
        }
        try {
            // Split location string into latitude and longitude
            String[] parts = location.split(",");
            double latitude = Double.parseDouble(parts[0].trim());
            double longitude = Double.parseDouble(parts[1].trim());

            // Use Geocoder to fetch address
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);

                // Get city & country
                String city = address.getLocality();   // City name (e.g., New York)
                String country = address.getCountryCode();  // Country name (e.g., USA)

                // Return formatted location
                if (city != null && country != null) {
                    return city + ", " + country; // Example: "New York, USA"
                } else if (country != null) {
                    return country; // If city is missing, return only country
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return "Unknown Location";
    }

}
