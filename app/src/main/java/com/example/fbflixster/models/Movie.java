package com.example.fbflixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

//To create folder, right-click->New->Package
//Model represents an object
//Identify this model as Parcel
@Parcel
public class Movie {
    //Make private TAG to test

    public static final String MOVIE_OBJECT = "movie_object";

    String posterPath;
    String backdropPath;
    String title;
    String overview;
    float popularity;
    String release_date;
    float vote_average;
    float vote_count;
    int id;

    //Required for Parceler
    public Movie () {}

    //Constructor to take JSON Object and return Movie object
    public Movie (JSONObject jsonObject) throws JSONException {
        //For JSONs, ensure to type LETTER FOR LETTER for correct keys
        posterPath = jsonObject.getString("poster_path");
        backdropPath = jsonObject.getString("backdrop_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        popularity = jsonObject.getInt("popularity");
        release_date = jsonObject.getString("release_date");
        vote_average = jsonObject.getInt("vote_average");
        vote_count = jsonObject.getInt("vote_count");
        id = jsonObject.getInt("id");
    }

    //Constructs movie for each JSON array (movie API indicates a
    // movie is a single array in the JSON)
    public static List<Movie> fromJsonArray (JSONArray jsonMovieArray)
            throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < jsonMovieArray.length(); i++) {
            movies.add(new Movie(jsonMovieArray.getJSONObject(i)));
        }
        return movies;
    }

    //Getters

    //posterPath is only partial; requires full URL
    public String getPosterPath() {
        //For alternate phones, we would include some function
        // to calculate which poster size should be used
        // based on phone size; do that later?
        //https://api.themoviedb.org/3/configuration?api_key=NOW_PLAYING_URL
        return String.format("https://image.tmdb.org/t/p/w342/%s",
                posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public float getPopularity() {
        return popularity;
    }

    public String getRelease_date() {
        return release_date;
    }

    public float getVote_average() {
        return vote_average;
    }

    public float getVote_count() {
        return vote_count;
    }

    public int getId() {
        return id;
    }
}
