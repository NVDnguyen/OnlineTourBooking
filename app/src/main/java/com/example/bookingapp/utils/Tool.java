package com.example.bookingapp.utils;

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
}
