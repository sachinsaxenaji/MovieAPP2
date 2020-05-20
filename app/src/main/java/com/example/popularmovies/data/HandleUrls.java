package com.example.popularmovies.data;

import android.net.Uri;

import com.example.popularmovies.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * HandleUrls is responsible to create and use URLs
 */

public class HandleUrls {

    /**
     * Method to create any downloadable image with a width of 500, from the imagePath
     */
    public static String createImageUrl(String imagePath) {
        return "https://image.tmdb.org/t/p/w500" + imagePath;
    }

    /**
     * Create movies list url based on selectingType string("popular","top_rated")
     */
    public static URL createMovieListUrl(String selectingType) {

        URL movieListUrl = null;

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(selectingType)
                .appendQueryParameter("api_key", BuildConfig.API_KEY);

        try {
            movieListUrl = new URL(builder.build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return movieListUrl;
    }

    /**
     * Create single movie url based on movieId string
     */
    public static URL createSingleMovieUrl(String movieId) {

        URL movieUrl = null;

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(movieId)
                .appendQueryParameter("api_key", BuildConfig.API_KEY);

        try {
            movieUrl = new URL(builder.build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return movieUrl;
    }

    /**
     * Create trailers list url based on movie id
     */
    public static URL createTrailerUrl(String id) {

        URL trailersUrl = null;

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(id)
                .appendPath("videos")
                .appendQueryParameter("api_key", BuildConfig.API_KEY);

        try {
            trailersUrl = new URL(builder.build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return trailersUrl;
    }


    public static String createYoutubeThumbnailUrl(String id) {

        String thumbnailUrl;

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("img.youtube.com")
                .appendPath("vi")
                .appendPath(id)
                .appendPath("hqdefault.jpg");

        thumbnailUrl = builder.build().toString();

        return thumbnailUrl;
    }

    /**
     * Create the YouTube trailers Url
     */
    public static String createYoutubeTrailerUrl(String id) {

        return "https://www.youtube.com/watch?v=" + id;
    }

    /**
     * Create reviews list url based on movie id
     */
    public static URL createReviewsUrl(String id) {

        URL reviewsUrl = null;

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(id)
                .appendPath("reviews")
                .appendQueryParameter("api_key", BuildConfig.API_KEY);

        try {
            reviewsUrl = new URL(builder.build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return reviewsUrl;

    }

    /**
     * Returns the JSON String from a HttpURLConnection
     */
    public static String getJsonDataFromHttpResponse(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = urlConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
