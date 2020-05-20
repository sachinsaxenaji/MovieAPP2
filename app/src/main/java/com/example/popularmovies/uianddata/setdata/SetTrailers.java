package com.example.popularmovies.uianddata.setdata;

import android.content.Context;
import android.os.Parcelable;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.data.asynctasks.GetTrailersData;
import com.example.popularmovies.uianddata.adapters.TrailersRecyclerAdapter;
import com.example.popularmovies.uianddata.interfaces.AsyncResponseForTrailerList;

import java.util.ArrayList;

public class SetTrailers implements AsyncResponseForTrailerList {

    private final Context context;
    private RecyclerView trailersRecyclerView;
    private Parcelable scrollPosition;

    public SetTrailers(Context context) {
        this.context = context;
    }

    public void setData(String id, RecyclerView trailersRecyclerView, Parcelable scrollPosition) {

        this.trailersRecyclerView = trailersRecyclerView;

        this.scrollPosition = scrollPosition;

        GetTrailersData getTrailersData = new GetTrailersData(this);

        getTrailersData.execute(id);

    }

    @Override
    public void processFinishTrailerData(ArrayList<String> trailerData) {

        if (trailerData != null) {

            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

            trailersRecyclerView.setLayoutManager(layoutManager);

            TrailersRecyclerAdapter trailersRecyclerAdapter = new TrailersRecyclerAdapter(context, trailerData);

            trailersRecyclerView.setAdapter(trailersRecyclerAdapter);

            trailersRecyclerView.getLayoutManager().onRestoreInstanceState(scrollPosition);
        }
    }
}
