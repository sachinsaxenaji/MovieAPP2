package com.example.popularmovies.data;

import com.example.popularmovies.data.modeldata.MovieData;
import com.example.popularmovies.data.modeldata.ReviewData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * JsonParse is responsible to parse the JSON strings to object[s]
 */

public class JsonParse {

    /**
     * Parse the JSON string to MovieData to store the movie required data
     * and returns
     */
    public static MovieData jsonParseForMovie(String jsonString) throws JSONException {

        JSONObject mainData = new JSONObject(jsonString);

        return new MovieData(
                mainData.getInt("id"),
                mainData.getDouble("vote_average"),
                mainData.getString("title"),
                mainData.getString("poster_path"),
                mainData.getString("backdrop_path"),
                mainData.getString("overview"),
                mainData.getString("release_date"),
                null,
                null);

    }

    /**
     * Parse the JSON string to ArrayList<MovieData> to store the movies required data
     * and returns the list
     */
    public static ArrayList<MovieData> jsonParseForMoviesList(String jsonString) throws JSONException {

        ArrayList<MovieData> moviesList = new ArrayList<>();

        JSONObject mainData = new JSONObject(jsonString);

        JSONArray moviesJsonArray = mainData.getJSONArray("results");

        for (int i = 0; i < moviesJsonArray.length(); i++) {

            JSONObject movie = moviesJsonArray.getJSONObject(i);

            MovieData movieData = new MovieData(
                    movie.getInt("id"),
                    movie.getDouble("vote_average"),
                    movie.getString("title"),
                    movie.getString("poster_path"),
                    movie.getString("backdrop_path"),
                    movie.getString("overview"),
                    movie.getString("release_date"),
                    null,
                    null);
            moviesList.add(movieData);

        }

        return moviesList;
    }

    public static ArrayList<String> jsonParseForTrailersList(String trailersJsonData) throws JSONException {

        ArrayList<String> trailersList = new ArrayList<>();

        JSONObject trailerData = new JSONObject(trailersJsonData);

        JSONArray trailerJsonArray = trailerData.getJSONArray("results");

        for (int i = 0; i < trailerJsonArray.length(); i++) {

            JSONObject trailer = trailerJsonArray.getJSONObject(i);

            if (trailer.getString("site").equals("YouTube")) {
                trailersList.add(trailer.getString("key"));
            }

        }

        return trailersList;
    }

    public static ArrayList<ReviewData> jsonParseForReviewsList(String reviewsJsonData) throws JSONException {

        ArrayList<ReviewData> reviewsList = new ArrayList<>();

        JSONObject reviewsData = new JSONObject(reviewsJsonData);

        JSONArray reviewsJsonArray = reviewsData.getJSONArray("results");

        for (int i = 0; i < reviewsJsonArray.length(); i++) {

            JSONObject trailer = reviewsJsonArray.getJSONObject(i);

            ReviewData reviewData = new ReviewData(
                    trailer.getString("author"),
                    trailer.getString("content")
            );

            reviewsList.add(reviewData);
        }

        return reviewsList;

    }
}
