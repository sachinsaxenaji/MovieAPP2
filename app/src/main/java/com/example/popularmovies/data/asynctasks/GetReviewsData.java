package com.example.popularmovies.data.asynctasks;

import android.os.AsyncTask;

import com.example.popularmovies.data.HandleUrls;
import com.example.popularmovies.data.JsonParse;
import com.example.popularmovies.data.modeldata.ReviewData;
import com.example.popularmovies.uianddata.interfaces.AsyncResponseForReviewList;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * GetReviewsData is responsible to get the movie review(s)
 * from the API in the background.
 */

public class GetReviewsData extends AsyncTask<String, Void, ArrayList<ReviewData>> {

    // Interface to pass the list of reviews back to the MovieDetails activity
    private AsyncResponseForReviewList delegate = null;

    /**
     * Get AsyncResponseForReviewList from MovieDetails
     */
    public GetReviewsData(AsyncResponseForReviewList delegate) {
        this.delegate = delegate;
    }

    /**
     * Get reviews from API
     */
    @Override
    protected ArrayList<ReviewData> doInBackground(String... strings) {

        ArrayList<ReviewData> reviewsList = null;

        // Create the URL based on the movie id passed
        URL reviewsDataUrl = HandleUrls.createReviewsUrl(strings[0]);

        String reviewsJsonData;
        try {
            // Get JSON from HttpURLConnection
            reviewsJsonData = HandleUrls.getJsonDataFromHttpResponse(reviewsDataUrl);
            // Parse and return the review(s) in a list
            reviewsList = JsonParse.jsonParseForReviewsList(reviewsJsonData);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if ((reviewsList != null ? reviewsList.size() : 0) != 0) {
            return reviewsList;
        } else {
            return null;
        }
    }

    /**
     * When background task finished, the ArrayList<ReviewData> passed back to MovieDetails
     */
    @Override
    protected void onPostExecute(ArrayList<ReviewData> reviewsList) {
        delegate.processFinishReviewData(reviewsList);
    }

}


