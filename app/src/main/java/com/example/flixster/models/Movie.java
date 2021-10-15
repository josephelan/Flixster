package com.example.flixster.models;

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {

  private String poster_path_;
  private String backdrop_path_;
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
    backdrop_path_ = json_object.getString("backdrop_path");
    poster_path_ = json_object.getString("poster_path");
    title_ = json_object.getString("title");
    overview_ = json_object.getString("overview");
    release_date_ = json_object.getString("release_date");
    rating_ = json_object.getDouble("vote_average");
  }

  public String getPoster_path() {
    return String.format("http://image.tmdb.org/t/p/w342/%s", poster_path_);
  }

  public String getBackdrop_path() {
    return String.format("http://image.tmdb.org/t/p/w342/%s", backdrop_path_);
  }

  public String getTitle() {
    return title_;
  }

  public String getOverview() {
    return overview_;
  }

  public String getRelease_date() {
    String[] date = release_date_.split("-");
    String month = monthHelper(date[1]);
    return month + " " + date[2] + " " + date[0];
  }

  public Double getRating() {
    return rating_;
  }

  /**
   * Convert int to month
   * @param month
   * @return month as a string
   */
  private String monthHelper(String month) {
    switch (month) {
      case "1":
        return "January";
      case "2":
        return "February";
      case "3":
        return "March";
      case "4":
        return "April";
      case "5":
        return "May";
      case "6":
        return "June";
      case "7":
        return "July";
      case "8":
        return "August";
      case "9":
        return "September";
      case "10":
        return "October";
      case "11":
        return "November";
      default:
        return "December";
    }
  }
}
