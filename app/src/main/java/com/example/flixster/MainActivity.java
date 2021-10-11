package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

  // URL with personal key to get now playing movies from themoviedb
  public static final String NOW_PLAYING_URL = "https://api.themoviedb" +
      ".org/3/movie/now_playing?api_key=49dc567369f030e2c6ccd9872ba06bf8";

  // tag for logging data
  public static final String TAG = "MainActivity";

  public List<Movie> movies;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Json handling Asynchronous client
    AsyncHttpClient client = new AsyncHttpClient();
    client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
      @Override
      public void onSuccess(int statusCode, Headers headers, JSON json) {
        Log.d(TAG, "onSuccess");

        // JSON object from response NOW_PLAYING_URL
        JSONObject json_object = json.jsonObject;

        // create json array for results which have objects for movies
        try {
          JSONArray results = json_object.getJSONArray("results");
          Log.i(TAG, "Results: " + results.toString()); // show JSON results

          // initialize movies from movies generated from json array
          movies = Movie.fromJsonArray(results);
          Log.i(TAG,"Movies size: " + movies.size());
        } catch (JSONException e) {
          Log.e(TAG, "Json results creation exception");
        }
      }

      @Override
       public void onFailure(int statusCode, Headers headers, String response,
                             Throwable throwable) {
        Log.d(TAG, "onFailure");
      }
    });
  }
}