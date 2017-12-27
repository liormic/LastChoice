package com.android.lior.lastchoice.Data;

/**
 * Created by Lior on 12/25/2017.
 */

public class MovieObject {
    public  String movieName = null;
    public  String movieDescription = null;
    public  String movieTrailer = null;

    public String getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(String movieRating) {
        this.movieRating = movieRating;
    }

    public  String movieRating =null;
    public  String moviePoster=null;
    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }



    public MovieObject(String movieName, String movieDescription, String movieTrailer,
                       String moviePoster,String movieRating){
        this.movieName =movieName;
        this.movieDescription =movieDescription;
        this.movieTrailer = movieTrailer;
        this.moviePoster= moviePoster;
        this.movieRating= movieRating;
    }


    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public String getMovieTrailer() {
        return movieTrailer;
    }

    public void setMovieTrailer(String movieTrailer) {
        this.movieTrailer = movieTrailer;
    }



}
