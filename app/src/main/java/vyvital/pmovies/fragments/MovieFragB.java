package vyvital.pmovies.fragments;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.dimorinny.floatingtextbutton.FloatingTextButton;
import vyvital.pmovies.R;
import vyvital.pmovies.Utils.FavoriteUtils;
import vyvital.pmovies.Utils.MovieAdapter;
import vyvital.pmovies.Utils.MovieApi;
import vyvital.pmovies.Utils.ReviewAdapter;
import vyvital.pmovies.data.MovieDbHelper;
import vyvital.pmovies.data.model.Movie;
import vyvital.pmovies.data.model.MovieList;

import static vyvital.pmovies.Utils.MovieAdapter.TAG;
import static vyvital.pmovies.data.MovieContract.FavoriteEntry.COLUMN_ID;
import static vyvital.pmovies.data.MovieContract.FavoriteEntry.TABLE_NAME;
import static vyvital.pmovies.fragments.MovieFragA.BASE_URL;

public class MovieFragB extends Fragment {

    Call<MovieList> call;
    private TextView mTitle;
    private TextView mRelease;
    private TextView mVote;
    private TextView mPlot;
    private TextView contentTxt;
    private String key;
    private ImageView mPoster;
    private Movie movie;
    private FloatingTextButton fav;
    private FloatingTextButton yt;
    private static Retrofit retrofit = null;
    RecyclerView reviewRV;
    private SQLiteDatabase mdb;

    public MovieFragB() {
    }

    public static MovieFragB newInstance() {
        return new MovieFragB();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        }
        MovieDbHelper dbHelper = new MovieDbHelper(getActivity());
        mdb = dbHelper.getWritableDatabase();


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_b, container, false);
        if (getArguments() != null) {
            movie = getArguments().getParcelable(MovieAdapter.MOVIE_KEY);
        }

        if (movie != null) {
            initialize(view);
            if (testNetwork()) connectAndGetApiData();
            else yt.setVisibility(View.INVISIBLE);
            if (isFavorite(movie.getId()))
                fav.setLeftIconDrawable(getResources().getDrawable(R.drawable.ic_star_yellow_24dp));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mPoster.setTransitionName(movie.getTitle());
            }
            Picasso.with(getActivity()).load(MovieAdapter.IMAGE_PATH + movie.getPoster()).noFade().into(mPoster);
            mTitle.setText(movie.getTitle());
            mPlot.setText(movie.getPlot());
            mRelease.setText(movie.getRelease().substring(0, 4));
            mVote.setText(movie.getVote() + "/10");
        }
        return view;
    }

    private boolean isFavorite(String id) {
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_ID + " =?";
        String[] selectionArgs = {id};
        String limit = "1";

        Cursor cursor = mdb.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initialize(View view) {
        reviewRV = view.findViewById(R.id.reviewRV);
        reviewRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        reviewRV.setHasFixedSize(true);
        mPoster = view.findViewById(R.id.m_poster);
        mTitle = view.findViewById(R.id.m_title);
        mPlot = view.findViewById(R.id.m_script);
        mRelease = view.findViewById(R.id.m_release);
        mVote = view.findViewById(R.id.m_vote);
        yt = view.findViewById(R.id.yt_btn);
        fav = view.findViewById(R.id.fav_btn);
        contentTxt = view.findViewById(R.id.m_scripts);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fav.getLeftIconDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.ic_star_black_24dp).getConstantState())) {
                    fav.setLeftIconDrawable(getResources().getDrawable(R.drawable.ic_star_yellow_24dp));
                    FavoriteUtils.setFavorite(movie, getActivity());
                    Toast.makeText(getActivity(), movie.getTitle() + getResources().getString(R.string.fav_add), Toast.LENGTH_SHORT).show();
                } else {
                    fav.setLeftIconDrawable(getResources().getDrawable(R.drawable.ic_star_black_24dp));
                    Toast.makeText(getActivity(), movie.getTitle() + getResources().getString(R.string.fav_remove), Toast.LENGTH_SHORT).show();
                    FavoriteUtils.removeFavorite(movie.getId(), getActivity());
                }

            }
        });
        yt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watchYT(getActivity(), key);
            }
        });
    }

    private void connectAndGetApiData() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        final MovieApi movieApi = retrofit.create(MovieApi.class);
        call = movieApi.getMovieTrailer(movie.getId(), MovieFragA.API_KEY);
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(@NonNull Call<MovieList> call, @NonNull Response<MovieList> response) {
                List<Movie> movies = response.body().getResults();
                if (response.body().getResults().size() == 0) yt.setVisibility(View.INVISIBLE);
                else key = movies.get(0).getKey();
            }

            @Override
            public void onFailure(@NonNull Call<MovieList> call, @NonNull Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
        call = movieApi.getMovieReviews(movie.getId(), MovieFragA.API_KEY);
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(@NonNull Call<MovieList> call, @NonNull Response<MovieList> response) {
                List<Movie> movies = response.body().getResults();
                if (response.body().getResults().size() == 0)
                    contentTxt.setText(R.string.empty_review);
                else {
                    reviewRV.setAdapter(new ReviewAdapter(movies, getActivity()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieList> call, @NonNull Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    private void watchYT(Context context, String key) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + key));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

    public boolean testNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return ((networkInfo != null) && (networkInfo.isConnected()));
    }


}
