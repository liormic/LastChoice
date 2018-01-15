package com.android.lior.lastchoice.Activities;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.lior.lastchoice.Data.MovieObject;
import com.android.lior.lastchoice.R;
import com.android.lior.lastchoice.ToolBarInterface;
import com.android.lior.lastchoice.Utilities.DialogUtil;
import com.android.lior.lastchoice.Utilities.NetworkUtil;
import com.android.lior.lastchoice.Utilities.QueryJsonUtil;
import com.android.lior.lastchoice.Utilities.ToastUtil;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends BaseActivity implements LoaderManager.LoaderCallbacks< ArrayList<MovieObject>> ,ToolBarInterface {
    public boolean isBusy = false;
    private ProgressBar pB;
    private EditText query;
    private static String queryString = "";
    private static int LOADERID = 3233;
    private Button makeQueryButton;
    private TextView textView;
    public String[] queryList = new String[2];
    private ImageView imageView;
    private final static String ERRORINRESPONSE = "N/A";
    private final static String ERRORMESSAGE = "No matches found! Please try again";
    public Context context;
    private MenuItem search;
    Boolean iSnetworkAvilabale;



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
        textView = findViewById(R.id.textHomeScreen);
        // makeQueryButton = (Button) findViewById(R.id.button);
        imageView = findViewById(R.id.imageView);
        pB = findViewById(R.id.progressBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        pB.setVisibility(View.GONE);
        getSupportLoaderManager().initLoader(LOADERID, null, this);

        View myView = new View(context);
        myView.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        query.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH && isBusy != true) {
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
    public void performQuery() {

        queryString = query.getText().toString();
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
    public Loader<ArrayList<MovieObject>> onCreateLoader(int i, final Bundle bundle) {
        return new AsyncTaskLoader<ArrayList<MovieObject>>(this) {
            private volatile Thread thread;

            @Override
            protected void onStartLoading() {

                super.onStartLoading();
                if(checkIfConnectedToInternet()!=true){
                    cancelLoadInBackground();
                }
                if (bundle == null) {
                    return;
                }
                //TODO: Add progressBar

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
                ;
                try {
                    Boolean isHttp = false;
                    URL queryURLTaste = new URL(searchQueryUrlTaste);
                    try {
                        String queryResultsTaste = NetworkUtil.getResponsefromUrl(queryURLTaste, isHttp);


                        movieObjects = queryJsonUtil.getJsonStrings(queryResultsTaste);
                    }catch (Exception e){
                     //   ToastUtil.createToast("Error connecting, please check your internet connection",getApplicationContext());
                        cancelLoadInBackground();
                    }



                    if (movieObjects != null) {


                        for (int i = 0; i < movieObjects.size(); i++) {
                            URL searchQueryExtra = NetworkUtil.buildUrlExtraData(movieObjects.get(i).getMovieName());
                            String queryResultsExtra = NetworkUtil.getResponsefromUrl(searchQueryExtra, isHttp = true);
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
                } catch (JSONException e) {
                    e.printStackTrace();
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
    public void buttonClick(View view) {
        performQuery();
    }


    public void clicktext(View view) {
        Intent intentFav = new Intent(this, FavActivity.class);
        startActivity(intentFav);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        search = menu.findItem(R.id.searchIcon);
        search.setVisible(false);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.searchIcon:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.favIcon:
                Intent intentFav = new Intent(this, FavActivity.class);
                startActivity(intentFav);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public Boolean checkIfConnectedToInternet() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = null;
        if (cm != null) {
            networkInfo = cm.getActiveNetworkInfo();

            Boolean isConnected=  networkInfo != null &&
                    networkInfo.isConnectedOrConnecting();
            if(!isConnected){
                DialogUtil.createDialog(context,getString(R.string.connectivtyMessage) ,getString(R.string.oops),getString(R.string.confirmMessage));
                iSnetworkAvilabale=false;
                return false;
            }else{
                iSnetworkAvilabale=true;
            }


        }
        return true;
    }
}
