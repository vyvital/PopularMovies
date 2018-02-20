package vyvital.pmovies.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import vyvital.pmovies.Movie;
import vyvital.pmovies.R;
import vyvital.pmovies.Utils.MovieAdapter;

public class MovieFragB extends Fragment {

    private TextView mTitle;
    private TextView mRelease;
    private TextView mVote;
    private TextView mPlot;
    private ImageView mPoster;
    private Movie movie;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_b, container, false);
        if (getArguments() != null) {
            movie = getArguments().getParcelable(MovieAdapter.MOVIE_KEY);
        }

        if (movie != null) {
            initialize(view);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mPoster.setTransitionName(movie.getTitle());
            }
            Picasso.with(getActivity()).load(MovieAdapter.IMAGE_PATH + movie.getPoster()).noFade().into(mPoster);
            mTitle.setText(movie.getTitle());
            mPlot.setText(movie.getPlot());
            mRelease.setText(movie.getRelease());
            mVote.setText(String.valueOf(movie.getVote()) + "/10");
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initialize(View view) {
        mPoster = view.findViewById(R.id.m_poster);
        mTitle = view.findViewById(R.id.m_title);
        mPlot = view.findViewById(R.id.m_script);
        mRelease = view.findViewById(R.id.m_release);
        mVote = view.findViewById(R.id.m_vote);
    }

}
