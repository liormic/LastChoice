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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.lior.lastchoice.Data.DBoperations;
import com.android.lior.lastchoice.Data.MovieObject;
import com.android.lior.lastchoice.R;
import com.android.lior.lastchoice.ToolBarInterface;
import com.android.lior.lastchoice.Utilities.ToastUtil;
import com.android.lior.lastchoice.databinding.ActivityMovieExpandBinding;
import com.squareup.picasso.Picasso;

public class MovieExpandActivity extends AppCompatActivity implements ToolBarInterface {
    private static final String TAG = MovieExpandActivity.class.getSimpleName();
    private ImageView imageView;
    private MovieObject movieObject;
    private ImageView plusFav;
    private final static String errorMessageDb = "Oh No! Error on adding the movie";
    private TextView addToFav;
    private ImageView checkFav;
    private Context context;
    private String movieNameString;
    private Intent expandTextIntent;
    private ActivityMovieExpandBinding movieExpandBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_movie_expand);
        movieExpandBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_expand);
        Intent intent = getIntent();
       // int position =intent.getIntExtra("position",1);
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }



        addToFav = findViewById(R.id.addtofavtext);
        movieObject = intent.getParcelableExtra("MOVIEOBJECT");
        imageView = findViewById(R.id.imagePosterExpand);
        plusFav = findViewById(R.id.imagePlus);
        checkFav = findViewById(R.id.imageCheck);
        invalidateOptionsMenu();

        movieExpandBinding.webViewExpand.getSettings().setJavaScriptEnabled(true);
        movieExpandBinding.webViewExpand.setWebChromeClient(new WebChromeClient());
        movieExpandBinding.webViewExpand.getSettings().setLoadWithOverviewMode(true);
        movieExpandBinding.webViewExpand.getSettings().setUseWideViewPort(true);
        movieExpandBinding.webViewExpand.getSettings().setPluginState(WebSettings.PluginState.ON);

        bindData(movieObject);
        movieNameString = movieObject.getMovieName();
        checkFav.setVisibility(View.INVISIBLE);

        context = this;
        expandTextIntent = new Intent(this, ExpandTextActivity.class);
        expandTextIntent.putExtra("MOVIEOBJECTE", movieObject);


        addToFav.setOnClickListener(onClickListenerFav);
        checkIfExists(movieNameString, context);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkIfExists(movieNameString, context);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkIfExists(movieNameString, context);
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

            case R.id.homeAsUp:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(expandTextIntent);
        }
    };

    private final View.OnClickListener onClickListenerFav = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            if(  checkFav.getVisibility() == View.VISIBLE){
                Boolean IsRemoved = null;
                DBoperations dBoperations = new DBoperations(context);
                IsRemoved= dBoperations.removeItem(movieNameString,context);
                if(IsRemoved) {
                    addToFav.setText(R.string.add_to_favorites);
                    checkFav.setVisibility(View.INVISIBLE);
                    revalAnimationRemove();
                    showSnackBar(getResources().getString(R.string.removedfromDB));
                }else{
                    showSnackBar(getResources().getString(R.string.errorRemoving));
                }

            }else {
                new addtofav().execute();
            }
        }
    };

//The class first checks if the movie exists in the DB. If true it changes the icon accordingly. If not it addes it to the
    //and changes the icon accordingly.

    private void checkIfExists(String movieNameString, Context context){


        DBoperations dBoperations = new DBoperations(context);
        Boolean iSMovieExists = dBoperations.checkIfMovieExsits(movieNameString,context);



        if(iSMovieExists) {
            plusFav.setVisibility(View.INVISIBLE);
            addToFav.setText(R.string.favadded);

            checkFav.setVisibility(View.VISIBLE);
            // revalAnimation();
        }else{
            checkFav.setVisibility(View.INVISIBLE);
            plusFav.setVisibility(View.VISIBLE);
            addToFav.setText(R.string.add_to_favorites);

        }



    }

    private class addtofav extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            plusFav.setVisibility(View.INVISIBLE);
            addToFav.setText(R.string.favadded);
            addToFav.setTextColor(getResources().getColor(R.color.BLACK));

            revalAnimationAdd();


        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                DBoperations dBoperations = new DBoperations(context);
                Boolean isSuccses = dBoperations.addItem(movieObject, context);

                if (isSuccses) {

                    showSnackBar(getResources().getString(R.string.succssAddingtoFav));
                } else {

                    showSnackBar(getResources().getString(R.string.errorAddingMovie));

                }

            } catch (SQLException e) {
                Log.e(TAG, "Failed to insert Value" + e.getMessage());
                ToastUtil.createToast(errorMessageDb, context);

            }
            return null;
        }
    }

    private void showSnackBar(String messageSnackbar){
        Snackbar snackbar = Snackbar.make((findViewById(R.id.constraintLayout)),messageSnackbar,Snackbar.LENGTH_SHORT);
        View sb = snackbar.getView();
        sb.setBackgroundColor(getResources().getColor(R.color.transperent));
        snackbar.show();
    }

    private void revalAnimationAdd() {
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


    private void revalAnimationRemove() {
        int cx = (plusFav.getWidth())/2;
        int cy = (plusFav.getHeight())/2;
        float finalRadius =  Math.max(plusFav.getWidth(), plusFav.getHeight());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Animator anim = ViewAnimationUtils.createCircularReveal(checkFav, cx, cy, 2, finalRadius);
            plusFav.setVisibility(View.VISIBLE);
            anim.start();
        } else {
            plusFav.setVisibility(View.VISIBLE);
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








