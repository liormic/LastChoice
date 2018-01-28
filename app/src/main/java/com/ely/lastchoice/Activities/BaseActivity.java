package com.ely.lastchoice.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ely.lastchoice.R;

public abstract class BaseActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());

        createToolBar();
    }

    protected abstract int getLayoutResource();


    private void createToolBar() {
        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {

            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

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
            case R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
