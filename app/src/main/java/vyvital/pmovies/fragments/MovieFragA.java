package vyvital.pmovies.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vyvital.pmovies.*;
import vyvital.pmovies.R;
import vyvital.pmovies.Utils.MovieAdapter;
import vyvital.pmovies.Utils.MovieApi;

public class MovieFragA extends Fragment {
    int sort = 0;
    Call<MovieList> call;
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;
    private final static String API_KEY = "e49c7fcd1c738a50afd1c8c6ca9d1fe9";
    RecyclerView movieRV;

    public MovieFragA() {

    }

    public static MovieFragA newInstance() {
        return new MovieFragA();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_a, container, false);
        View emptyView = rootView.findViewById(R.id.empty_view);
        setHasOptionsMenu(true);
        movieRV = rootView.findViewById(R.id.movieRV);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            movieRV.setLayoutManager(new GridLayoutManager(getContext(),5));
        else  movieRV.setLayoutManager(new GridLayoutManager(getContext(),3));
        movieRV.setHasFixedSize(true);
        if (testNetwork()){
        connectAndGetApiData();
        } else {
            movieRV.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        return rootView;

    }

    private void connectAndGetApiData() {
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        final MovieApi movieApi = retrofit.create(MovieApi.class);
        if (sort == 0) {
            call = movieApi.getTopRatedMovies(API_KEY);
        }
        else {
            call = movieApi.getTopPopular(API_KEY);
        }
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(@NonNull Call<MovieList> call, @NonNull Response<MovieList> response) {
                List<Movie> movies = response.body().getResults();
                movieRV.setAdapter(new MovieAdapter(movies,getActivity()));
            }

            @Override
            public void onFailure(@NonNull Call<MovieList> call, @NonNull Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public boolean testNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return ((networkInfo != null) && (networkInfo.isConnected()));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu , MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_main,menu);
        super.onCreateOptionsMenu(menu,inflater);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sortByPop){
            sort = 1;
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();
            return true;
        }
        if (id == R.id.sortByRating){
            sort = 0;
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
