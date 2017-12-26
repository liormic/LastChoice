package com.android.lior.lastchoice.Utilities;

import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by lior on 12/24/17.
 */

public class NetworkUtil {
    private static final String TAG = NetworkUtil.class.getSimpleName();
    private static final String BASE_URL_TASTE = "https://tastedive.com/api/similar";
    private static final String format = "JSON";
    private static final String QUERY_PARAM_TASTE = "q";
    private static final String TYPE_PARAM_TASTE = "type";
    private static final String typetaste = "movie";
    private static final String infotaste = "1";
    private static final String INFO_PARAM_TASTE= "info";
    private static final String LIMIT_PARAM_TASTE="limit";
    private static final String limittaste = "5";
    private static final String APIKEY_PARAM_TASTE= "k";
    private static final String api_key_taste = "294364-LastTast-AEUE33KD";
    private static final String VERBOSE_PARAM_TASTE = "verbose";
    private static final String verbosetaste = "1";

    private static final String BASE_URL_EXTRA = "http://www.omdbapi.com/";
    private static final String QUERY_PARAM_EXTRA = "t";
    private static final String api_key_extra = "b06448ae";
    private static final String APIKEY_PARAM_EXTRA= "apikey";





    //https://tastedive.com/api/similar?q=snow+crash,book:way+of+kings&type=books&limit=5&k=294364-LastTast-AEUE33KD
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static URL buildUrlTaste(String query) {
        Uri apiRequestUri = Uri.parse(BASE_URL_TASTE).buildUpon()
                .appendQueryParameter(QUERY_PARAM_TASTE, query)
                .appendQueryParameter(TYPE_PARAM_TASTE, typetaste)
                .appendQueryParameter(LIMIT_PARAM_TASTE,limittaste)
                .appendQueryParameter(INFO_PARAM_TASTE, infotaste)
                .appendQueryParameter(VERBOSE_PARAM_TASTE, verbosetaste)
                .appendQueryParameter(APIKEY_PARAM_TASTE, api_key_taste)
                .build();
        try {
            URL apiRequestUrl = new URL(apiRequestUri.toString());
            Log.v(TAG, "URL:" + apiRequestUrl);
            return apiRequestUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static URL buildUrlExtraData(String query){
        Uri apiRequestUriExtra = Uri.parse(BASE_URL_EXTRA).buildUpon()
                .appendQueryParameter(QUERY_PARAM_EXTRA,query)
                .appendQueryParameter(APIKEY_PARAM_EXTRA,api_key_extra)
                .build();
        try{
            URL apiRequestUrlExtra = new URL(apiRequestUriExtra.toString());
            Log.v(TAG,"URLEXTRA"+apiRequestUrlExtra);
            return apiRequestUrlExtra;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String getResponsefromUrl(URL url,Boolean isHttp) throws IOException {

        String results;

        if(isHttp=true ){
             HttpURLConnection HttpUrlConnection =(HttpURLConnection)url.openConnection();
             results = createHttpconncetion(HttpUrlConnection,isHttp);
             return  results;
        }else{
            HttpsURLConnection HttpSurlConnection =(HttpsURLConnection)url.openConnection();
            results = createHttpconncetion(HttpSurlConnection,isHttp);
            return  results;
        }

    }

    public static String createHttpconncetion(HttpURLConnection urlConnection, Boolean isHttp) throws IOException {


        try{
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput=scanner.hasNext();
            String response = null;
            if(hasInput){
                response= scanner.next();

            }
            scanner.close();
            return response;
        }finally {
            urlConnection.disconnect();
        }

    }





}