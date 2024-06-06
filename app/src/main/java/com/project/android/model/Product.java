package com.project.android.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class Product implements Serializable {
    String id;
    String foodName;
    String idType;
    int quantity;
    boolean isCombo;
    boolean idSale;
    boolean isHot;
    String img;
    int price;
    int basePrice;
    int image;

    public Product(int image ,String foodName, String idType)  {
        this.image = image;
        this.foodName = foodName;
        this.idType = idType;

    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Product() {
    }

    public Product(String foodName, String idType, int quantity, String img, int price, int basePrice) {
        this.foodName = foodName;
        this.idType = idType;
        this.quantity = quantity;
        this.img = img;
        this.price = price;
        this.basePrice = basePrice;
    }

    public Product(String id, String foodName, String idType, int quantity, boolean isCombo, boolean idSale, boolean isHot, String img, int price, int basePrice) {
        this.id = id;
        this.foodName = foodName;
        this.idType = idType;
        this.quantity = quantity;
        this.isCombo = isCombo;
        this.idSale = idSale;
        this.isHot = isHot;
        this.img = img;
        this.price = price;
        this.basePrice = basePrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isCombo() {
        return isCombo;
    }

    public void setCombo(boolean combo) {
        isCombo = combo;
    }

    public boolean isIdSale() {
        return idSale;
    }

    public void setIdSale(boolean idSale) {
        this.idSale = idSale;
    }

    public boolean isHot() {
        return isHot;
    }

    public void setHot(boolean hot) {
        isHot = hot;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }
}
