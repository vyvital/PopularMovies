package vyvital.pmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import vyvital.pmovies.data.MovieContract.FavoriteEntry;

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favorites.db";
    private static final int DATABASE_VERSION = 2;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " +
                FavoriteEntry.TABLE_NAME + " (" +
                FavoriteEntry.COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                FavoriteEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                FavoriteEntry.COLUMN_SUMMARY + " TEXT NOT NULL, " +
                FavoriteEntry.COLUMN_YEAR + " TEXT NOT NULL, " +
                FavoriteEntry.COLUMN_RATING + " TEXT NOT NULL, " +
                FavoriteEntry.COLUMN_PICTURE + " TEXT NOT NULL " +
                " );";

        db.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.FavoriteEntry.TABLE_NAME);
        onCreate(db);
    }
}
