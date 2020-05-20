package com.example.popularmovies.uianddata.interfaces;

import com.example.popularmovies.data.modeldata.MovieData;

import java.util.ArrayList;

/**
 * AsyncResponseForMoviesList is responsible to pass the movies list from AsyncTask to MovieList
 */

public interface AsyncResponseForMoviesList {
    void processFinishMovieData(ArrayList<MovieData> movieData);
}
