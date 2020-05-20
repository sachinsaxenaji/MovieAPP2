package com.example.popularmovies.uianddata.setdata;

import android.content.Context;
import android.os.Parcelable;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.data.asynctasks.GetReviewsData;
import com.example.popularmovies.data.modeldata.ReviewData;
import com.example.popularmovies.uianddata.adapters.ReviewsRecyclerAdapter;
import com.example.popularmovies.uianddata.interfaces.AsyncResponseForReviewList;

import java.util.ArrayList;

public class SetReviews implements AsyncResponseForReviewList {

    private final Context context;
    private RecyclerView reviewsRecyclerView;
    private Parcelable scrollPosition;

    public SetReviews(Context context) {
        this.context = context;
    }

    public void setData(String id, RecyclerView reviewsRecyclerView, Parcelable scrollPosition) {

        this.reviewsRecyclerView = reviewsRecyclerView;

        this.scrollPosition = scrollPosition;

        GetReviewsData getReviewsData = new GetReviewsData(this);

        getReviewsData.execute(id);
    }

    // Load reviews to RecyclerView
    @Override
    public void processFinishReviewData(ArrayList<ReviewData> reviewData) {

        if (reviewData != null) {

            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

            reviewsRecyclerView.setLayoutManager(layoutManager);

            ReviewsRecyclerAdapter reviewsRecyclerAdapter = new ReviewsRecyclerAdapter(reviewData);

            reviewsRecyclerView.setAdapter(reviewsRecyclerAdapter);

            reviewsRecyclerView.getLayoutManager().onRestoreInstanceState(scrollPosition);
        }

    }
}
