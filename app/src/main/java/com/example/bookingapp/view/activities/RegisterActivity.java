package com.example.bookingapp.view.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.bookingapp.R;
import com.example.bookingapp.contract.RegisterContract;
import com.example.bookingapp.data.repository.UserRepository;
import com.example.bookingapp.databinding.ActivityRegisterBinding;
import com.example.bookingapp.presenter.RegisterPresenter;
import com.example.bookingapp.utils.LocationManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View, OnMapReadyCallback {
    private ActivityRegisterBinding binding;
    private RegisterPresenter registerPresenter;
    private LocationManager locationManager;
    private GoogleMap mMap;
    private Marker currentMarker;
    private String selectedLocation = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize dependencies
        UserRepository userRepository = new UserRepository(this);
        registerPresenter = new RegisterPresenter(this, userRepository);
        locationManager = new LocationManager(this);

        // Register button click listener
        binding.registerButton.setOnClickListener(view -> {
            registerPresenter.register(
                    binding.email.getText().toString(),
                    binding.password.getText().toString(),
                    binding.userName.getText().toString(),
                    selectedLocation
            );
        });

        binding.loginLink.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        // Validate password match
        binding.confirmPassword.addTextChangedListener(passwordWatcher);

        // Load Google Map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Enable "My Location" button if permission is granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            getUserCurrentLocation();
        } else {
            requestLocationPermission();
        }

        // Handle map click for selecting a location
        mMap.setOnMapClickListener(this::updateMapLocation);
    }

    private void requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);
            getUserCurrentLocation();
        } else {
            Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void getUserCurrentLocation() {
        locationManager.getCurrentLocation(this, this::updateMapLocation);
    }

    private final TextWatcher passwordWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            validatePasswordMatch();
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    };

    private void validatePasswordMatch() {
        String password = binding.password.getText().toString();
        String confirmPassword = binding.confirmPassword.getText().toString();
        binding.textinputError.setVisibility(password.equals(confirmPassword) ? View.GONE : View.VISIBLE);
    }

    private void updateMapLocation(LatLng latLng) {
        if (currentMarker != null) {
            currentMarker.remove();
        }
        selectedLocation = latLng.latitude + "," + latLng.longitude;
        currentMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Selected Location"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    @Override
    public void onRegisterSuccess() {
        Toast.makeText(this, "Register successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void onRegisterError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
