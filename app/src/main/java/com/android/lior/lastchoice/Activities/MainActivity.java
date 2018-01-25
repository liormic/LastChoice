package com.android.lior.lastchoice.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.lior.lastchoice.Data.MovieObject;
import com.android.lior.lastchoice.R;
import com.android.lior.lastchoice.ToolBarInterface;
import com.android.lior.lastchoice.Utilities.DialogUtil;
import com.android.lior.lastchoice.Utilities.NetworkUtil;
import com.android.lior.lastchoice.Utilities.QueryJsonUtil;
import com.android.lior.lastchoice.Utilities.ToastUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends BaseActivity implements LoaderManager.LoaderCallbacks< ArrayList<MovieObject>> ,ToolBarInterface {
    private static final String TAG ="MainActivity";
    private boolean isBusy = false;
    private ProgressBar pB;
    private EditText query;
    private static final int LOADERID = 3233;
    private final static String ERRORMESSAGE = "No matches found! Please try again";
    private Context context;
    private Boolean iSnetworkAvilabale;


    @Override
    public void onBackPressed() {
        finish();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_main);

        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        invalidateOptionsMenu();
        context = this;
        query = findViewById(R.id.Query);
        checkIfConnectedToInternet();
        pB = findViewById(R.id.progressBar);
        if(toolbar!=null) {

            setSupportActionBar(toolbar);
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }


        pB.setVisibility(View.GONE);
        getSupportLoaderManager().initLoader(LOADERID, null, this);

//        View myView = new View(context);
//        myView.requestFocus();
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        query.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH && !isBusy ){
                    performQuery();
                    return true;
                }
                return false;
            }
        });


    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void performQuery() {

        String queryString = query.getText().toString();
        URL searchQueryTaste = NetworkUtil.buildUrlTaste(queryString);

        Bundle bundle = new Bundle();
        bundle.putString("SEARCH_QUERY_TASTE", searchQueryTaste.toString());

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> searchLoader = loaderManager.getLoader(LOADERID);
        if (searchLoader == null) {
            loaderManager.initLoader(LOADERID, bundle, this);
        } else

        {
            loaderManager.restartLoader(LOADERID, bundle, this);
        }
    }


    @Override
    public Loader<ArrayList<MovieObject>> onCreateLoader(int i, final Bundle bundle)   {
        return new AsyncTaskLoader<ArrayList<MovieObject>>(this) {


            @Override
            protected void onStartLoading() {

                super.onStartLoading();
                if(!checkIfConnectedToInternet()){
                    cancelLoadInBackground();
                }
                if (bundle == null) {
                    return;
                }

                forceLoad();
                pB.setVisibility(View.VISIBLE);
                isBusy = true;
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
//
//                Context context =getApplicationContext();
//                ToastUti
                if(iSnetworkAvilabale) {
                    //   ToastUtil.createToast(getString(R.string.errorConnecting),context);
                    // }else {
                    ToastUtil.createToast(ERRORMESSAGE, context);

                }
                pB.setVisibility(View.INVISIBLE);
                isBusy = false;
                abandon();
            }

            @Override
            public ArrayList<MovieObject> loadInBackground() {

                QueryJsonUtil queryJsonUtil = new QueryJsonUtil();
                String searchQueryUrlTaste = bundle.getString("SEARCH_QUERY_TASTE");

                if (searchQueryUrlTaste == null || TextUtils.isEmpty(searchQueryUrlTaste)) {
                    return null;
                }
                ArrayList<MovieObject> movieObjects = new ArrayList<>();

                try {
                    Boolean isHttp = false;
                    URL queryURLTaste = new URL(searchQueryUrlTaste);
                    try {
                        String queryResultsTaste = NetworkUtil.getResponsefromUrl(queryURLTaste,isHttp,context);


                        movieObjects = queryJsonUtil.getJsonStrings(queryResultsTaste);
                    }catch (Exception e){
                        Log.e(TAG,"Error" +e.getMessage());
                        //   ToastUtil.createToast("Error connecting, please check your internet connection",getApplicationContext());
                        cancelLoadInBackground();
                    }



                    if (movieObjects != null) {
                        isHttp=true;

                        for (int i = 0; i < movieObjects.size(); i++) {

                            URL searchQueryExtra = NetworkUtil.buildUrlExtraData(movieObjects.get(i).getMovieName());
                            String queryResultsExtra = NetworkUtil.getResponsefromUrl(searchQueryExtra,isHttp,context);
                            MovieObject movieObject;
                            movieObject = queryJsonUtil.getJsonStringsExtra(queryResultsExtra);
                            movieObjects.get(i).setMoviePoster(movieObject.getMoviePoster());
                            movieObjects.get(i).setMovieRating(movieObject.getMovieRating());

                        }
                    } else {
                        cancelLoadInBackground();

                    }


                } catch (IOException e) {

                    e.printStackTrace();
                    return null;
                }
                return movieObjects;

            }
        };
    }


    @Override
    public void onLoadFinished(Loader<ArrayList<MovieObject>> loader, ArrayList<MovieObject> movieObjects) {
        pB.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        query.setText("");
        Intent intent = new Intent(this, MovieSuggestionsActivity.class);
        intent.putParcelableArrayListExtra("MovieObjects", movieObjects);
        startActivity(intent);
        finish();


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkIfConnectedToInternet();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieObject>> loader) {

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void buttonClick() {
        performQuery();
    }


    public void clicktext(View view) {
        Intent intentFav = new Intent(this, FavActivity.class);
        startActivity(intentFav);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem search = menu.findItem(R.id.searchIcon);
        search.setVisible(false);

        return true;
    }


    private Boolean checkIfConnectedToInternet() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = null;
        if (cm != null) {
            networkInfo = cm.getActiveNetworkInfo();

            Boolean isConnected=  networkInfo != null &&
                    networkInfo.isConnectedOrConnecting();
            if(!isConnected){
                DialogUtil.createDialog(context,getString(R.string.connectivtyMessage) , getString(R.string.confirmMessage));
                iSnetworkAvilabale=false;
                return false;
            }else{
                iSnetworkAvilabale=true;
            }


        }
        return true;
    }
}
