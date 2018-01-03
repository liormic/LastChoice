package com.android.lior.lastchoice.Activities;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.lior.lastchoice.Data.DBoperations;
import com.android.lior.lastchoice.Data.MovieObject;
import com.android.lior.lastchoice.R;
import com.android.lior.lastchoice.Utilities.ToastUtil;
import com.squareup.picasso.Picasso;

public class MovieExpandActivity extends AppCompatActivity {
   private static final String TAG = MovieExpandActivity.class.getSimpleName();
   private WebView webView;
   private ImageView imageView;
   private TextView movieName;
   private TextView movieDescription;
   private RatingBar ratingBar;
   private TextView fullDescription;
   private ConstraintLayout linearLayout;
   private LinearLayout mainLayout;
   public  MovieObject movieObject;
   private final static  String errorMessageDb ="Oh No! Error on adding the movie";
   private final static  String succsesMessage ="Movie Added!";
   private final static String movieAlreadyExistsMessage ="Movie already exists in your favorites!";
   private TextView addToFav;
   public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_expand);
        Intent intent = getIntent();
        addToFav= findViewById(R.id.addtofavtext);
         movieObject = intent.getParcelableExtra("MOVIEOBJECT");
     //   mainLayout = findViewById(R.id.mainLayout);
        webView = findViewById(R.id.webViewExpand);
        imageView = findViewById(R.id.imagePosterExpand);
        movieName = (TextView) findViewById(R.id.movieTitleExpand);
        movieDescription = findViewById(R.id.Description);
        ratingBar = findViewById(R.id.ratingBar);
        fullDescription = findViewById(R.id.fullDescription);
        webView.getSettings().setJavaScriptEnabled(true);
        linearLayout = findViewById(R.id.constraintLayout);
        context = this;
//


//        webView.setWebViewClient(new WebViewClient(){
//            public void onReceivedError(WebView webView,int errorCode,String description,String faillingUrl){
//                Toast.makeText(getApplicationContext(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
//            }
//        });
        String movieDescr = movieObject.getMovieDescription();
        webView.loadUrl(movieObject.getMovieTrailer());
        Picasso.with(this).load(movieObject.getMoviePoster()).fit().into(imageView);
        movieName.setText(movieObject.getMovieName());
        movieName.setTextSize(40);
        movieDescription.setText(movieDescr);
        ratingBar.setMax(5);
        ratingBar.setIsIndicator(true);
        Float rating = Float.valueOf(movieObject.getMovieRating());
        rating = rating / 2;
        ratingBar.setRating(rating);
        fullDescription.setText(movieDescr);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        linearLayout.setVisibility(View.GONE);
        movieDescription.setOnClickListener(onClickListener);
        addToFav.setOnClickListener(onClickListenerFav);


    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mainLayout.setVisibility(View.GONE);
          linearLayout.setVisibility(View.VISIBLE);
        }
    };

    private View.OnClickListener onClickListenerFav = new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    try {
                        DBoperations dBoperations = new DBoperations(getApplicationContext());
                        Boolean isSuccses = dBoperations.addItem(movieObject, getApplicationContext());

                        if (isSuccses) {
                            ToastUtil.createToast(succsesMessage, context);

                        } else {

                            ToastUtil.createToast(movieAlreadyExistsMessage, context);
                        }

                    } catch (SQLException e) {
                        Log.e(TAG, "Failed to insert Value" + e.getMessage());
                        ToastUtil.createToast(errorMessageDb, context);

                    }
                    return null;
                }
            };


        }


    };

    }



