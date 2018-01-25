package com.android.lior.lastchoice.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.lior.lastchoice.Adapters.MovieAdapter;
import com.android.lior.lastchoice.Data.MovieObject;
import com.android.lior.lastchoice.R;
import com.android.lior.lastchoice.ToolBarInterface;

import java.util.ArrayList;

public class MovieSuggestionsActivity extends BaseActivity implements MovieAdapter.ListItemClickListener,ToolBarInterface{

    private ArrayList<MovieObject> movieObjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_movie_suggestions);
        invalidateOptionsMenu();
        if (toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Intent intent = getIntent();
        movieObjects=  intent.getParcelableArrayListExtra("MovieObjects");
        GridLayoutManager gridLayoutManager= new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        MovieAdapter movieAdapter = new MovieAdapter(movieObjects, this, false);
        recyclerView.setAdapter(movieAdapter);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_movie_suggestions;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        CardView cardView = findViewById(R.id.cardView);
        cardView.setElevation(8);
        Intent intent = new Intent(this, MovieExpandActivity.class);
        intent.putExtra("MOVIEOBJECT",movieObjects.get(clickedItemIndex));

        startActivity(intent);

    }

}
