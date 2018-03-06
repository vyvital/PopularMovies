package vyvital.pmovies.Utils;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import vyvital.pmovies.R;
import vyvital.pmovies.data.model.Movie;
import vyvital.pmovies.fragments.MovieFragA;
import vyvital.pmovies.fragments.MovieFragB;

public class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {
    public static final String MOVIE_KEY = "MOVIE_KEY";
    public static final String TAG = MovieFragA.class.getSimpleName();
    public static final String IMAGE_PATH = "http://image.tmdb.org/t/p/w342//";
    private List<Movie> movies;
    private Context context;

    public MovieAdapter(List<Movie> movies, Context mContext) {
        this.movies = movies;
        this.context = mContext;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_grid_item, parent, false);
        return new MovieHolder(v);
    }

    @Override
    public void onBindViewHolder(final MovieHolder holder, final int position) {
        final String image_url = IMAGE_PATH + movies.get(position).getPoster();
        final Movie ms = movies.get(position);
        Picasso.with(context).load(image_url).networkPolicy(NetworkPolicy.OFFLINE).into(holder.poster, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Picasso.with(context)
                        .load(image_url)
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(holder.poster);
            }
        });
        ViewCompat.setTransitionName(holder.poster, ms.getTitle());
        holder.poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("TRANS", ms.getTitle());
                bundle.putParcelable(MOVIE_KEY, movies.get(position));
                MovieFragB fragment = MovieFragB.newInstance();
                fragment.setArguments(bundle);
                ((AppCompatActivity) context).getSupportFragmentManager()
                        .beginTransaction()
                        .addSharedElement(holder.poster, ViewCompat.getTransitionName(holder.poster))
                        .addToBackStack(TAG)
                        .replace(R.id.content, fragment)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

}
