package com.example.mycalendar.model;

import android.text.TextUtils;
import android.util.Patterns;

public class Login {
    private String Name;
    private String Email;
    private String Password;

    public Login(String name, String email, String password) {
        Name = name;
        Email = email;
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
    public boolean isEmailValid()
    {
        return !TextUtils.isEmpty(Email) && Patterns.EMAIL_ADDRESS.matcher(Email).matches();
    }

    public boolean isPasswordValid()
    {
        return !TextUtils.isEmpty(Password) && Password.length()>=6;
    }
}
