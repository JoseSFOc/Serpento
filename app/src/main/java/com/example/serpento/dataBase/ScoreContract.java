package com.example.serpento.dataBase;

import android.provider.BaseColumns;

public class ScoreContract {

    private ScoreContract() {}

    public static class ScoreEntry implements BaseColumns {
        public static final String TABLE_NAME = "score";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_PLAYER = "player";
        public static final String COLUMN_SCORE = "score";

        protected static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + " (" +
                ScoreEntry._ID + " INTEGER PRIMARY KEY," +
                COLUMN_PLAYER + " TEXT," +
                COLUMN_SCORE + " INTEGER)";
        protected static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

}
