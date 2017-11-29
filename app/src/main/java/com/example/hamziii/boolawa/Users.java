package com.example.hamziii.boolawa;

import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Hamziii on 8/14/2017.
 */

public class Users
{
    private String Image , Name , Email , Price , Service , UserName , phone , charges , Caption , EventImage , UploaderId , friendName , Sent_By , InvitationCard , CreatedBy  , HiredBy , confirmation , InvitationTo ;

    public Users(){

    }

    public Users(String image, String name, String email, String price, String service, String userName, String phone, String charges, String caption, String eventImage, String uploaderId, String friendName, String sent_By, String invitationCard, String createdBy, String hiredBy, String confirmation, String invitationTo) {
        Image = image;
        Name = name;
        Email = email;
        Price = price;
        Service = service;
        UserName = userName;
        this.phone = phone;
        this.charges = charges;
        Caption = caption;
        EventImage = eventImage;
        UploaderId = uploaderId;
        this.friendName = friendName;
        Sent_By = sent_By;
        InvitationCard = invitationCard;
        CreatedBy = createdBy;
        HiredBy = hiredBy;
        this.confirmation = confirmation;
        InvitationTo = invitationTo;
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

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public String getCaption() {
        return Caption;
    }

    public void setCaption(String caption) {
        Caption = caption;
    }

    public String getEventImage() {
        return EventImage;
    }

    public void setEventImage(String eventImage) {
        EventImage = eventImage;
    }

    public String getUploaderId() {
        return UploaderId;
    }

    public void setUploaderId(String uploaderId) {
        UploaderId = uploaderId;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getSent_By() {
        return Sent_By;
    }

    public void setSent_By(String sent_By) {
        Sent_By = sent_By;
    }

    public String getInvitationCard() {
        return InvitationCard;
    }

    public void setInvitationCard(String invitationCard) {
        InvitationCard = invitationCard;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getHiredBy() {
        return HiredBy;
    }

    public void setHiredBy(String hiredBy) {
        HiredBy = hiredBy;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public String getInvitationTo() {
        return InvitationTo;
    }

    public void setInvitationTo(String invitationTo) {
        InvitationTo = invitationTo;
    }
}
