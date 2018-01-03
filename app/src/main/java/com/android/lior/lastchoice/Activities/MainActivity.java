package com.android.lior.lastchoice.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.lior.lastchoice.Data.MovieObject;
import com.android.lior.lastchoice.R;
import com.android.lior.lastchoice.Utilities.NetworkUtil;
import com.android.lior.lastchoice.Utilities.QueryJsonUtil;
import com.android.lior.lastchoice.Utilities.ToastUtil;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks< ArrayList<MovieObject>> {

    private EditText query;
    private static String queryString = "";
    private static int LOADERID = 3233;
    private Button makeQueryButton;
    private TextView textView;
    public String[] queryList= new String[2];
    private ImageView imageView;
    private final static String ERRORINRESPONSE ="N/A";
    private final static String ERRORMESSAGE ="No matches found! Please try again";
    public Context context;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        query = (EditText) findViewById(R.id.Query);
        textView = (TextView)findViewById(R.id.textView);
        makeQueryButton = (Button) findViewById(R.id.button);
        imageView =(ImageView)findViewById(R.id.imageView);
        getSupportLoaderManager().initLoader(LOADERID, null, this);
        context = this;



    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void performQuery() {
        queryString = query.getText().toString();
        URL searchQueryTaste = NetworkUtil.buildUrlTaste(queryString);

        Bundle bundle = new Bundle();
        bundle.putString("SEARCH_QUERY_TASTE", searchQueryTaste.toString());

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> searchLoader = loaderManager.getLoader(LOADERID);
        if (searchLoader == null) {
            loaderManager.initLoader(LOADERID, bundle, this);
        }else

        {
            loaderManager.restartLoader(LOADERID, bundle, this);
        }
    }

    @Override
    public Loader< ArrayList<MovieObject>> onCreateLoader(int i, final Bundle bundle) {
        return new AsyncTaskLoader< ArrayList<MovieObject>>(this) {
            private volatile Thread thread;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if(bundle == null){
                    return;
                }
                //TODO: Add progressBar

                forceLoad();
            }

            @Override
            public void cancelLoadInBackground() {
                abandon();

            }

            @Override
            public void onCanceled(ArrayList<MovieObject> data) {

//
//                LayoutInflater layoutInflater= getLayoutInflater();
//                View layout = layoutInflater.inflate(R.layout.toast_layout,
//                        (ViewGroup) findViewById(R.id.toast_layout_root));
//                TextView toastText = (TextView)layout.findViewById(R.id.text);
//                toastText.setText(ERRORMESSAGE);
//                Context context =getApplicationContext();
//                int duration = Toast.LENGTH_SHORT;
//                Toast toast =new Toast(context);
//                toast.setDuration(duration);
//                toast.setGravity(Gravity.CENTER,0,0);
//                toast.setView(layout);
//
//
//
//
//
//                toast.show();
                ToastUtil.createToast(ERRORMESSAGE,context);
                abandon();
            }

            @Override
            public ArrayList<MovieObject> loadInBackground() {
                String searchQueryUrlTaste = bundle.getString("SEARCH_QUERY_TASTE");

                if (searchQueryUrlTaste == null || TextUtils.isEmpty(searchQueryUrlTaste) ) {
                    return null;
                }
                ArrayList<MovieObject> movieObjects = new ArrayList<>();;
                try {
                    Boolean isHttp = false;
                    URL queryURLTaste = new URL(searchQueryUrlTaste);
                    String queryResultsTaste = NetworkUtil.getResponsefromUrl(queryURLTaste, isHttp);


                    QueryJsonUtil queryJsonUtil = new QueryJsonUtil();

                    movieObjects = queryJsonUtil.getJsonStrings(queryResultsTaste);
                    if(movieObjects!=null){




                        for (int i = 0; i < movieObjects.size(); i++) {
                            URL searchQueryExtra = NetworkUtil.buildUrlExtraData(movieObjects.get(i).getMovieName());
                            String queryResultsExtra = NetworkUtil.getResponsefromUrl(searchQueryExtra, isHttp = true);
                            MovieObject movieObject;
                            movieObject = queryJsonUtil.getJsonStringsExtra(queryResultsExtra);
                            movieObjects.get(i).setMoviePoster(movieObject.getMoviePoster());
                            movieObjects.get(i).setMovieRating(movieObject.getMovieRating());

                        }
                    }else {
                        cancelLoadInBackground();

                    }



                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return movieObjects;

            }
        };
    }



    @Override
    public void onLoadFinished(Loader< ArrayList<MovieObject>> loader,  ArrayList<MovieObject> movieObjects) {
           //TODO: Finsih loading
           //TODO: Display data




        query.setText("");
        Intent intent = new Intent(this,MovieSuggestions.class);
        intent.putParcelableArrayListExtra("MovieObjects",  movieObjects);
        startActivity(intent);


    }

    @Override
    public void onLoaderReset(Loader< ArrayList<MovieObject>> loader) {

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void buttonClick(View view) {
        performQuery();
    }
}
