package com.example.popularmovies.userinterface;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.data.asynctasks.GetFavoritesData;
import com.example.popularmovies.data.asynctasks.GetMoviesData;
import com.example.popularmovies.data.modeldata.MovieData;
import com.example.popularmovies.uianddata.HandleFavorites;
import com.example.popularmovies.uianddata.interfaces.AsyncResponseForFavoriteMoviesList;
import com.example.popularmovies.uianddata.interfaces.AsyncResponseForMoviesList;
import com.example.popularmovies.uianddata.adapters.MoviesRecyclerAdapter;
import com.example.popularmovies.uianddata.SetVisibility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * MoviesList is responsible to show movies list and to select the
 * popular or top rated ones.
 */

public class MainActivity extends AppCompatActivity implements AsyncResponseForMoviesList, AsyncResponseForFavoriteMoviesList {

    @BindView(R.id.movies_list_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.movies_list_progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.movies_list_no_connection_text_view)
    TextView noConnection;

    private GridLayoutManager gridLayoutManager;

    private SetVisibility setVisibility;

    private ArrayList<MovieData> moviesList;

    private String currentList;

    private int numberOfFavoriteMovies = 0;

    private Parcelable scrollPosition = null;

    private ViewTreeObserver.OnGlobalLayoutListener viewListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ButterKnife.bind(this);

        setVisibility = new SetVisibility();

        currentList = getString(R.string.movie_type_popular);

        // Set RecyclerView column numbers
        setRecyclerView();

        viewListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                setVisibility.setInvisible(progressBar);
                setVisibility.setInvisible(noConnection);
                setVisibility.setVisible(recyclerView);
                recyclerView.getLayoutManager().onRestoreInstanceState(scrollPosition);
            }
        };

        if (checkInternetConnection()) {

            // Check if orientation has changed
            if (savedInstanceState != null) {

                scrollPosition = savedInstanceState.getParcelable("scrollPosition");

                currentList = savedInstanceState.getString(getString(R.string.on_saved_instance_state_current_list_type));

                if ("favorite".equals(currentList)) {

                    moviesList = savedInstanceState.getParcelableArrayList(getString(R.string.on_saved_instance_state_movies_list_key));

                    numberOfFavoriteMovies = savedInstanceState.getInt(getString(R.string.on_saved_instance_state_number_of_favorite_movies), 0);

                    HandleFavorites handleFavorites = new HandleFavorites(this);

                    ArrayList<String> favoriteMoviesIdList = handleFavorites.getFavoriteMoviesId();

                    // Check if favorite movies database changed
                    if (numberOfFavoriteMovies != favoriteMoviesIdList.size()) {
                        numberOfFavoriteMovies = favoriteMoviesIdList.size();
                        getMoviesList(getString(R.string.movie_type_favorite));
                    } else {
                        if (moviesList != null) {
                            setVisibility.setInvisible(recyclerView);
                            setVisibility.setVisible(progressBar);
                            loadMoviesToRecyclerView();
                        } else {
                            getMoviesList(getString(R.string.movie_type_popular));
                        }
                    }
                } else {
                    moviesList = savedInstanceState.getParcelableArrayList(getString(R.string.on_saved_instance_state_movies_list_key));
                    if (moviesList != null) {
                        setVisibility.setInvisible(recyclerView);
                        setVisibility.setVisible(progressBar);
                        loadMoviesToRecyclerView();
                    } else {
                        getMoviesList(getString(R.string.movie_type_popular));
                    }
                }

            } else {
                getMoviesList(getString(R.string.movie_type_popular));
            }
        } else {
            setVisibility.setInvisible(recyclerView);
            setVisibility.setInvisible(progressBar);
            setVisibility.setVisible(noConnection);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    // Create options menu to select popular or top rated movies
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.popular_movies:
                if (checkInternetConnection()) {
                    getMoviesList(getString(R.string.movie_type_popular));
                } else {
                    setVisibility.setInvisible(recyclerView);
                    setVisibility.setInvisible(progressBar);
                    setVisibility.setVisible(noConnection);
                }
                break;
            case R.id.top_rated_movies:
                if (checkInternetConnection()) {
                    getMoviesList(getString(R.string.movie_type_top_rated));
                } else {
                    setVisibility.setInvisible(recyclerView);
                    setVisibility.setInvisible(progressBar);
                    setVisibility.setVisible(noConnection);
                }
                break;
            case R.id.favorite_movies:
                if (checkInternetConnection()) {
                    getMoviesList(getString(R.string.movie_type_favorite));
                } else {
                    setVisibility.setInvisible(recyclerView);
                    setVisibility.setInvisible(progressBar);
                    setVisibility.setVisible(noConnection);
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    // Set RecyclerView grid, columns
    private void setRecyclerView() {

        gridLayoutManager = new GridLayoutManager(this, numberOfColumns());

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(gridLayoutManager);

    }

    private int numberOfColumns() {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        // Width of the poster
        int widthDivider = 500;
        int width = displayMetrics.widthPixels;
        int columnsNumber = width / widthDivider;
        if (columnsNumber < 2) return 2;

        return columnsNumber;
    }

    // Get movies list in the background
    private void getMoviesList(String listType) {

        setVisibility.setInvisible(recyclerView);
        setVisibility.setVisible(progressBar);

        currentList = listType;

        if (!listType.equals(getString(R.string.movie_type_favorite))) {

            GetMoviesData getMoviesData = new GetMoviesData(this);

            getMoviesData.execute(listType);

        } else {

            HandleFavorites handleFavorites = new HandleFavorites(this);

            ArrayList<String> favoriteMoviesIdList = handleFavorites.getFavoriteMoviesId();

            numberOfFavoriteMovies = favoriteMoviesIdList.size();

            GetFavoritesData getFavoriteData = new GetFavoritesData(this);

            getFavoriteData.execute(favoriteMoviesIdList);

        }
    }

    // AsyncResponseForMoviesList interface to handle getting back moviesList
    @Override
    public void processFinishMovieData(ArrayList<MovieData> moviesList) {
        if (moviesList != null) {
            this.moviesList = moviesList;
            loadMoviesToRecyclerView();

        }
    }

    // AsyncResponseForFavoriteMoviesList interface to handle getting back moviesList
    @Override
    public void processFinishFavoriteMoviesData(ArrayList<MovieData> moviesList) {
        if (moviesList != null) {
            this.moviesList = moviesList;
            loadMoviesToRecyclerView();
        }
    }


    // Load movies list to the RecyclerView
    private void loadMoviesToRecyclerView() {

        MoviesRecyclerAdapter moviesRecyclerAdapter = new MoviesRecyclerAdapter(this, moviesList);

        recyclerView.setAdapter(moviesRecyclerAdapter);

        // Set scroll position after orientation change
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(viewListener);

        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(viewListener);
                scrollPosition = null;
            }
        }, 500);
    }

    // Save movies list if orientation changes
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(getString(R.string.on_saved_instance_state_current_list_type), currentList);
        outState.putInt(getString(R.string.on_saved_instance_state_number_of_favorite_movies), numberOfFavoriteMovies);
        outState.putParcelableArrayList(getString(R.string.on_saved_instance_state_movies_list_key), moviesList);
        if (checkInternetConnection()) {
            outState.putParcelable("scrollPosition", recyclerView.getLayoutManager().onSaveInstanceState());
        }
    }

    private boolean checkInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onResume() {

        // Check if favorite movies database size changed
        if (currentList != null) {
            if ((currentList.equals(getString(R.string.movie_type_favorite)))) {

                HandleFavorites handleFavorites = new HandleFavorites(this);

                ArrayList<String> favoriteMoviesIdList = handleFavorites.getFavoriteMoviesId();

                if (numberOfFavoriteMovies != favoriteMoviesIdList.size()) {
                    numberOfFavoriteMovies = favoriteMoviesIdList.size();

                    setVisibility.setInvisible(recyclerView);
                    setVisibility.setVisible(progressBar);
                    setRecyclerView();
                    getMoviesList(getString(R.string.movie_type_favorite));
                    currentList = getString(R.string.movie_type_favorite);
                }
            }
        }
        super.onResume();
    }

}
