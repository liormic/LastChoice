package com.android.lior.lastchoice.Data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by lior on 12/27/17.
 */

@SuppressWarnings("DefaultFileTemplate")
public class DBhelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION =2;
    private static final String TAG = DBhelper.class.getSimpleName();



    public DBhelper(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + ContractDB.MovieData.TABLE_NAME + " (" +
                    ContractDB.MovieData._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ContractDB.MovieData.COLUMN_MOVIENAME + " TEXT NOT NULL, " +
                    ContractDB.MovieData.COLUMN_Description + " TEXT NOT NULL, " +
                    ContractDB.MovieData.COLUMN_MOVIEIMAGE + " TEXT NOT NULL, " +
                    ContractDB.MovieData.COLUMN_YOUTUBEURL + " TEXT NOT NULL, " +
                    ContractDB.MovieData.COLUMN_MOVIERATING + " TEXT NOT NULL" +
                    "); ";

            sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
        }catch (SQLException e){
            Log.e(TAG,"ERROR ERROR "+e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ContractDB.MovieData.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
