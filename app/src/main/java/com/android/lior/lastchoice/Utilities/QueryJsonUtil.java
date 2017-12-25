package com.android.lior.lastchoice.Utilities;

import com.android.lior.lastchoice.Data.MovieObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by lior on 12/24/17.
 */

public class QueryJsonUtil {

    private static final String MOVIE_NAME="Name";
    private static final String MOVIE_DESCRIPTION="wTeaser";
    private static final String MOVIE_TRAILER = "wUrl";
    private static final String Results ="Results";
    private JSONObject object;
    private ArrayList<MovieObject> movieObjects = new ArrayList<>();


    public ArrayList<MovieObject> getJsonStrings(String resultsFromJson) throws JSONException{
        JSONObject jsonObject= new JSONObject(resultsFromJson);
        JSONArray  jsonArray = jsonObject.getJSONArray(Results);
        for(int i = 0; i>jsonArray.length();i++){
            try {
                object = jsonArray.getJSONObject(i);
            }catch (JSONException e){

            }
            String movieName = object.getString(MOVIE_NAME);
            String movieDescription = object.getString(MOVIE_DESCRIPTION);
            String movieTrailer = object.getString(MOVIE_TRAILER);
            MovieObject movieObject= new MovieObject(movieName,movieDescription,movieTrailer);
            movieObjects.add(movieObject);
        }

        return movieObjects;
       }

    }





