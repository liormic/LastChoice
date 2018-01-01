package com.android.lior.lastchoice.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.lior.lastchoice.Data.MovieObject;
import com.android.lior.lastchoice.R;
import com.squareup.picasso.Picasso;

public class MovieExpandActivity extends AppCompatActivity {

    WebView webView ;
    ImageView imageView;
    TextView  movieName;
    TextView  movieDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_expand);
        Intent intent =getIntent();
        MovieObject movieObject = intent.getParcelableExtra("MOVIEOBJECT");

        webView= findViewById(R.id.webViewExpand);
        imageView=findViewById(R.id.imagePosterExpand);
        movieName = (TextView)findViewById(R.id.movieTitleExpand);
        movieDescription= findViewById(R.id.Description);


        webView.getSettings().setJavaScriptEnabled(true);
//
//        webView.setWebViewClient(new WebViewClient(){
//            public void onReceivedError(WebView webView,int errorCode,String description,String faillingUrl){
//                Toast.makeText(getApplicationContext(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
//            }
//        });

        webView.loadUrl(movieObject.getMovieTrailer());
        Picasso.with(this).load(movieObject.getMoviePoster()).fit().into(imageView);
        movieName.setText(movieObject.getMovieName());
        movieName.setTextSize(40);
        movieDescription.setText(movieObject.getMovieDescription());


    }

}
