package com.example.bookingapp.contract;

import com.example.bookingapp.data.model.User;

public interface LoginContract {
    interface View {
        void onLoginSuccess(User user);
        void onLoginError(String message);
    }

    interface Presenter {
        void login(String email, String password);
    }
}
