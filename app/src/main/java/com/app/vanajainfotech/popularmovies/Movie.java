package com.app.vanajainfotech.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vishnushankar on 10/07/17
 *
 *
 */

public class Movie implements Parcelable {

    private String posterUrl;
    private String title;
    private String overView;
    private Double vote_Average;
    private String release_date;

    public Movie() {
        super();
    }

    private Movie(Parcel in) {
        posterUrl = in.readString();
        title = in.readString();
        overView = in.readString();
        vote_Average = in.readDouble();
        release_date = in.readString();
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = "http://image.tmdb.org/t/p/" + "w185" + posterUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public Double getVote_Average() {
        return vote_Average;
    }

    public void setVote_Average(Double vote_Average) {
        this.vote_Average = vote_Average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(posterUrl);
        dest.writeString(title);
        dest.writeString(overView);
        dest.writeDouble(vote_Average);
        dest.writeString(release_date);
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
}
