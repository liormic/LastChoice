package com.android.lior.lastchoice.Utilities;

import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by lior on 12/24/17.
 */

public class NetworkUtil {
    private static final String TAG = NetworkUtil.class.getSimpleName();
    private static final String BASE_URL = "https://tastedive.com/api/similar";
    private static final String format = "JSON";
    private static final String QUERY_PARAM = "q";
    private static final String TYPE_PARAM = "type";
    private static final String type = "movie";
    private static final String info = "1";
    private static final String INFO_PARAM = "info";
    private static final String LIMIT_PARAM="limit";
    private static final String limit = "5";
    private static final String APIKEY_PARAM = "k";
    private static final String api_key = "294364-LastTast-AEUE33KD";
    private static final String VERBOSE_PARAM = "verbose";
    private static final String verbose = "1";



    //https://tastedive.com/api/similar?q=snow+crash,book:way+of+kings&type=books&limit=5&k=294364-LastTast-AEUE33KD
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static URL buildUrl(String query) {
        Uri apiRequestUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, query)
                .appendQueryParameter(TYPE_PARAM, type)
                .appendQueryParameter(LIMIT_PARAM,limit)
                .appendQueryParameter(INFO_PARAM, info)
                .appendQueryParameter(VERBOSE_PARAM, verbose)
                .appendQueryParameter(APIKEY_PARAM, api_key)
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

    public static String getResponsefromUrl(URL url) throws IOException {
        HttpsURLConnection urlConnection =(HttpsURLConnection)url.openConnection();
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