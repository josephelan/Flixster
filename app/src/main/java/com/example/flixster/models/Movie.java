package com.example.flixster.models;

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {

  private String poster_path_;
  private String title_;
  private String overview_;
  private String release_date_;
  private double rating_;

  // Creates list of movies in from JSON array
  public static List<Movie> fromJsonArray(JSONArray movie_json_array) throws JSONException {

    // movie list
    List<Movie> movies = new ArrayList<>();

    // iterate through json array and create movie objects
    for (int i = 0; i < movie_json_array.length(); ++i) {
      movies.add(new Movie(movie_json_array.getJSONObject(i)));
    }
    return movies;
  }

  public Movie(JSONObject json_object) throws JSONException {
    poster_path_ = json_object.getString("poster_path");
    title_ = json_object.getString("title");
    overview_ = json_object.getString("overview");
    release_date_ = json_object.getString("release_date");
    rating_ = json_object.getDouble("vote_average");
  }

  public String getPoster_path_() {
    return String.format("http://image.tmdb.org/t/p/w342/%s", poster_path_);
  }

  public String getTitle_() {
    return title_;
  }

  public String getOverview_() {
    return overview_;
  }

  public String getRelease_date_() {
    return release_date_;
  }

  public double getRating_() {
    return rating_;
  }
}
