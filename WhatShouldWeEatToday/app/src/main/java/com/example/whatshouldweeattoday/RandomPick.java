package com.example.whatshouldweeattoday;

import java.util.Random;

/**
 * Created by 윤대원 on 2017-12-14.
 */

public class RandomPick {

    static final String[] DINING_PLACE = new String[]{
            "Blacksburg Taphouse","622 North","Italiano's", "El Rodeo", "Next Door Bake Shop",
            "India Garden Restaurant", "Sub Station II", "Buffalo Wild Wings", "Turner Place", "Abby's",
            "Five Guys", "Waffle House", "Zaxby's Chicken Fingers & Buffalo", "Mill Mountain Coffee & Tea", "Taco Bell",
            "Wendy's", "Papa John's Pizza", "Burger 37", "Domino's Pizza", "McDonald's"
    };

    int number = -1;
    Random random;

    public RandomPick(int seed) {
        random = new Random(seed);
    }

    public void spin() {
        number = random.nextInt(20);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int n) {
        this.number = n;
    }

    public String check(int n) {
        if (n >= 0) {
            return DINING_PLACE[n];
        }
        else {
            return "Pick a Place!";
        }
    }
}
