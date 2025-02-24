package com.example.bookingapp.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.maps.model.LatLng;

public class LocationManager {
    private final FusedLocationProviderClient fusedLocationClient;

    public LocationManager(Context context) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    public void getCurrentLocation(Context context, OnSuccessListener<LatLng> callback) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Location permission required", Toast.LENGTH_SHORT).show();
            return;
        }

        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        callback.onSuccess(new LatLng(location.getLatitude(), location.getLongitude()));
                    } else {
                        Toast.makeText(context, "Unable to get current location", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
