package com.android.lior.lastchoice.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.lior.lastchoice.Data.MovieObject;
import com.android.lior.lastchoice.R;

public class ExpandTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_text);
        Intent intent = getIntent();
        MovieObject movieObject = intent.getParcelableExtra("MOVIEOBJECTE");
        TextView textView = (TextView)findViewById(R.id.fullDescription);
        textView.setText(movieObject.getMovieDescription());
    }
}
