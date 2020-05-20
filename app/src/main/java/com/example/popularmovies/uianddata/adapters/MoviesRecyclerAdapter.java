package com.example.popularmovies.uianddata.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.popularmovies.R;
import com.example.popularmovies.data.HandleUrls;
import com.example.popularmovies.data.modeldata.MovieData;
import com.example.popularmovies.userinterface.DetailActivity;

import java.util.ArrayList;

/** MoviesRecyclerAdapter is responsible to fill the MovieList RecyclerView from the
 *  passed ArrayList<MovieData> list.
 *  And to handle the click on the images to start the MovieDetails activity.
 */

public class MoviesRecyclerAdapter extends RecyclerView.Adapter<MoviesRecyclerAdapter.ViewHolder> {

    private final Context context;

    //Passed movies list
    private final ArrayList<MovieData> moviesList;

    public MoviesRecyclerAdapter(Context context,ArrayList<MovieData> moviesList){
        this.context = context;
        this.moviesList = moviesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.movie_in_grid,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesRecyclerAdapter.ViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load(HandleUrls.createImageUrl(moviesList.get(position).getPosterPath()))
                .into(holder.posterImage);

    }

    @Override
    public int getItemCount() {

        return moviesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        final ImageView posterImage;

        ViewHolder(final View itemView) {
            super(itemView);

            posterImage = itemView.findViewById(R.id.movies_list_movie_poster);
            // Set click listener to the images
            posterImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Check internet connection
                    if(checkInternetConnection()) {
                        Intent intent = new Intent(view.getContext(), DetailActivity.class);
                        MovieData currentMovieDetails = moviesList.get(getAdapterPosition());
                        // Add actual movie data to intent
                        intent.putExtra(view.getContext().getString(R.string.passing_movie_parcelable_key), currentMovieDetails);
                        // Set animation between the two activity
                        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) view.getContext(), posterImage, "animated");
                        // Start MovieDetails activity
                        view.getContext().startActivity(intent, options.toBundle());
                    }else{
                        Toast.makeText(context,"No internet connection.",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private boolean checkInternetConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
