package com.example.bookingapp.presenter;

import static com.example.bookingapp.utils.Validator.isValidEmail;

import com.example.bookingapp.contract.LoginContract;
import com.example.bookingapp.data.model.User;
import com.example.bookingapp.data.repository.UserRepository;

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View loginView;
    private UserRepository userRepository;

    public LoginPresenter(LoginContract.View loginView, UserRepository userRepository) {
        this.loginView = loginView;
        this.userRepository = userRepository;
    }

    @Override
    public void login(String email, String password) {
        if (!isValidEmail(email)) {
            loginView.onLoginError("Invalid email format.");
            return;
        }
        if (password == null || password.trim().isEmpty()) {
            loginView.onLoginError("Password cannot be empty.");
            return;
        }

        User user = userRepository.getUserByAuth(email, password);
        if (user != null) {
            loginView.onLoginSuccess(user);
        } else {
            loginView.onLoginError("Invalid email or password.");
        }
    }

}
