package com.android.lior.lastchoice.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.lior.lastchoice.Adapters.MovieAdapter;
import com.android.lior.lastchoice.Data.MovieObject;
import com.android.lior.lastchoice.R;

import java.util.ArrayList;

public class MovieSuggestions extends AppCompatActivity implements MovieAdapter.ListItemClickListener{


    RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_suggestions);
        recyclerView= (RecyclerView)findViewById(R.id.recyclerView);
        Intent intent = getIntent();
        ArrayList<MovieObject> movieObjects=  intent.getParcelableArrayListExtra("MovieObjects");
        GridLayoutManager linearLayoutManager= new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        movieAdapter = new MovieAdapter(movieObjects,this);
        recyclerView.setAdapter(movieAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {


        Intent intent = new Intent(this, MovieExpandActivity.class);
        startActivity(intent);

    }
}
