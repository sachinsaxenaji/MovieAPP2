package com.example.popularmovies.uianddata.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.data.modeldata.ReviewData;

import java.util.ArrayList;

/**
 * ReviewsRecyclerAdapter is responsible to fill the MovieDetails reviews RecyclerView from the
 * passed ArrayList<ReviewData> list.
 */

public class ReviewsRecyclerAdapter extends RecyclerView.Adapter<ReviewsRecyclerAdapter.ViewHolder> {

    private final ArrayList<ReviewData> reviewsList;

    public ReviewsRecyclerAdapter(ArrayList<ReviewData> reviewsList) {
        this.reviewsList = reviewsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.review, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewsRecyclerAdapter.ViewHolder holder, int position) {

        holder.author.setText(reviewsList.get(position).getAuthor());
        holder.content.setText(reviewsList.get(position).getContent());
    }

    @Override
    public int getItemCount() {

        return reviewsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final TextView author;
        final TextView content;

        ViewHolder(final View itemView) {
            super(itemView);

            author = itemView.findViewById(R.id.author);
            content = itemView.findViewById(R.id.content);

        }
    }

}

