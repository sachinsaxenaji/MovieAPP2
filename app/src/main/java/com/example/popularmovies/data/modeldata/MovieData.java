package com.example.popularmovies.data.modeldata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * MovieData is responsible to store one movie data, that is needed.
 * The Parcelable is required to pass the MovieData object from MoviesList
 * to MovieDetails.
 */

public class MovieData implements Parcelable {

    private int id;

    private final double voteAverage;

    private String title;
    private final String posterPath;
    private final String backdropPath;
    private final String overview;
    private final String releaseDate;

    private ArrayList<String> trailers = new ArrayList<>();
    private ArrayList<ReviewData> reviews = new ArrayList<>();

    public MovieData(int id,
                     double voteAverage,
                     String title,
                     String posterPath,
                     String backdropPath,
                     String overview,
                     String releaseDate,
                     ArrayList<String> trailers,
                     ArrayList<ReviewData> reviews) {
        this.id = id;
        this.voteAverage = voteAverage;
        this.title = title;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.trailers = trailers;
        this.reviews = reviews;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeDouble(this.voteAverage);
        dest.writeString(this.title);
        dest.writeString(this.posterPath);
        dest.writeString(this.backdropPath);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeStringList(this.trailers);
        dest.writeTypedList(this.reviews);
    }

    private MovieData(Parcel in) {
        this.id = in.readInt();
        this.voteAverage = in.readDouble();
        this.title = in.readString();
        this.posterPath = in.readString();
        this.backdropPath = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.trailers = in.createStringArrayList();
        this.reviews = in.createTypedArrayList(ReviewData.CREATOR);
    }

    public static final Creator<MovieData> CREATOR = new Creator<MovieData>() {
        @Override
        public MovieData createFromParcel(Parcel source) {
            return new MovieData(source);
        }

        @Override
        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };
}