package com.android.lior.lastchoice.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lior on 12/25/2017.
 */

public class MovieObject implements Parcelable {
    public  String MovieName = null;
    public  String MovieDescription= null;
    public  String MovieTrailer = null;
    public  String moviePoster=null;

    public String getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(String movieRating) {
        this.movieRating = movieRating;
    }

    public  String movieRating = null;
    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }



    public MovieObject(String movieName, String movieDescription, String movieTrailer,String moviePoster, String movieRating){
        this.MovieName =movieName;
        this.MovieDescription=movieDescription;
        this.MovieTrailer= movieTrailer;
        this.moviePoster = moviePoster;
        this.movieRating = movieRating;


    }


    public String getMovieName() {
        return MovieName;
    }

    public void setMovieName(String movieName) {
        MovieName = movieName;
    }

    public String getMovieDescription() {
        return MovieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        MovieDescription = movieDescription;
    }

    public String getMovieTrailer() {
        return MovieTrailer;
    }

    public void setMovieTrailer(String movieTrailer) {
        MovieTrailer = movieTrailer;
    }




    protected MovieObject(Parcel in) {
        MovieName = in.readString();
        MovieDescription = in.readString();
        MovieTrailer = in.readString();
        moviePoster = in.readString();
        movieRating = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(MovieName);
        dest.writeString(MovieDescription);
        dest.writeString(MovieTrailer);
        dest.writeString(moviePoster);
        dest.writeString(movieRating);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MovieObject> CREATOR = new Parcelable.Creator<MovieObject>() {
        @Override
        public MovieObject createFromParcel(Parcel in) {
            return new MovieObject(in);
        }

        @Override
        public MovieObject[] newArray(int size) {
            return new MovieObject[size];
        }
    };
}