package com.example.popularmovies.data.databasedata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class FavoritesDbHelper extends SQLiteOpenHelper {

    // Constructor
    public FavoritesDbHelper(Context context) {
        super(context, FavoriteContract.DATABASE_NAME, null, FavoriteContract.DATABASE_VERSION);
    }


    // Called when the tasks database is created for the first time.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FavoriteContract.FavoritesEntry.CREATE_TABLE_FAVORITES);
    }

    // This method discards the old table of data and calls onCreate to recreate a new one.
    // This only occurs when the version number for this database (DATABASE_VERSION) is incremented.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(FavoriteContract.FavoritesEntry.DELETE_TABLE_FAVORITES);
        onCreate(db);
    }
}
