package com.example.flixster.adapters;

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

  // Usually involves inflacting a layout from XML and returning the holder
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

    TextView tv_title;
    TextView tv_overview;
    TextView tv_rating;
    TextView tv_date;
    ImageView image_view_poster;

    // construct viewholder
    public ViewHolder(@NonNull View itemView) {
      super(itemView);

      tv_title = itemView.findViewById(R.id.movie_title);
      tv_overview = itemView.findViewById(R.id.movie_overview);
      tv_rating = itemView.findViewById(R.id.movie_rating);
      tv_date = itemView.findViewById(R.id.movie_release_date);
      image_view_poster = itemView.findViewById(R.id.image_view_poster);
    }

    // bind movie to viewholder
    public void bind(Movie movie) {
      tv_title.setText(movie.getTitle_());
      tv_overview.setText(movie.getOverview_());
      tv_rating.setText(String.format("Rating: %s", movie.getRating_()));
      tv_date.setText(String.format("Release date: %s", movie.getRelease_date_()));
      String imageUrl;

      // Change image based on portrait vs landscape mode
      if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
        imageUrl = movie.getBackdrop_path_();
      } else {
        imageUrl = movie.getPoster_path_();
      }

      // load image
      Glide.with(context).load(imageUrl).into(image_view_poster);
    }
  }
}
