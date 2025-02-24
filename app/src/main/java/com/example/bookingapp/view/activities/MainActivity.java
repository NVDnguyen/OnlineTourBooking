package com.example.bookingapp.view.activities;

import static com.example.bookingapp.utils.Tool.insertSampleData;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.bookingapp.R;
import com.example.bookingapp.databinding.ActivityMainBinding;
import com.example.bookingapp.view.fragments.HomeFragment;
import com.example.bookingapp.view.fragments.MyTripFragment;
import com.example.bookingapp.view.fragments.SavedFragment;
import com.example.bookingapp.view.fragments.SettingFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Init Fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(binding.fragmentContainer.getId(), new HomeFragment())
                    .commit();
        }

        // Set up BottomNavigationView with NavController
        setupBottomNavigationView();

        // init database
       // insertSampleData(this);


    }
    private void setupBottomNavigationView(){
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            if(item.getItemId() == R.id.navigation_home){
                selectedFragment = new HomeFragment();
            }else if(item.getItemId() == R.id.navigation_mytrip){
                selectedFragment = new MyTripFragment();
            }else if(item.getItemId() == R.id.navigation_saved){
                selectedFragment = new SavedFragment();
            }else if(item.getItemId() == R.id.navigation_setting){
                selectedFragment = new SettingFragment();
            }
            if(selectedFragment != null){
                getSupportFragmentManager().beginTransaction()
                        .replace(binding.fragmentContainer.getId(), selectedFragment)
                        .commit();
            }
            return true;
        });
    }

}