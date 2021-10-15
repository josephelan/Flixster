package com.example.flixster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

  Context context;
  List<Movie> movies;

  public MovieAdapter(Context context, List<Movie> movies) {
    this.context = context;
    this.movies = movies;
  }

  // Usually involves inflating a layout from XML and returning the holder
  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    Log.d("MovieAdapter", "onCreateViewHolder");
    View movie_view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
    return new ViewHolder(movie_view);
  }

  // Involves populating data into item through viewholder
  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Log.d("MovieAdapter", "onBindViewHolder: " + position);

    // get movie at position
    Movie movie = movies.get(position);

    // bind movie data into the viewholder
    holder.bind(movie);

  }

  // Returns total count of items in list
  @Override
  public int getItemCount() {
    return movies.size();
  }

  // inner viewholder class extends recyclerview viewholder
  public class ViewHolder extends RecyclerView.ViewHolder{

    RelativeLayout container;
    TextView tv_title;
    TextView tv_overview;
    TextView tv_rating;
    TextView tv_date;
    ImageView image_view_poster;

    // construct viewholder
    public ViewHolder(@NonNull View itemView) {
      super(itemView);

      // Assign viewholder information to current movie data
      tv_title = itemView.findViewById(R.id.movie_title);
      tv_overview = itemView.findViewById(R.id.movie_overview);
      tv_rating = itemView.findViewById(R.id.movie_rating);
      tv_date = itemView.findViewById(R.id.movie_release_date);
      image_view_poster = itemView.findViewById(R.id.image_view_poster);
    }

    // bind movie to Viewholder
    public void bind(Movie movie) {

      // Set screen text to movie data members
      tv_title.setText(movie.getTitle());
      tv_overview.setText(movie.getOverview());
      tv_rating.setText(String.format("Rating: %s", movie.getRating()));
      tv_date.setText(String.format("Release date: %s", movie.getRelease_date()));
      if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
        container = itemView.findViewById(R.id.container_landscape);
      } else {
        container = itemView.findViewById(R.id.container_portrait);
      }
      String imageUrl;

      // Change image based on portrait vs landscape mode
      if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
        imageUrl = movie.getBackdrop_path();
      } else {
        imageUrl = movie.getPoster_path();
      }

      // load image into poster position, include loading image
      Glide.with(context)
          .load(imageUrl)
          .placeholder(R.drawable.ic_launcher_background)
          .into(image_view_poster);

      // 1. Register click listener on whole movie row
      container.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

          // 2. Navigate to a new activity on click (tap)
          Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show();
        }
      });
    }
  }
}
