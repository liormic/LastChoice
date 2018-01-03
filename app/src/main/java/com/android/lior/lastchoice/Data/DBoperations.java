package com.android.lior.lastchoice.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by lior on 12/27/17.
 */

public class DBoperations {
    SQLiteDatabase db;
    SQLiteOpenHelper dBhelper;

    public DBoperations(Context context){
      dBhelper = new DBhelper(context);
      db = dBhelper.getWritableDatabase();
    }

   public void close (){
        dBhelper.close();
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
    public void addItem(MovieObject movieObject){

        ContentValues contentValues = null;
        String movieName = movieObject.getMovieName();
        String movieDescription = movieObject.getMovieDescription();
        String moviePoster = movieObject.getMoviePoster();
        String movieRating = movieObject.getMovieRating();
        String movieTrailer= movieObject.getMovieTrailer();

        contentValues.put(ContractDB.MovieData.COLUMN_MOVIENAME,movieName);
        contentValues.put(ContractDB.MovieData.COLUMN_Description,movieDescription);
        contentValues.put(ContractDB.MovieData.COLUMN_MOVIEIMAGE,moviePoster);
        contentValues.put(ContractDB.MovieData.COLUMN_YOUTUBEURL,movieTrailer);
        contentValues.put(ContractDB.MovieData.COLUMN_MOVIERATING,movieRating);
        db.close();

       }


    public void removeItem(long id){

        db.delete(ContractDB.MovieData.TABLE_NAME,
                ContractDB.MovieData._ID+"=" + id,null);

    }



   }


