package com.example.bookingapp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapp.contract.LoginContract;
import com.example.bookingapp.data.model.User;
import com.example.bookingapp.data.repository.UserRepository;
import com.example.bookingapp.databinding.ActivityLoginBinding;
import com.example.bookingapp.presenter.LoginPresenter;

import java.util.List;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {
    private ActivityLoginBinding binding;
    private LoginPresenter loginPresenter;
    private UserRepository userRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize
        userRepository = new UserRepository(this);
        loginPresenter = new LoginPresenter(this, userRepository,this);


        // Navigate to Register screen
        binding.registerButton.setOnClickListener(view -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
        binding.loginButton.setOnClickListener(v -> attemptLogin());
        // Load users in background to prevent UI thread blocking
        Executors.newSingleThreadExecutor().execute(() -> {
            List<User> users = userRepository.getAllUsers();
            Log.d("Users", users.toString());
        });
    }

    // Method to validate input and attempt login
    private void attemptLogin() {
        String email = binding.email.getText().toString().trim();
        String password = binding.password.getText().toString().trim();

        // Input validation
        if (email.isEmpty()) {
            binding.email.setError("Email is required");
            return;
        }
        if (password.isEmpty()) {
            binding.password.setError("Password is required");
            return;
        }
        loginPresenter.login(email, password);
    }

    @Override
    public void onLoginSuccess(User user) {
        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("USER_EMAIL", user.getEmail());  // Pass user data if needed
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
