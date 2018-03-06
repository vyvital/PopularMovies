package vyvital.pmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {

    public static final String AUTHORITY = "vyvital.pmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_FAV = "favorites";

    public static final class FavoriteEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAV).build();
        public static final String TABLE_NAME = "favorites";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_SUMMARY = "summary";
        public static final String COLUMN_YEAR = "year";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_PICTURE = "picture";

        public static Uri movieUriBuilder(String id) {
            Uri uri = CONTENT_URI.buildUpon().appendPath(id).build();
            return uri;
        }
    }
}
