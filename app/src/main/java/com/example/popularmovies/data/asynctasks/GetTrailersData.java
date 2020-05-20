package com.example.popularmovies.data.asynctasks;

import android.os.AsyncTask;

import com.example.popularmovies.data.HandleUrls;
import com.example.popularmovies.data.JsonParse;
import com.example.popularmovies.uianddata.interfaces.AsyncResponseForTrailerList;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * GetTrailersData is responsible to get the movie trailer(s)
 * from the API in the background.
 */

public class GetTrailersData extends AsyncTask<String, Void, ArrayList<String>> {

    // Interface to pass the list of trailer(s) id back to the MovieDetails activity
    private AsyncResponseForTrailerList delegate = null;

    /**
     * Get AsyncResponseForTrailerList from MovieDetails
     */
    public GetTrailersData(AsyncResponseForTrailerList delegate) {
        this.delegate = delegate;
    }

    /**
     * Get trailer(s) id from API
     */
    @Override
    protected ArrayList<String> doInBackground(String... strings) {

        ArrayList<String> trailersList = null;

        // Create the URL based on the movie id passed
        URL trailersDataUrl = HandleUrls.createTrailerUrl(strings[0]);

        String trailersJsonData;
        try {
            // Get JSON from HttpURLConnection
            trailersJsonData = HandleUrls.getJsonDataFromHttpResponse(trailersDataUrl);
            // Parse and return the trailer(s) id in a list
            trailersList = JsonParse.jsonParseForTrailersList(trailersJsonData);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return trailersList;
    }

    /**
     * When background task finished, the ArrayList<String> with the ids passed back to MovieDetails
     */
    @Override
    protected void onPostExecute(ArrayList<String> trailersList) {
        delegate.processFinishTrailerData(trailersList);
    }
}
