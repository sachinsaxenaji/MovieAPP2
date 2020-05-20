package com.example.popularmovies.userinterface;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.popularmovies.R;
import com.example.popularmovies.data.HandleUrls;
import com.example.popularmovies.data.modeldata.MovieData;
import com.example.popularmovies.uianddata.HandleFavorites;
import com.example.popularmovies.uianddata.setdata.SetReviews;
import com.example.popularmovies.uianddata.setdata.SetTrailers;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * MovieDetails is responsible to show the passed movie required data
 */

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.main_scroll_view)
    ScrollView scrollView;

    @BindView(R.id.movie_detail_backdrop)
    ImageView backdropImage;
    @BindView(R.id.movie_detail_poster)
    ImageView posterImage;

    @BindView(R.id.movie_detail_title)
    TextView titleText;
    @BindView(R.id.movie_detail_vote_average)
    TextView voteText;
    @BindView(R.id.movie_detail_overview)
    TextView overviewText;
    @BindView(R.id.movie_detail_date)
    TextView dateText;

    @BindView(R.id.movie_trailer)
    RecyclerView trailers;
    @BindView(R.id.movie_reviews)
    RecyclerView reviews;

    @BindView(R.id.favorite_button)
    ImageButton favorite;

    private Unbinder unbinder;

    private HandleFavorites handleFavorites;

    Parcelable trailerPosition;

    Parcelable reviewPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        unbinder = ButterKnife.bind(this);

        handleFavorites = new HandleFavorites(this);

        if (checkInternetConnection()) {
            Bundle data = getIntent().getExtras();
            // If passed data is not null load it
            if (data != null) {
                // Get passed data
                final MovieData movieData = data.getParcelable(getString(R.string.passing_movie_parcelable_key));
                if (movieData != null) {
                    // Handle orientation changes
                    if (savedInstanceState != null) {
                        final int[] position = savedInstanceState.getIntArray("scroll_position");
                        if (position != null)
                            scrollView.post(new Runnable() {
                                public void run() {
                                    scrollView.scrollTo(position[0], position[1]);
                                }
                            });
                        trailerPosition = savedInstanceState.getParcelable("trailer_position");
                        reviewPosition = savedInstanceState.getParcelable("review_position");
                    } else {
                        scrollView.post(new Runnable() {
                            @Override
                            public void run() {
                                scrollView.fullScroll(ScrollView.FOCUS_UP);
                            }
                        });
                    }

                    Glide.with(this)
                            .load(HandleUrls.createImageUrl(movieData.getBackdropPath()))
                            .into(backdropImage);
                    Glide.with(this)
                            .load(HandleUrls.createImageUrl(movieData.getPosterPath()))
                            .into(posterImage);
                    titleText.setText(movieData.getTitle());
                    voteText.setText(getString(R.string.vote_average_string, Double.toString(movieData.getVoteAverage())));
                    overviewText.setText(movieData.getOverview());
                    dateText.setText(getString(R.string.release_date_string, movieData.getReleaseDate()));

                    // Get trailers
                    SetTrailers setTrailers = new SetTrailers(this);

                    setTrailers.setData(String.valueOf(movieData.getId()), trailers, trailerPosition);

                    // Get reviews
                    SetReviews setReviews = new SetReviews(this);

                    setReviews.setData(String.valueOf(movieData.getId()), reviews, reviewPosition);

                    // Set favorite icon
                    if (handleFavorites.checkIfMovieIsFavorite(String.valueOf(movieData.getId()))) {
                        favorite.setImageResource(R.mipmap.ic_favorite_white_24dp);
                    } else {
                        favorite.setImageResource(R.mipmap.ic_favorite_border_white_24dp);
                    }

                    // Add(delete) movie to favorite
                    favorite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (handleFavorites.checkIfMovieIsFavorite(String.valueOf(movieData.getId()))) {

                                handleFavorites.deleteFavoriteFromDatabase(String.valueOf(movieData.getId()));

                                favorite.setImageResource(R.mipmap.ic_favorite_border_white_24dp);
                            } else {

                                handleFavorites.saveFavoriteToDatabase(String.valueOf(movieData.getId()), movieData.getTitle());

                                favorite.setImageResource(R.mipmap.ic_favorite_white_24dp);
                            }
                        }
                    });

                } else {
                    // If passed data is null show message
                    Toast.makeText(this, getString(R.string.no_passed_movie_in_details), Toast.LENGTH_SHORT).show();
                }
            } else {
                // If passed data is null show message
                Toast.makeText(this, getString(R.string.no_passed_movie_in_details), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, getString(R.string.movie_detail_no_connection_text), Toast.LENGTH_LONG).show();
            finish();
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

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray("scroll_position",
                new int[]{scrollView.getScrollX(), scrollView.getScrollY()});
        outState.putParcelable("trailer_position", trailers.getLayoutManager().onSaveInstanceState());
        outState.putParcelable("review_position", reviews.getLayoutManager().onSaveInstanceState());
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
