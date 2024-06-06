package com.project.android.Utils;

import com.project.android.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public  class ultil {

    public static String intToVND(int iput){
        String inPut =Integer.toString(iput);
        String rsl = " VND";
        char[] charrr = inPut.toCharArray() ;
        int count = 0;
        for(int i=charrr.length-1;i>=0 ; i--){
            count++;
            rsl =  charrr[i] +rsl ;
            if(count%3 ==0  && count < charrr.length) {
                rsl =  '.' +rsl ;
            }
        }
        return rsl;
    }
    public static Product getProductClass(String id, ArrayList<Product> pr){
        Product rs = new Product();
        for (Product e: pr) {
            if (e.getId().equals(id)){
                rs= e;
                break;
            }
        }
        return rs;
    }
    public static Map<String,Integer> getType(){
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put( "Cơm gà", 0);
        map.put("Cơm bò", 0);
        map.put("Cơm hến", 0);
        map.put("Cơm chó", 0);
        return map;
    }

}
