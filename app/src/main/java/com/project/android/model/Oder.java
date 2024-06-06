package com.project.android.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import com.google.firebase.database.PropertyName;

import java.util.Map;


public class Oder implements Serializable {
    String id;
    String pbuyName;
    String phoneNumber;
    String note;
    String dayCrate;
    String status;
    String idAccount;
    int cost;
    int ship;
    int net;
    int total;
    String address;
    Map<String, Integer> allOderCart;


    public Oder(String id, String pbuyName, String phoneNumber, String note, String dayCrate, String status, String idAccount, String address, Map<String, Integer> allOderCart) {
        this.id = id;
        this.pbuyName = pbuyName;
        this.phoneNumber = phoneNumber;
        this.note = note;
        this.dayCrate = dayCrate;
        this.status = status;
        this.idAccount = idAccount;
        this.address = address;
        this.allOderCart = allOderCart;
    }

    public Oder() {
        // Default constructor required for calls to DataSnapshot.getValue(Oder.class)
    }

    public Oder(String id, String pbuyName, String phoneNumber, String note, String dayCrate, String status, String idAccount, int cost, int ship, int net, int total, String address, Map<String, Integer> allOderCart) {
        this.id = id;
        this.pbuyName = pbuyName;
        this.phoneNumber = phoneNumber;
        this.note = note;
        this.dayCrate = dayCrate;
        this.status = status;
        this.idAccount = idAccount;
        this.cost = cost;
        this.ship = ship;
        this.net = net;
        this.total = total;
        this.address = address;
        this.allOderCart = allOderCart;
    }

//    public Oder(String id, String pbuyName, String phoneNumber, String note, String dayCrate, String status, String idAccount, int cost, int ship, int net, int total, String address) {
//        this.id = id;
//        this.pbuyName = pbuyName;
//        this.phoneNumber = phoneNumber;
//        this.note = note;
//        this.dayCrate = dayCrate;
//        this.status = status;
//        this.idAccount = idAccount;
//        this.cost = cost;
//        this.ship = ship;
//        this.net = net;
//        this.total = total;
//        this.address = address;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPbuyName() {
        return pbuyName;
    }

    public void setPbuyName(String pbuyName) {
        this.pbuyName = pbuyName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDayCrate() {
        return dayCrate;
    }

    public void setDayCrate(String dayCrate) {
        this.dayCrate = dayCrate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getShip() {
        return ship;
    }

    public void setShip(int ship) {
        this.ship = ship;
    }

    public int getNet() {
        return net;
    }

    public void setNet(int net) {
        this.net = net;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getMouth(){
        StringTokenizer st = new StringTokenizer(dayCrate,"/");
        st.nextToken();
        return st.nextToken().toString();
    }
    public String getYear(){
        StringTokenizer st = new StringTokenizer(dayCrate,"/");
        st.nextToken();
        st.nextToken();
        return st.nextToken().toString();
    }
    public Map<String, Integer> getAllOderCart() {
        return allOderCart;
    }
    public String getIDpro(){
        String rs="";
        for (Map.Entry<String, Integer> entry : allOderCart.entrySet()) {
           rs+= entry.getKey();
        }
        return rs;
    }

    public void setAllOderCart(Map<String, Integer> allOderCart) {
        this.allOderCart = allOderCart;
    }
}
