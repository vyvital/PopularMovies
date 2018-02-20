package vyvital.pmovies.Utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import vyvital.pmovies.R;

class MovieHolder extends RecyclerView.ViewHolder {

    ImageView poster;

    MovieHolder(View itemView) {
        super(itemView);
        poster = itemView.findViewById(R.id.moviePic);
    }
}
