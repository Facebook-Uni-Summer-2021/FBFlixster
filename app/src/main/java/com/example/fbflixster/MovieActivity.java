package com.example.fbflixster;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.fbflixster.models.Movie;
import com.example.fbflixster.models.Video;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MovieActivity extends YouTubeBaseActivity {
    /*
    A problem I have encountered is including YouTube into the emulator.
    QUESTION: How do I fix this?
     */

    private static final String TAG = "MovieActivity";

    Movie movie;
    List<Video> videos;
    String videoId = "wtx0fdzRAp8";//Set a placeholder

    RatingBar rbMovieRating;
    TextView tvDetailedTitle;
    TextView tvDetailedOverview;
    TextView tvRelease;
    ImageView ivDetailedPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //Unwrap movie from intent
        movie = Parcels.unwrap(getIntent().getParcelableExtra(Movie.MOVIE_OBJECT));
        Log.d(TAG, "Title: " + movie.getTitle() + ", video: ");

        //Create client for getting the videos available for a movie
        AsyncHttpClient client = new AsyncHttpClient();
        String MOVIE_VIDEOS_URL = "https://api.themoviedb.org/3/movie/" +
                movie.getId() + "/videos?api_key=" +
                getString(R.string.movie_api_key);
        client.get(MOVIE_VIDEOS_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                //When successful connection to Json
                Log.d(TAG, "onSuccess");

                //Create a list of videos associated with a movie
                videos = new ArrayList<>();
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results =
                            jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: " + results.toString());
                    videos.addAll(Video.fromJsonArray(results));
                    Log.i(TAG, "Movies: " + videos.size());

                    //Find the first video that is a trailer, since movies
                    // may have more than 1
                    for (int j = 0; j < videos.size(); j++) {
                        if (videos.get(j).getSite().compareTo("YouTube") == 0 &&
                                videos.get(j).getType().compareTo("Trailer") == 0) {
                            videoId = videos.get(j).getKey();
                            break;
                        }
                    }

                    //Perform the videoplayer initialization here just in case
                    // the network is slow

                    //Bind the player
                    YouTubePlayerView player = findViewById(R.id.player);

                    //Initialize player using API key
                    //This is not secret in code, as Android Studio replaces the getString(...)
                    // with the actual key in gray
                    player.initialize(getString(R.string.youtube_api_key),
                            new YouTubePlayer.OnInitializedListener() {
                                @Override
                                public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                                    YouTubePlayer youTubePlayer,
                                                                    boolean b) {
                                    //Cue the video
                                    youTubePlayer.cueVideo(videoId);
                                }

                                @Override
                                public void
                                onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult
                                                                youTubeInitializationResult) {
                                    Log.e(TAG, "Error playing: " + youTubeInitializationResult);
                                }
                            });

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

        //Perform other binding for detailed view
        rbMovieRating = findViewById(R.id.rbMovieRating);
        tvDetailedTitle = findViewById(R.id.tvDetailedTitle);
        tvDetailedOverview = findViewById(R.id.tvDetailedOverview);
        ivDetailedPoster = findViewById(R.id.ivDetailedPoster);
        tvRelease = findViewById(R.id.tvRelease);
        //Glide
        Glide.with(this).
                load("http://via.placeholder.com/300.png").
                into(ivDetailedPoster);

        tvDetailedTitle.setText(movie.getTitle());
        if (movie.getVote_average() >= 6) {
            tvDetailedTitle.setTextColor(Color.parseColor("#F0EA28"));
        }

        tvDetailedOverview.setText(movie.getOverview());
        tvRelease.setText("Release date: " + movie.getRelease_date());
        Glide.with(this).load(movie.getPosterPath()).into(ivDetailedPoster);
        Log.i(TAG, "Rating: " + movie.getVote_average());
        rbMovieRating.setRating(movie.getVote_average());
    }
}