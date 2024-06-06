package com.project.android.controller;

import android.annotation.SuppressLint;

import com.project.android.model.Oder;
import com.project.android.model.Product;
import com.project.android.Utils.ultil;

import java.io.Serializable;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class ProfitControl implements Serializable {
    String month ;
    ArrayList<Oder> oders ;
    ArrayList<Product> products;
    public ProfitControl(ArrayList<Oder> oders, ArrayList<Product> products) {
        this.oders = oders;
        this.products = products;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getMonth() {
        return month;
    }

    public ArrayList<Oder> getOders() {
        return oders;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setOders(ArrayList<Oder> oders) {
        this.oders = oders;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ProfitControl() {

    }
    public int getAllOder(){
        int rs = 0;
        if("all".equals(month)) {
            rs += oders.size();
        }
        else {
            for (Oder e : oders) {
                if (e.getMouth().equals(month)&&e.getYear().equals(Integer.toString(Year.now().getValue()))) {
                    rs += 1;
                }
            }
        }
        return rs;
    }
    @SuppressLint("SuspiciousIndentation")
    public int getTotalOderdestroy(){
        int rs = 0;
        if("all".equals(month)) {
            for (Oder e : oders) {
                if (e.getStatus().equals("Huỷ"))
                    rs += 1;
            }
        }else {
            for (Oder e : oders) {
                if (e.getMouth().equals(month)&&e.getYear().equals(Integer.toString(Year.now().getValue()))) {
                    if (e.getStatus().equals("Huỷ"))
                    rs += 1;
                }
            }
        }
        return rs;
    }
    public int getProfit(){
        int rs = 0;
        if("all".equals(month)) {
            for (Oder e: this.oders) {
                Map<String, Integer> map = e.getAllOderCart();
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                     Product check = ultil.getProductClass(entry.getKey(),this.products);
                     rs+= check.getPrice()*entry.getValue();
                }
            }
        }
        else {
            for (Oder e : oders) {
                if (e.getMouth().equals(month)&&e.getYear().equals(Integer.toString(Year.now().getValue()))) {
                    Map<String, Integer> map = e.getAllOderCart();
                    for (Map.Entry<String, Integer> entry : map.entrySet()) {
                        Product check = ultil.getProductClass(entry.getKey(),this.products);
                        rs+= check.getPrice()*entry.getValue();
                    }
                }
            }
        }
        return rs;
    }
    public int getCost(){
            int rs = 0;
            if("all".equals(month)) {
                for (Oder e: this.oders) {
                    Map<String, Integer> map = e.getAllOderCart();
                    for (Map.Entry<String, Integer> entry : map.entrySet()) {
                        Product check = ultil.getProductClass(entry.getKey(),this.products);
                        rs+= check.getBasePrice()*entry.getValue();
                    }
                }
            }
            else {
                for (Oder e : oders) {
                    if (e.getMouth().equals(month)&&e.getYear().equals(Integer.toString(Year.now().getValue()))) {
                        Map<String, Integer> map = e.getAllOderCart();
                        for (Map.Entry<String, Integer> entry : map.entrySet()) {
                            Product check = ultil.getProductClass(entry.getKey(),this.products);
                            rs+= check.getBasePrice()*entry.getValue();
                        }
                    }
                }
            }
            return rs;
    }
    public String getMostFood(){
        Map<String,Integer> contri = ultil.getType();
        for (Oder e: this.oders) {
            Map<String, Integer> map = e.getAllOderCart();
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                for (Map.Entry<String, Integer> cont : contri.entrySet()) {
                    if (entry.getKey().equals(cont.getKey())){
                        cont.setValue(cont.getValue()+1);
                    }
                }
            }
        }
        return Collections.max(contri.entrySet(), Map.Entry.comparingByValue()).getKey().toString();
    }
    public int getComplete(){
        {
            int rs = 0;
            if("all".equals(month)) {
                for (Oder e : oders) {
                    if (e.getStatus().equals("Hoàn thành"))
                        rs += 1;
                }
            }else {
                for (Oder e : oders) {
                    if (e.getMouth().equals(month)&&e.getYear().equals(Integer.toString(Year.now().getValue()))) {
                        if (e.getStatus().equals("Hoàn thành"))
                            rs += 1;
                    }
                }
            }
            return rs;
        }
    }


}
