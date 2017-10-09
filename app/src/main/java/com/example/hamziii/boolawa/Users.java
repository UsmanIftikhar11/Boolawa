package com.example.hamziii.boolawa;

import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Hamziii on 8/14/2017.
 */

public class Users
{
    private String Image ;

    public Users(){

    }

    public Users(String image) {
        Image = image;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
