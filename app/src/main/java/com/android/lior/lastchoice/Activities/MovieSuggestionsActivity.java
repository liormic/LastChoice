package com.android.lior.lastchoice.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.android.lior.lastchoice.Adapters.MovieAdapter;
import com.android.lior.lastchoice.Data.MovieObject;
import com.android.lior.lastchoice.R;

import java.util.ArrayList;

public class MovieSuggestionsActivity extends BaseActivity implements MovieAdapter.ListItemClickListener{

    ArrayList<MovieObject> movieObjects;
    RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_suggestions);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView= (RecyclerView)findViewById(R.id.recyclerView);
        Intent intent = getIntent();
        movieObjects=  intent.getParcelableArrayListExtra("MovieObjects");
        GridLayoutManager gridLayoutManager= new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        movieAdapter = new MovieAdapter(movieObjects,this,false);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

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
}
