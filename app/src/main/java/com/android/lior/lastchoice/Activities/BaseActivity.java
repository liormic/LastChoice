package com.android.lior.lastchoice.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.android.lior.lastchoice.R;

public abstract class BaseActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
//        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        //     getSupportActionBar().setDisplayUseLogoEnabled(true);
        createToolBar();
    }

    protected abstract int getLayoutResource();


    private void createToolBar() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        if (toolbar != null) {

            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


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
