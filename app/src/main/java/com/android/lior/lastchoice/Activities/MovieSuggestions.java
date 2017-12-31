package com.android.lior.lastchoice.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.lior.lastchoice.Adapters.MovieAdapter;
import com.android.lior.lastchoice.Data.MovieObject;
import com.android.lior.lastchoice.R;

import java.util.ArrayList;

public class MovieSuggestions extends AppCompatActivity {


    RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_suggestions);
        recyclerView= (RecyclerView)findViewById(R.id.recyclerView);
        Intent intent = getIntent();
        ArrayList<MovieObject> movieObjects=  intent.getParcelableArrayExtra("MovieObjects");
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        movieAdapter = new MovieAdapter(movieObjects);

    }
}
