package com.example.fbflixster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fbflixster.R;
import com.example.fbflixster.models.Movie;

import java.util.List;

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
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

            //Create images using Glide
            Glide.with(context).load(imageURL).into(ivPoster);

        }
    }
}
