package vyvital.pmovies.Utils;

import android.content.ActivityNotFoundException;
import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import vyvital.pmovies.R;
import vyvital.pmovies.data.model.Movie;
import vyvital.pmovies.fragments.MovieFragB;


public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {
    public static final String YOUTUBE_PATH = "https://img.youtube.com/vi/";
    private List<Movie> mData;
    private Context context;

    // data is passed into the constructor
    public TrailerAdapter(List<Movie> data, Context mContext) {
        this.context = mContext;
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item, parent, false);
        return new ViewHolder(v);
    }



    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        final String image_url = YOUTUBE_PATH + mData.get(position).getKey() + "/0.jpg";
        Picasso.with(context).load(image_url).networkPolicy(NetworkPolicy.OFFLINE).into(holder.img, new Callback() {
            @Override
            public void onSuccess() {
            }
            @Override
            public void onError() {
                Picasso.with(context)
                        .load(image_url)
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(holder.img);
            }
        });
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + mData.get(position).getKey()));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + mData.get(position).getKey()));
                try {
                    context.startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    context.startActivity(webIntent);
                }
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.roundedImageView);
        }
    }



}