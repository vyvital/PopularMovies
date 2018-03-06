package vyvital.pmovies.Utils;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vyvital.pmovies.R;
import vyvital.pmovies.data.model.Movie;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private List<Movie> mData;
    private Context context;

    // data is passed into the constructor
    public ReviewAdapter(List<Movie> data, Context mContext) {
        this.context = mContext;
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ViewHolder(v);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie review = mData.get(position);
        holder.content.setText(review.getContent());
        holder.author.setText(review.getAuthor());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView author;
        TextView content;

        ViewHolder(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.review_content);
            author = itemView.findViewById(R.id.author);

        }
    }

}
