package com.example.mycalendar.presenter;

import com.example.mycalendar.model.Login;

public class RegisterPresenter {
RegisterInterface registerInterface;

    public RegisterPresenter(RegisterInterface registerInterface) {
        this.registerInterface = registerInterface;
    }
    public void RegisterCheck(Login login)
    {
        if(login.isEmailValid() && login.isPasswordValid())
        {
            registerInterface.RegisterSuccess();
        }
        else
            registerInterface.RegisterError();
    }
}
