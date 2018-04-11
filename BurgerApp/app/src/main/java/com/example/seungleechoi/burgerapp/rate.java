package com.example.seungleechoi.burgerapp;

import android.widget.Toast;

/**
 * Created by seungleechoi on 9/11/17.
 */

public class rate {
    double total=0;
    int total_calorie =0;
    int total1 =0;
    public double computePrice(double price )
    {

        total = total + price;
        return total;
        //Toast toast = Toast.makeText(this, "hi", Toast.LENGTH_SHORT);
    }
    public int computerCalorie(int calorie)
    {

        total_calorie= total_calorie + calorie;
        return total_calorie;
    }
    public int maxTopping()
    {
        //int total1 = 0;
        total1 = total1 + 1;
        return total1;
    }

}
