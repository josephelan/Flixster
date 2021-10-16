package com.example.flixster;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class DetailActivity extends YouTubeBaseActivity {

  private static final String YOUTUBE_API_KEY = "AIzaSyBC5_pQRGa5fg1UDNTVGlxmGMASbMJI9Vk";
  public static final String VIDEOS_URL = "https://api.themoviedb" +
      ".org/3/movie/%d/videos?api_key=49dc567369f030e2c6ccd9872ba06bf8";

  TextView tv_title;
  TextView tv_overview;
  TextView tv_release_date;
  RatingBar rating_bar;
  YouTubePlayerView youTubePlayerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);

    // set ui element in activity
    tv_title = findViewById(R.id.tv_movie_title);
    tv_overview = findViewById(R.id.tv_overview);
    tv_release_date = findViewById(R.id.tv_movie_release_date);
    rating_bar = findViewById(R.id.rb_movie_rating);
    youTubePlayerView = findViewById(R.id.yt_player);

    // initialize data into ui elements
    Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
    tv_title.setText(movie.getTitle());
    tv_overview.setText(movie.getOverview());
    rating_bar.setRating(movie.getRating().floatValue());
    tv_release_date.setText(movie.getRelease_date());

    AsyncHttpClient client = new AsyncHttpClient();
    client.get(String.format(VIDEOS_URL, movie.getMovie_id()), new JsonHttpResponseHandler() {
      @Override
      public void onSuccess(int statusCode, Headers headers, JSON json) {
        Log.d("Detailed Activity", "onSuccess JSON HTTP HANDLER");
        try {
          JSONArray results = json.jsonObject.getJSONArray("results");
          if (results.length() == 0) {
            return;
          } else {
            String youtube_key = results.getJSONObject(0).getString("key");
            Log.d("DetailActivity", youtube_key);
            initializeYoutube(youtube_key);
          }
        } catch (JSONException e) {
          Log.e("DetailActivity", "Failed to parse JSON array", e);
          e.printStackTrace();
        }
      }

      @Override
      public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
        Log.d("Detailed Activity", "Failed JSON HTTP HANDLER");
      }
    });
  }

  private void initializeYoutube(final String youtube_key) {
    youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
      @Override
      public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        Log.d("Detail Activity", "onSuccess");

        double rating = rating_bar.getRating();
        if (rating >= 5.0) {
          youTubePlayer.loadVideo(youtube_key);
        } else {
          youTubePlayer.cueVideo(youtube_key);
        }
      }

      @Override
      public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Log.d("Detail Activity", "onInitializationFailure");
      }
    });
  }
}