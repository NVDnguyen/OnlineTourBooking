package com.example.bookingapp.presenter;

import com.example.bookingapp.contract.RegisterContract;
import com.example.bookingapp.data.model.User;
import com.example.bookingapp.data.repository.UserRepository;
import com.example.bookingapp.utils.Validator;

public class RegisterPresenter implements RegisterContract.Present {
    private final RegisterContract.View registerView;
    private final UserRepository userRepository;

    public RegisterPresenter(RegisterContract.View registerView, UserRepository userRepository){

        this.registerView = registerView;
        this.userRepository = userRepository;
    }
    @Override
    public void register(String email, String password, String name, String location) {
        User u = new User(email,location,name,password);
        if(Validator.validateUser(u)){
            if(userRepository.createUser(u)!=-1){
                registerView.onRegisterSuccess();
            }else{
                registerView.onRegisterError("Register failed");
            }
        }else{
            registerView.onRegisterError("Invalid email or password");
        }
    }
}
