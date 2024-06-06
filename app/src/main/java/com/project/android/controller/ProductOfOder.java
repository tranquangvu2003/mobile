package com.project.android.controller;

public class ProductOfOder {
    String id;
    String name;
    int quantity;
    int cost;
    String pic;

    public ProductOfOder() {
    }

    public ProductOfOder(String id, String name, int quantity, int cost, String pic) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.cost = cost;
        this.pic = pic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCost() {
        return cost*quantity;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
