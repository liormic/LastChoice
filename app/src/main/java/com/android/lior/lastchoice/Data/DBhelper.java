package com.android.lior.lastchoice.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lior on 12/27/17.
 */

public class DBhelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION =1;




    public DBhelper(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       final String SQL_CREATE_MOVIE_TABLE= "CREATE TANLE " + ContractDB.MovieData.TABLE_NAME +" (" +
               ContractDB.MovieData._ID + "INTERGER PRIMARY KEY AUTOINCREMENT,"+
               ContractDB.MovieData.COLUMN_MOVIENAME +"TEXT NOT NULL, " +
               ContractDB.MovieData.COLUMN_Description+"TEXT NOT NULL, "+
               ContractDB.MovieData.COLUMN_MOVIEIMAGE+"MOVIE IMAGE, "+
               ContractDB.MovieData.COLUMN_YOUTUBEURL+"YOUTUBE URL NOT NULL"+
               ContractDB.MovieData.COLUMN_MOVIERATING+" INTEGER"+
               "); ";
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
