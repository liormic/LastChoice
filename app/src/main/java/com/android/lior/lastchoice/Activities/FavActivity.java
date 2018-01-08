package com.android.lior.lastchoice.Activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.lior.lastchoice.Adapters.MovieAdapter;
import com.android.lior.lastchoice.Data.ContractDB;
import com.android.lior.lastchoice.Data.DBoperations;
import com.android.lior.lastchoice.Data.MovieObject;
import com.android.lior.lastchoice.R;

import java.util.ArrayList;

public class FavActivity extends AppCompatActivity implements MovieAdapter.ListItemClickListener {

    private  RecyclerView recyclerView;
    public ArrayList<MovieObject> movieObjectsArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        recyclerView=findViewById(R.id.recyclerViewFav);

        DBoperations dBoperations=new DBoperations(this);
        getMovieObjectFromCursor(dBoperations.getAllitems());
        MovieAdapter movieAdapter = new MovieAdapter(movieObjectsArray,this);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(true);

    }


    public void getMovieObjectFromCursor(Cursor cursor){
        if(cursor.getCount()<1) {
            Toast toast = Toast.makeText(this, R.string.toastMessageFav,Toast.LENGTH_SHORT);
            toast.show();
            return ;
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

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

    }
}
