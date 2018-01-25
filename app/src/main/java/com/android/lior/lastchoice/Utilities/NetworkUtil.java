package com.android.lior.lastchoice.Utilities;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.android.lior.lastchoice.BuildConfig;
import com.android.lior.lastchoice.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by lior on 12/24/17.
 */

@SuppressWarnings("DefaultFileTemplate")
public class NetworkUtil  {
    private static final String TAG = NetworkUtil.class.getSimpleName();
    private static final String BASE_URL_TASTE = "https://tastedive.com/api/similar";
    private static final String QUERY_PARAM_TASTE = "q";
    private static final String TYPE_PARAM_TASTE = "type";
    private static final String typetaste = "movies";
    private static final String infotaste = "1";
    private static final String INFO_PARAM_TASTE= "info";
    private static final String LIMIT_PARAM_TASTE="limit";
    private static final String limittaste = "12";
    private static final String APIKEY_PARAM_TASTE= "k";

    private static final String VERBOSE_PARAM_TASTE = "verbose";
    private static final String verbosetaste = "1";

    private static final String BASE_URL_EXTRA = "http://www.omdbapi.com/";
    private static final String QUERY_PARAM_EXTRA = "t";

    private static final String APIKEY_PARAM_EXTRA= "apikey";




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static URL buildUrlTaste(String query) {
        Uri apiRequestUri = Uri.parse(BASE_URL_TASTE).buildUpon()
                .appendQueryParameter(QUERY_PARAM_TASTE, query)
                .appendQueryParameter(TYPE_PARAM_TASTE, typetaste)
                .appendQueryParameter(LIMIT_PARAM_TASTE,limittaste)
                .appendQueryParameter(INFO_PARAM_TASTE, infotaste)
                .appendQueryParameter(VERBOSE_PARAM_TASTE, verbosetaste)
                .appendQueryParameter(APIKEY_PARAM_TASTE, BuildConfig.api_key_taste)
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
                .appendQueryParameter(APIKEY_PARAM_EXTRA,BuildConfig.api_key_extra)
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

    public static String getResponsefromUrl(URL url,Boolean isHttp,Context context) throws IOException {

        String results;


        if(isHttp){
            HttpURLConnection httpUrlConnection =(HttpURLConnection)url.openConnection();
            results = createHttpConnection(httpUrlConnection,context);
            return  results;
        }else{
            HttpsURLConnection httpsUrlConnection =(HttpsURLConnection)url.openConnection();
            httpsUrlConnection.setConnectTimeout(5000);
            httpsUrlConnection.setReadTimeout(5000);

            results = createHttpConnection(httpsUrlConnection,context);
            return  results;
        }

    }

    private static String createHttpConnection(HttpURLConnection urlConnection,Context context) throws IOException {


        BufferedReader in = null;

        try{



            try {

                in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            }catch(SocketTimeoutException e) {
                ToastUtil.createToast(context.getString(R.string.timeoutstring), context);
                Log.e(TAG, "error:" + e.getMessage());
            }


            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput=scanner.hasNext();
            String response = null;
            if(hasInput){
                response= scanner.next();

            }

            if(response.contains("Error")||response.contains("Results[]")){
                response ="N/A";
            }
            scanner.close();
            return response;
        }finally {
            urlConnection.disconnect();
        }

    }



    }







