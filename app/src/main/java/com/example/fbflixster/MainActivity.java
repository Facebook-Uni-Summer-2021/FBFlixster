package com.example.fbflixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.fbflixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=2ab073f81ddbdbac6fe1df61563b9614";

    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Async performs actions based on completion time of network
        // connection (network connection has variable time and
        // cannot be predicted); once async is run, code moves on
        AsyncHttpClient client = new AsyncHttpClient();
        //We know this String is JSON, so we use JSON handler
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
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
                    movies = Movie.fromJsonArray(results);
                    Log.i(TAG, "Movies: " + movies.size());
                } catch (JSONException e) {
                    //Name may be incorrect, so catch exception
                    Log.e(TAG, "Json exception: " + e);
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                //When failed connection to Json
                Log.d(TAG, "onFailure");
            }
        });
    }
}