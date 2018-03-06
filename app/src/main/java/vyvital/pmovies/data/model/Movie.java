package vyvital.pmovies.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable {

    private String title;

    @SerializedName("content")
    private String content;

    @SerializedName("author")
    private String author;

    @SerializedName("id")
    private String id;

    @SerializedName("release_date")
    private String release;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("vote_average")
    private String vote;

    @SerializedName("overview")
    private String plot;

    @SerializedName("key")
    private String key;

    public Movie(String title, String release, String posterPath, String vote, String plot, String id) {
        this.title = title;
        this.release = release;
        this.posterPath = posterPath;
        this.vote = vote;
        this.plot = plot;
        this.id = id;

    }

    public Movie() {

    }

    protected Movie(Parcel in) {
        title = in.readString();
        release = in.readString();
        posterPath = in.readString();
        vote = in.readString();
        plot = in.readString();
        id = in.readString();
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getKey() {
        return key;
    }

    public String getContent() {
        return content;
    }

    public String getPoster() {
        return posterPath;
    }

    public String getVote() {
        return vote;
    }

    public String getPlot() {
        return plot;
    }

    public String getRelease() {
        return release;
    }

    public String getAuthor() {
        return author;
    }

    public String getId() {
        return id;
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
        dest.writeString(vote);
        dest.writeString(plot);
        dest.writeString(id);
    }
}