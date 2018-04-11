package com.example.whatshouldweeattoday;

import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by 윤대원 on 2017-12-14.
 */

public class Food implements Comparable<Food> {

    private String name;
    private String closed;
    private String price;
    private String location;
    private Double rating;
    private String url;
    private String image_url;
    public void setName(String t)
    {
        this.name = t;
    }
    public void setClosed(String c) {
        this.closed = c;
    }
    public void setPrice(String p)
    {
        price = p;
    }
    public void setLocation(String l)
    {
        location = l;
    }
    public void setRating(double r)
    {
        rating = r;
    }
    public void setUrl(String u)
    {
        url = u;
    }
    public void setImage_url(String iu)
    {
        image_url = iu;
    }
    public String getName()
    {
        return name;
    }
    public String getClosed() {
        return closed;
    }
    public String getPrice()
    {
        return price;
    }
    public String getLocation()
    {
        return location;
    }
    public double getRating()
    {
        return rating;
    }
    public String getUrl()
    {
        return url;
    }
    public String getImage_url()
    {
        return image_url;
    }

    @Override
    public int compareTo(@NonNull Food other) {
        String menuName1 = this.getName().toUpperCase();
        String menuName2 = other.getName().toUpperCase();

        return menuName1.compareTo(menuName2);
    }

    public static Comparator<Food> RatingComparator = new Comparator<Food>() {
        @Override
        public int compare(Food f1, Food f2) {
            double rating1 = f1.getRating();
            double rating2 = f2.getRating();

            return Double.compare(rating2, rating1);
        }
    };

    public static Comparator<Food> PriceComparator = new Comparator<Food>() {
        @Override
        public int compare(Food f1, Food f2) {
            String price1 = f1.getPrice();
            String price2 = f2.getPrice();

            return price1.compareTo(price2);
        }
    };
}
