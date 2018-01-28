package com.ely.lastchoice.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.ely.lastchoice.Data.MovieObject;
import com.ely.lastchoice.R;

public class ExpandTextActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_expand_text);
        Intent intent = getIntent();
        MovieObject movieObject = intent.getParcelableExtra("MOVIEOBJECTE");
        TextView textView = findViewById(R.id.fullDescription);
        textView.setText(movieObject.getMovieDescription());
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_expand_text;
    }
}
