package vyvital.pmovies.Utils;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vyvital.pmovies.data.MovieContract;
import vyvital.pmovies.data.model.Movie;

import static vyvital.pmovies.Utils.MovieAdapter.TAG;

public class FavoriteUtils {

    public static void setFavorite(Movie movie, Context context) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.FavoriteEntry.COLUMN_ID, movie.getId());
        contentValues.put(MovieContract.FavoriteEntry.COLUMN_TITLE, movie.getTitle());
        contentValues.put(MovieContract.FavoriteEntry.COLUMN_SUMMARY, movie.getPlot());
        contentValues.put(MovieContract.FavoriteEntry.COLUMN_RATING, movie.getVote());
        contentValues.put(MovieContract.FavoriteEntry.COLUMN_PICTURE, movie.getPoster());
        contentValues.put(MovieContract.FavoriteEntry.COLUMN_YEAR, movie.getRelease());
        Uri uri = context.getContentResolver().insert(MovieContract.FavoriteEntry.CONTENT_URI, contentValues);
        if (uri != null) Log.d(TAG, uri.toString());
    }

    public static void removeFavorite(String movieId, Context context) {
        Uri movieUri = MovieContract.FavoriteEntry.movieUriBuilder(movieId);
        if (movieUri != null) Log.d(TAG, movieUri.toString());
        context.getContentResolver().delete(movieUri, null, null);
    }

    public static Cursor getCursor(Context context) {
        return context.getContentResolver().query(MovieContract.FavoriteEntry.CONTENT_URI, null, null, null, null);
    }

    public static List<Movie> getFavorites(Cursor cursor) {

        List<Movie> movies = new ArrayList<>();
        if (cursor != null) {
            try {
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        final Movie movie = new Movie();
                        movie.setTitle(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteEntry.COLUMN_TITLE)));
                        movie.setId(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteEntry.COLUMN_ID)));
                        movie.setPlot(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteEntry.COLUMN_SUMMARY)));
                        movie.setRelease(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteEntry.COLUMN_YEAR)));
                        movie.setVote(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteEntry.COLUMN_RATING)));
                        movie.setPosterPath(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteEntry.COLUMN_PICTURE)));
                        movies.add(movie);
                    }
                } else {
                    movies = Collections.emptyList();
                }
            } finally {
                cursor.close();
            }
        } else {
            movies = Collections.emptyList();
        }
        return movies;
    }

}
