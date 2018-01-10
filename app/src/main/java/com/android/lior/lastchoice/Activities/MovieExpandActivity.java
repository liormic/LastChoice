package com.android.lior.lastchoice.Activities;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.lior.lastchoice.Data.DBoperations;
import com.android.lior.lastchoice.Data.MovieObject;
import com.android.lior.lastchoice.R;
import com.android.lior.lastchoice.Utilities.ToastUtil;
import com.android.lior.lastchoice.databinding.ActivityMovieExpandBinding;
import com.squareup.picasso.Picasso;

public class MovieExpandActivity extends AppCompatActivity {
   private static final String TAG = MovieExpandActivity.class.getSimpleName();
   private ImageView imageView;
   private TextView movieName;

   public  MovieObject movieObject;
   private ImageView plusFav;
   private final static  String errorMessageDb ="Oh No! Error on adding the movie";

   private TextView addToFav;
   private ImageView checkFav;
   public Context context;
   public  String movieNameString;
   private Intent expandTextIntent;
    ActivityMovieExpandBinding movieExpandBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_expand);
        Intent intent = getIntent();

        movieExpandBinding= DataBindingUtil.setContentView(this,R.layout.activity_movie_expand);
        addToFav= findViewById(R.id.addtofavtext);
        movieObject = intent.getParcelableExtra("MOVIEOBJECT");
        imageView = findViewById(R.id.imagePosterExpand);
        plusFav = findViewById(R.id.imagePlus);
        checkFav = findViewById(R.id.imageCheck);

        movieExpandBinding.webViewExpand.getSettings().setJavaScriptEnabled(true);
        movieExpandBinding.webViewExpand.setWebChromeClient(new WebChromeClient());
        movieExpandBinding.webViewExpand.getSettings().setLoadWithOverviewMode(true);
        movieExpandBinding.webViewExpand.getSettings().setUseWideViewPort(true);
        movieExpandBinding.webViewExpand.getSettings().setPluginState(WebSettings.PluginState.ON);

        bindData(movieObject);
        movieNameString = movieObject.getMovieName();
        checkFav.setVisibility(View.INVISIBLE);

        context=this;
        expandTextIntent = new Intent(this,ExpandTextActivity.class);
        expandTextIntent.putExtra("MOVIEOBJECTE",movieObject);


        addToFav.setOnClickListener(onClickListenerFav);
        new checkIfExists().execute();

    }



    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
         startActivity(expandTextIntent);
        }
    };

    private View.OnClickListener onClickListenerFav = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            if(  checkFav.getVisibility() == View.VISIBLE){
                DBoperations dBoperations = new DBoperations(context);
               Boolean isSucssu=  dBoperations.removeItem(movieNameString);
                addToFav.setText(R.string.add_to_favorites);

            }
            new addtofav().execute();
        }
    };

//The class first checks if the movie exists in the DB. If true it changes the icon accordingly. If not it addes it to the
    //and changes the icon accordingly.

    private class checkIfExists extends AsyncTask<Void,Void,Boolean>{


        @Override
        protected Boolean doInBackground(Void... voids) {
            DBoperations dBoperations = new DBoperations(context);
            Boolean iSMovieExists = dBoperations.checkIfMovieExsits(movieNameString,context);
            return iSMovieExists;
        }

        @Override
        protected void onPostExecute(Boolean iSMovieExists) {
            super.onPostExecute(iSMovieExists);
            if(iSMovieExists) {
                plusFav.setVisibility(View.INVISIBLE);
                addToFav.setText(R.string.favadded);
                revalAnimation();
            }


        }
    }

    private class addtofav extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            plusFav.setVisibility(View.INVISIBLE);
            addToFav.setText(R.string.favadded);
              revalAnimation();


        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                DBoperations dBoperations = new DBoperations(context);
                Boolean isSuccses = dBoperations.addItem(movieObject, context);

                if (isSuccses) {

                    Snackbar snackbar = Snackbar.make((findViewById(R.id.constraintLayout)),"Movie Added to favorites",Snackbar.LENGTH_SHORT);

                } else {

                    Snackbar snackbar = Snackbar.make((findViewById(R.id.constraintLayout)),"There was an issue adding this movie",Snackbar.LENGTH_SHORT);

                }

            } catch (SQLException e) {
                Log.e(TAG, "Failed to insert Value" + e.getMessage());
                ToastUtil.createToast(errorMessageDb, context);

            }
            return null;
        }
    }

    public  void revalAnimation( ) {
        int cx = (checkFav.getWidth())/2;
        int cy = (checkFav.getHeight())/2;
        float finalRadius =  Math.max(checkFav.getWidth(), checkFav.getHeight());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Animator anim = ViewAnimationUtils.createCircularReveal(checkFav, cx, cy, 2, finalRadius);
            checkFav.setVisibility(View.VISIBLE);
            anim.start();
        } else {
            checkFav.setVisibility(View.VISIBLE);
        }

    }



    private void bindData(MovieObject movieObject){
        String movieDescr = movieObject.getMovieDescription();
        movieExpandBinding.webViewExpand.loadUrl(movieObject.getMovieTrailer());
        Picasso.with(this).load(movieObject.getMoviePoster()).fit().into(imageView);
        movieExpandBinding.movieTitleExpand.setText(movieObject.getMovieName());
        movieExpandBinding.movieTitleExpand.setTextSize(40);
        movieExpandBinding.Description.setText(movieDescr);
        movieExpandBinding.ratingText.setText(movieObject.getMovieRating());

        movieExpandBinding.Description.setOnClickListener(onClickListener);

    }

}






