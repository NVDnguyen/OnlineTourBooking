package com.example.bookingapp.contract;

public interface RegisterContract {
    interface View {
        void onRegisterSuccess();
        void onRegisterError(String message);
    }
    interface Present {
        void register(String email, String password, String name, String location);
    }
}
