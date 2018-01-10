package com.android.lior.lastchoice.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.lior.lastchoice.Adapters.MovieAdapter;
import com.android.lior.lastchoice.Data.ContractDB;
import com.android.lior.lastchoice.Data.DBoperations;
import com.android.lior.lastchoice.Data.MovieObject;
import com.android.lior.lastchoice.R;

import java.util.ArrayList;

public class FavActivity extends BaseActivity implements MovieAdapter.ListItemClickListener {private static final String TAG = FavActivity.class.getSimpleName();
  private  MovieAdapter movieAdapter;
  private  RecyclerView recyclerView;
  public   ArrayList<MovieObject> movieObjects;
  private  DBoperations dBoperations;
  private  MenuItem fav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initRecyclerView();


    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_fav;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        fav = menu.findItem(R.id.favIcon);

        fav.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.searchIcon:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public ArrayList<MovieObject>  getMovieObjectFromCursor(Cursor cursor){
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
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(this, MovieExpandActivity.class);
        intent.putExtra("MOVIEOBJECT",movieObjects.get(clickedItemIndex));
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initRecyclerView();
    }

    public void initRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewFav);

        dBoperations = new DBoperations(this);
        MovieAdapter movieAdapter = null;
        try {
            movieObjects = getMovieObjectFromCursor(dBoperations.getAllitems());
        }catch (SQLException e ){
            Log.e(TAG,getString(R.string.errorMessageGetAllItems)+e.getMessage());
        }
        if (movieObjects != null) {
            movieAdapter = new MovieAdapter(movieObjects, this,true);
            recyclerView.setAdapter(movieAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recyclerView.setHasFixedSize(true);
        } else {
            setContentView(R.layout.fav_layout__no_items);

        }

    }
}
