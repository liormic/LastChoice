package com.ely.lastchoice.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.ely.lastchoice.Adapters.MovieAdapter;
import com.ely.lastchoice.Data.ContractDB;
import com.ely.lastchoice.Data.DBoperations;
import com.ely.lastchoice.Data.MovieObject;
import com.ely.lastchoice.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class FavActivity extends BaseActivity implements MovieAdapter.ListItemClickListener,LoaderManager.LoaderCallbacks<ArrayList<MovieObject>> {

    private static final String TAG = FavActivity.class.getSimpleName();
    private  RecyclerView recyclerView;
    private ArrayList<MovieObject> movieObjects;
    private  DBoperations dBoperations;
    private  RelativeLayout relativeLayout;
    private Context context;
    private static Timer timer;
    private static final int LOADERID =34;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //     setContentView(R.layout.activity_fav);

        getSupportLoaderManager().initLoader(LOADERID, null, this);
        initRecyclerView();


        context=this;
    }


    @Override
    protected int getLayoutResource() {

        return R.layout.activity_fav;
    }



    private ArrayList<MovieObject>  getMovieObjectFromCursor(Cursor cursor){
        ArrayList<MovieObject> movieObjectsArray = new ArrayList<>();
        if(cursor.getCount()<1) {

            setContentView(R.layout.fav_layout__no_items);
            return null;
        }

        int movieNameCol = cursor.getColumnIndex(ContractDB.MovieData.COLUMN_MOVIENAME);
        int movieTrailerUrlCol=cursor.getColumnIndex(ContractDB.MovieData.COLUMN_YOUTUBEURL);
        int moviePosterCol = cursor.getColumnIndex(ContractDB.MovieData.COLUMN_MOVIEIMAGE);
        int movieRatingCol = cursor.getColumnIndex(ContractDB.MovieData.COLUMN_MOVIERATING);
        int movieDescCol = cursor.getColumnIndex(ContractDB.MovieData.COLUMN_Description);
        while (cursor.moveToNext()){

            MovieObject movieObject = new MovieObject(cursor.getString(movieNameCol)
                    ,cursor.getString(movieDescCol)
                    ,cursor.getString(movieTrailerUrlCol)
                    ,cursor.getString(moviePosterCol)
                    ,cursor.getString(movieRatingCol));
            movieObjectsArray.add(movieObject);


        }

        cursor.close();
        return movieObjectsArray;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem bin = menu.findItem(R.id.favIcon);
        bin.setIcon(R.drawable.ic_delete_black_24dp);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.searchIcon:
                startActivity();
                return true;
            case R.id.favIcon:

                createDialogFav();

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(this, MovieExpandActivity.class);
        intent.putExtra("MOVIEOBJECT",movieObjects.get(clickedItemIndex));
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.invalidate();

    }



    private void startActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);



    }


    private void initRecyclerView(){
        recyclerView =  findViewById(R.id.recyclerViewFav);
        relativeLayout = findViewById(R.id.relativeLayoutNoItems);
        dBoperations = new DBoperations(this);
        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<ArrayList<MovieObject>> loader = loaderManager.getLoader(LOADERID);

        if(loader==null){
            loaderManager.initLoader(LOADERID,null, this);
        }else{
            loaderManager.restartLoader(LOADERID,null   , this);
        }


    }

    private void createDialogFav() {

        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage(getString(R.string.deleteAll));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DBoperations dBoperations = new DBoperations(context);
                        dBoperations.removeAllItems();
                        //initRecyclerView();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                        alertDialog.dismiss();
                    }
                }
        );
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        alertDialog.dismiss();
                    }

                }


        );
        alertDialog.show();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(timer!=null){
            timer.cancel();
            timer.purge();
        }
    }


    @Override
    public Loader<ArrayList<MovieObject>> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<ArrayList<MovieObject>>(this) {
            @Override
            public ArrayList<MovieObject> loadInBackground() {

                try {
                    movieObjects = getMovieObjectFromCursor(dBoperations.getAllitems());
                }catch (SQLException e ){
                    Log.e(TAG,getString(R.string.errorMessageGetAllItems)+e.getMessage());
                }
                return movieObjects;
            }

            @Override
            protected void onStartLoading() {
                if(movieObjects!=null) {
                    setupRecyclerView(movieObjects);
                }else{
                    forceLoad();
                }

            }
        };
    }



    @Override
    public void onLoadFinished(Loader<ArrayList<MovieObject>> loader, ArrayList<MovieObject> movieObjects) {
        setupRecyclerView(movieObjects);



    }

    private void setupRecyclerView(ArrayList<MovieObject> movieObjects){

        if (movieObjects != null) {
            MovieAdapter movieAdapter = new MovieAdapter(movieObjects, this, true);
            recyclerView.setAdapter(movieAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recyclerView.setHasFixedSize(true);
        } else {

            recyclerView.setVisibility(View.INVISIBLE);
            relativeLayout.setVisibility(View.VISIBLE);
            timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    startActivity();
                    finish();
                }
            };
            int interval = 2400;
            timer.schedule(timerTask, interval);
        }
    }



    @Override
    public void onLoaderReset(Loader loader) {

    }
}
