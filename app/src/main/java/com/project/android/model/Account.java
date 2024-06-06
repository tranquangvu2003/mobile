package com.project.android.model;

import com.google.firebase.database.DataSnapshot;
import com.project.android.controller.AccountControl;

import java.io.Serializable;

public class Account implements Serializable {
    String id;
    String password;
    String userName;
    String email;
    String phoneNumber;
    String avatar;
    String name;

    public Account() {
    }

    public Account(String password, String userName, String email, String phoneNumber, String avatar, String name) {
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.avatar = avatar;
        this.name = name;
    }

    public Account(String id, String password, String userName, String email, String phoneNumber, String avatar, String name) {
        this.id = id;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.avatar = avatar;
        this.name = name;
    }

    public Account(String username, String password) {
    }

    public Account(String userName, String password, String phoneNumber, String email, String name) {
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void getAccinfor(DataSnapshot snapshot) {

    }
}
