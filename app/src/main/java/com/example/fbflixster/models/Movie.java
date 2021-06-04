package com.example.fbflixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//To create folder, right-click->New->Package
//Model represents an object
public class Movie {
    String posterPath;
    String title;
    String overview;

    //Constructor to take JSON Object and return Movie object
    public Movie (JSONObject jsonObject) throws JSONException {
        //For JSONs, ensure to type LETTER FOR LETTER for correct keys
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
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

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }
}
