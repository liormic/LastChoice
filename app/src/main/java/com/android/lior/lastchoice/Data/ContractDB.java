package com.android.lior.lastchoice.Data;

import android.provider.BaseColumns;

/**
 * Created by lior on 12/25/17.
 */

public class ContractDB   {

    private ContractDB(){};


    public class MovieData implements BaseColumns{

        public static final String TABLE_NAME="movies";
        public static final String COLUMN_MOVIENAME="moviename";
        public static final String COLUMN_Description="description";
        public static final String COLUMN_YOUTUBEURL="url";
        public static final String COLUMN_MOVIEIMAGE="image";
        public static final String COLUMN_MOVIERATING="rating";

    }
}
