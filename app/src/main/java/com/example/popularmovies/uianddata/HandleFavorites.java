package com.example.popularmovies.uianddata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.popularmovies.data.databasedata.FavoriteContract;

import java.util.ArrayList;

/**
 * HandleFavorites is responsible to handle favorite database
 */

public class HandleFavorites {

    private final Context context;

    public HandleFavorites(Context context) {
        this.context = context;
    }

    public void saveFavoriteToDatabase(String movieId, String title) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(FavoriteContract.FavoritesEntry.COLUMN_MOVIE_ID, movieId);
        contentValues.put(FavoriteContract.FavoritesEntry.COLUMN_MOVIE_TITLE, title);
        context.getContentResolver().insert(FavoriteContract.FavoritesEntry.CONTENT_URI, contentValues);

    }

    public void deleteFavoriteFromDatabase(String movieId) {

        String selection = FavoriteContract.FavoritesEntry.COLUMN_MOVIE_ID + "=?";
        String[] selectionArgs = {movieId};
        context.getContentResolver().delete(FavoriteContract.FavoritesEntry.CONTENT_URI, selection, selectionArgs);

    }

    public boolean checkIfMovieIsFavorite(String movieId) {
        Cursor cursor = context.getContentResolver().query(
                FavoriteContract.FavoritesEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                if (movieId.equals(
                        cursor.getString(
                                cursor.getColumnIndex(
                                        FavoriteContract.FavoritesEntry.COLUMN_MOVIE_ID)))) {
                    return true;
                }
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return false;
    }

    public ArrayList<String> getFavoriteMoviesId() {

        ArrayList<String> movieIdList = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(
                FavoriteContract.FavoritesEntry.CONTENT_URI,
                null,
                null,
                null,
                null);

        if (cursor != null) {
            while (cursor.moveToNext()) {

                movieIdList.add(cursor.getString(
                        cursor.getColumnIndex(
                                FavoriteContract.FavoritesEntry.COLUMN_MOVIE_ID)));

            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return movieIdList;
    }

}
