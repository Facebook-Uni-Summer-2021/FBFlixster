package com.example.fbflixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.fbflixster.adapters.MovieAdapter;
import com.example.fbflixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {
    /*
    Goals
    User can view list of movies
    User can view an individual movie and view its information
    Data must come from MovieDB API

    API - Application Programming Interface
        Functions/resources provided by a third party software
    Client - software/resource that "consumes" (uses) API

    Endpoint - can get or modify data
        Most API combine "[website][endpoint]?[keyvalue pairs]"

    A JSON List/Array is represented by [] (square brackets)
    A JSON Object is represented by {} (brackets)

     */
    //This may need to be secret, check Hints in Week 1 Day 2
    private static final String TAG = "MainActivity";
    public static String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=";

    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Async performs actions based on completion time of network
        // connection (network connection has variable time and
        // cannot be predicted); once async is run, code moves on
        AsyncHttpClient client = new AsyncHttpClient();
        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();

        //Originally, I combined the playing url with API key here; an error occurred
        // when rotating that the API key was not valid

        //Create adapter
        MovieAdapter adapter = new MovieAdapter(this, movies);
        //Set adapter on RecyclerView
        rvMovies.setAdapter(adapter);
        //Set LayoutManager to tell RV how to layout views on screen
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        //We know this String is JSON, so we use JSON handler
        //A BUG: When creating a secret xml, the Async has problems getting the combined
        // version of the NOW_PLAYING_URL + getString() if not contained in client call
        client.get(NOW_PLAYING_URL + getString(R.string.movie_api_key), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                //When successful connection to Json
                Log.d(TAG, "onSuccess");
                //Breakpoint indicates JSON json is jsonObject
                JSONObject jsonObject = json.jsonObject;
                //JSONObject jsonObject contains JSON Array results, which
                // contains the movie information we need indicated
                // by breakpoint (run with bug symbol)
                try {
                    JSONArray results =
                            jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: " + results.toString());
                    //Convert JSON object's results to list of movies
                    //movies = Movie.fromJsonArray(results);
                    //Use currently created items list
                    movies.addAll(Movie.fromJsonArray(results));
                    adapter.notifyDataSetChanged();
                    Log.i(TAG, "Movies: " + movies.size());
                } catch (JSONException e) {
                    //Name may be incorrect, so catch exception
                    Log.e(TAG, "Json exception: " + e);
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                //When failed connection to Json
                Log.d(TAG, "onFailure: " + s);
            }
        });
    }
}

/*
The following are questions I had while working:
    The ViewBind does not work; I added it into build.gradle(Module) and created test
        activity, but I could not create the class explained in the CodePath docs
        FIXED? Make sure you sync, and to use the binding class type [layout]Binding, where layout
        may be activity_test
    I could not get the YouTube player to work on emulator despite it saying
        it supports it...am I the only one?
           I FIXED IT! Check manifest
    An error occurs when creating a secret xml (I added it to git when I created the xml,
        was that wrong?) and combining the url and api_key outside the async client call
    How do you make the secret xml? See above question, or try the other way using .gitignore

Later:
    Can you place a new image on top of another? Refers to adding play button on top of poster
 */