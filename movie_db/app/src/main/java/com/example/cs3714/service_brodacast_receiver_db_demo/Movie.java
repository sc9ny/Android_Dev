package com.example.cs3714.service_brodacast_receiver_db_demo;

/**
 * Created by esaki on 10/12/2017.
 */

public class Movie {


    private String title;
    private String date;
    private String rating;
    private String overview;
    private String path_poster;
    private String backdrop_path;
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
    public void setOverview(String overview)
    {
        this.overview =overview;
    }
    public void setPath_poster(String path_poster)
    {
        this.path_poster = path_poster;
    }
    public void setBackdrop_path(String backdrop_path) {this.backdrop_path = backdrop_path;}
    public String getTitle() {

        return title;
    }

    public String getDate() {
        return date;
    }

    public String getRating() {
        return rating;
    }
    public String getOverview()
    {
        return overview;
    }
    public String getPath_poster()
    {
        return path_poster;
    }
    public String getBackdrop_path() {return backdrop_path;}
}
