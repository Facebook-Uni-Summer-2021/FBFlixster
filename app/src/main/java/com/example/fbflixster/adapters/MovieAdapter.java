package com.example.fbflixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.fbflixster.MovieActivity;
import com.example.fbflixster.R;
import com.example.fbflixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/*
The following are steps to creating an adapter:
Step 1. Create a ViewHolder public class that extends
 RecyclerView.ViewHolder and define the widget variables
Step 2. Extend adapter class with
 RecyclerView.Adapter<[AdapterClass].ViewHolder> (implement methods)
Step 4. Define context and items list in adapter, then generate constructor
Step 5. Inflate view in onCreateViewHolder with
 "View view = LayoutInflater.from(context).inflate(R.layout.item_movie,
 parent, false);" and return as new ViewHolder
Step 6. In onBindViewHolder, create an item from the list of items
 (Item item = items.get(index)) and populate widget variables
 through holder.bind(item) (fix error by creating new method
 "bind()" in ViewHolder)
Step 7. Bind appropriate item model information to widget variables
 from ViewHolder
Step 8. Set getItemCount to size of items list

onCreateViewHolder - inflates an xml layout and return as ViewHolder
onBindViewHolder - populates data into view through ViewHolder
getItemCount - returns total items of items list

 */
public class MovieAdapter extends
        RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    private static final String TAG = "MovieAdapter";

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        View view = LayoutInflater.from(context).inflate(
                R.layout.item_movie,
                parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,
                                 int position) {
        Log.d(TAG, "onBindViewHolder " + position);
        Movie movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        TextView tvVoteAverage;
        TextView tvVoteCount;
        RelativeLayout rlContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            tvVoteAverage = itemView.findViewById(R.id.tvVoteAverage);
            tvVoteCount = itemView.findViewById(R.id.tvVoteCount);
            rlContainer = itemView.findViewById(R.id.rlContainer);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            if (movie.getVote_average() >= 6) {
                tvTitle.setTextColor(Color.parseColor("#F0EA28"));
            }

            tvVoteAverage.setText(String.valueOf(movie.getVote_average()));
            tvVoteCount.setText(String.valueOf(movie.getVote_count()));

            tvOverview.setText(movie.getOverview());
            //QUESTION: where does this go?
            Glide.with(context).
                    load("http://via.placeholder.com/300.png").
                    into(ivPoster);

            String imageURL;

            //if phone in landscape
            if (context.getResources().getConfiguration().orientation ==
                    Configuration.ORIENTATION_LANDSCAPE) {
                //imageURL = backdrop
                imageURL = movie.getBackdropPath();
            } else {
                //imageURL = poster
                imageURL = movie.getPosterPath();
            }

            //Create images using Glide; check out CodePath docs
            // for API of Glide
            // https://guides.codepath.org/android/Displaying-Images-with-the-Glide-Library#troubleshooting
            int radius = 50;
            int margin = 10;
            Glide.with(context).load(imageURL).fitCenter().transform(new RoundedCornersTransformation(radius, margin)).into(ivPoster);

            /*
            Potentially I can use popularity and ratings_count below the poster (increase maxLines
            for title and overview) with an icon of a person silouette and a thumbs up, respectively,
            for using more of the movie attributes!
             */

            rlContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Create detailed activity of movie
                    //Requires previous class, not adapter class, so use context
                    Intent intent = new Intent(context, MovieActivity.class);
                    //Requires Parceler to pass on Movie model; see Movie model for
                    // changes required for Parceler
                    intent.putExtra(Movie.MOVIE_OBJECT, Parcels.wrap(movie));

                    //Requires context to start new activity, since we are in adapter
                    context.startActivity(intent);
                }
            });


        }
    }
}
