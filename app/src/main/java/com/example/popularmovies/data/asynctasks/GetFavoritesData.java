package com.example.popularmovies.data.asynctasks;

import android.os.AsyncTask;

import com.example.popularmovies.data.HandleUrls;
import com.example.popularmovies.data.JsonParse;
import com.example.popularmovies.data.modeldata.MovieData;
import com.example.popularmovies.uianddata.interfaces.AsyncResponseForFavoriteMoviesList;

import java.net.URL;
import java.util.ArrayList;


public class GetFavoritesData extends AsyncTask<ArrayList<String>, Void, ArrayList<MovieData>> {

    // Interface to pass movies list back to MovieList activity
    private AsyncResponseForFavoriteMoviesList delegate = null;

    /**
     * Get AsyncResponseForFavoriteMoviesList from MovieList
     */
    public GetFavoritesData(AsyncResponseForFavoriteMoviesList delegate) {
        this.delegate = delegate;
    }

    /**
     * Get movies id from database and movie data from API
     */
    @SafeVarargs
    @Override
    protected final ArrayList<MovieData> doInBackground(ArrayList<String>... stringList) {

        ArrayList<MovieData> moviesList = new ArrayList<>();

        ArrayList<String> moviesId = stringList[0];

        for (int i = 0; i < moviesId.size(); i++) {

            URL movieDataUrl = HandleUrls.createSingleMovieUrl(moviesId.get(i));

            try {
                // Get JSON from HttpURLConnection
                String movieJsonData = HandleUrls.getJsonDataFromHttpResponse(movieDataUrl);
                // Return the movie data in as MovieData
                moviesList.add(JsonParse.jsonParseForMovie(movieJsonData));

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }

        return moviesList;

    }

    /**
     * When background task finished, the ArrayList<MovieData> passed back to MovieList
     */
    @Override
    protected void onPostExecute(ArrayList<MovieData> moviesList) {
        delegate.processFinishFavoriteMoviesData(moviesList);
    }
}

