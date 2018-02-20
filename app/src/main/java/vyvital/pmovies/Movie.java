package vyvital.pmovies;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable {

    private String title;

    @SerializedName("release_date")
    private String release;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("vote_average")
    private Double vote;

    @SerializedName("overview")
    private String plot;

    public Movie(String title, String release, String posterPath, Double vote, String plot) {
        this.title = title;
        this.release = release;
        this.posterPath = posterPath;
        this.vote = vote;
        this.plot = plot;
    }

    protected Movie(Parcel in) {
        title = in.readString();
        release = in.readString();
        posterPath = in.readString();
        vote = in.readDouble();
        plot = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return posterPath;
    }

    public Double getVote() {
        return vote;
    }

    public String getPlot() {
        return plot;
    }

    public String getRelease() {
        return release;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(release);
        dest.writeString(posterPath);
        dest.writeDouble(vote);
        dest.writeString(plot);
    }
}