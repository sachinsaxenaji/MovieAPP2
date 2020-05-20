package com.example.popularmovies.uianddata.interfaces;

import com.example.popularmovies.data.modeldata.ReviewData;

import java.util.ArrayList;

/** AsyncResponseForReviewList is responsible to pass the movies list from AsyncTask to MovieDetails */

public interface AsyncResponseForReviewList {
    void processFinishReviewData(ArrayList<ReviewData> reviewData);
}
