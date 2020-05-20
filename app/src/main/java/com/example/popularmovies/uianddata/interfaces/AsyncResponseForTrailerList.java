package com.example.popularmovies.uianddata.interfaces;

import java.util.ArrayList;

/** AsyncResponseForTrailerList is responsible to pass the movies list from AsyncTask to MovieDetails */

public interface AsyncResponseForTrailerList {
    void processFinishTrailerData(ArrayList<String> trailerData);
}
