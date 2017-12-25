package com.android.lior.lastchoice.Data;

/**
 * Created by Lior on 12/25/2017.
 */

public class MovieObject {
    public  String MovieName = null;
    public  String MovieDescription= null;
    public  String MovieTrailer = null;

    public MovieObject(String movieName, String movieDescription, String movieTraler){
        this.MovieName =MovieName;
        this.MovieDescription=MovieDescription;
        this.MovieTrailer= movieTraler;

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



}
