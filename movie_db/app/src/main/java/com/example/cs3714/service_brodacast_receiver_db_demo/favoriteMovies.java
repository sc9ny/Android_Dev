package com.example.cs3714.service_brodacast_receiver_db_demo;

import java.util.ArrayList;

/**
 * Created by seungleechoi on 10/18/17.
 */

public class favoriteMovies
{
    private ArrayList<String> likedTitles;
    private ArrayList<Movie> likedMovies;
    public favoriteMovies()
    {
        likedTitles = new ArrayList<String>();
        likedMovies = new ArrayList<Movie>();
    }
    public void addTitle(String title, Movie movie)
    {
        if (!isLiked(title))
        {
            likedTitles.add(title);
            likedMovies.add(movie);
        }

    }
    public void removeTitle(String title)
    {
        if (isLiked(title)) {
            likedTitles.remove(title);
            for (int i = 0; i < likedMovies.size(); i++)
            {
                if (likedMovies.get(i).getTitle().equals(title))
                {
                    likedMovies.remove(i);
                }
            }
        }
    }
    public ArrayList<Movie> getLikedMovies()
    {
        return likedMovies;
    }
    public ArrayList<String> getLikedTitles()
    {
        return likedTitles;
    }
    public boolean isLiked(String title)
    {
        if (likedTitles.contains(title))
        {
            return true;
        }
        return false;
    }
    public void removeAll()
    {
        likedMovies.clear();
        likedTitles.clear();
    }
}