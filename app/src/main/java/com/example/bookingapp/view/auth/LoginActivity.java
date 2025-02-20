package com.example.bookingapp.view.auth;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapp.contract.LoginContract;
import com.example.bookingapp.data.model.User;
import com.example.bookingapp.data.repository.UserRepository;
import com.example.bookingapp.databinding.LoginActivityBinding;
import com.example.bookingapp.presenter.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private LoginActivityBinding binding;
    private LoginPresenter loginPresenter;
    private UserRepository userRepository;

    public LoginActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize View Binding properly
        binding = LoginActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.userRepository = new UserRepository(this);
        this.loginPresenter = new LoginPresenter(this,userRepository);

        binding.loginButton.setOnClickListener(v -> {
            String email = binding.email.getText().toString();
            String password = binding.password.getText().toString();
            loginPresenter.login(email,password);
        });
    }

    @Override
    public void onLoginSuccess(User user) {
        Toast.makeText(this,"Login successfully",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();

    }
}
