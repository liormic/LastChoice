package com.android.lior.lastchoice.Activities;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.lior.lastchoice.R;
import com.android.lior.lastchoice.Utilities.NetworkUtil;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private EditText query;
    private static String queryString = "";
    private static int LOADERID = 3233;
    private Button makeQueryButton;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        query = (EditText) findViewById(R.id.Query);
        makeQueryButton = (Button) findViewById(R.id.button);
        getSupportLoaderManager().initLoader(LOADERID, null, this);


    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void performQuery() {
        queryString = query.getText().toString();
        URL searchQuery = NetworkUtil.buildUrl(queryString);
        Bundle bundle = new Bundle();
        bundle.putString("SEARCH_QUERY_EXTRA", searchQuery.toString());
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
    public Loader<String> onCreateLoader(int i, final Bundle bundle) {
        return new AsyncTaskLoader<String>(this) {
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
            public String loadInBackground() {
                String searchQueryUrl = bundle.getString("SEARCH_QUERY_EXTRA");
                if(searchQueryUrl ==null || TextUtils.isEmpty(searchQueryUrl)){
                    return null;
                }
                try{
                    URL queryURL = new URL(searchQueryUrl);
                    String queryResults = NetworkUtil.getResponsefromUrl(queryURL);
                    return queryResults;

                }catch (IOException e){
                    e.printStackTrace();
                    return null;
                }


            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String s) {
           //TODO: Finsih loading
           //TODO: Display data

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void buttonClick(View view) {
        performQuery();
    }
}
