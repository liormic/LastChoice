package com.ely.lastchoice.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;


/**
 * Created by lior on 12/27/17.
 */

@SuppressWarnings("DefaultFileTemplate")
public class DBoperations {
    private final SQLiteDatabase db;

    public DBoperations(Context context) {
        SQLiteOpenHelper dBhelper = new DBhelper(context);
       db = dBhelper.getWritableDatabase();
    }

    public Cursor getAllitems() {
        return db.query(ContractDB.MovieData.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null

        );
    }

    public boolean checkIfMovieExsits(String movieName, Context context) {
        SQLiteDatabase db;
        DBhelper dBhelper = new DBhelper(context);
        db = dBhelper.getReadableDatabase();
        String table = ContractDB.MovieData.TABLE_NAME;
        String[] columns = {ContractDB.MovieData.COLUMN_MOVIENAME};
        String selection = ContractDB.MovieData.COLUMN_MOVIENAME + "=?";
        String[] selectionArgs = {movieName};


        Cursor cursor = db.query(table, columns, selection, selectionArgs, null, null, null, null);

        if (cursor.getCount() < 1) {
            cursor.close();
            return false;
        } else {
            cursor.close();
            return true;
        }


    }

    public Boolean addItem(MovieObject movieObject, Context context) {

        SQLiteDatabase db;
        DBhelper dBhelper = new DBhelper(context);
        db = dBhelper.getWritableDatabase();
        //   Boolean isMovieExists = checkIfMovieExsits(movieObject.getMovieName(),context);

        ContentValues contentValues = new ContentValues();
        String movieName = movieObject.getMovieName();
        String movieDescription = movieObject.getMovieDescription();
        String moviePoster = movieObject.getMoviePoster();
        String movieRating = movieObject.getMovieRating();
        String movieTrailer = movieObject.getMovieTrailer();

        contentValues.put(ContractDB.MovieData.COLUMN_MOVIENAME, movieName);
        contentValues.put(ContractDB.MovieData.COLUMN_Description, movieDescription);
        contentValues.put(ContractDB.MovieData.COLUMN_MOVIEIMAGE, moviePoster);
        contentValues.put(ContractDB.MovieData.COLUMN_YOUTUBEURL, movieTrailer);
        contentValues.put(ContractDB.MovieData.COLUMN_MOVIERATING, movieRating);
        db.insert(ContractDB.MovieData.TABLE_NAME, null, contentValues);
        db.close();
        return true;


    }


    public Boolean removeItem(String name, Context context) {
        SQLiteDatabase db;
        DBhelper dBhelper = new DBhelper(context);
        db = dBhelper.getWritableDatabase();
        Boolean b = null;
        try {
            b= db.delete(ContractDB.MovieData.TABLE_NAME,
                    ContractDB.MovieData.COLUMN_MOVIENAME + "='" + name+"'", null) > 0;

        } catch (SQLException e) {
            Log.e(TAG, "ERrror on removing" + e.getMessage());
        }
        return b;
    }

    public boolean removeAllItems(){

        try {
            db.delete(ContractDB.MovieData.TABLE_NAME, null, null);
            db.close();
            return true;
        }catch (SQLException e){
            return false;
        }
    }
}







