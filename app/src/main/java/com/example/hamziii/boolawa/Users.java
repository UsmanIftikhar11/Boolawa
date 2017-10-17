package com.example.hamziii.boolawa;

import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Hamziii on 8/14/2017.
 */

public class Users
{
    private String Image , Name , Email , Price , Service ;

    public Users(){

    }

    public Users(String image, String name, String email, String price, String service) {
        Image = image;
        Name = name;
        Email = email;
        Price = price;
        Service = service;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
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

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getService() {
        return Service;
    }

    public void setService(String service) {
        Service = service;
    }
}
