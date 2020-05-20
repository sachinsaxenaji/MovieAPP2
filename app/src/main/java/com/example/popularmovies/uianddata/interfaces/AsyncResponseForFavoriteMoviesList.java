package com.example.popularmovies.uianddata.interfaces;

import com.example.popularmovies.data.modeldata.MovieData;

import java.util.ArrayList;

/**
 * AsyncResponseForFavoriteMoviesList is responsible to pass the movies list from AsyncTask to MovieList
 */

public interface AsyncResponseForFavoriteMoviesList {
    void processFinishFavoriteMoviesData(ArrayList<MovieData> moviesList);
}
