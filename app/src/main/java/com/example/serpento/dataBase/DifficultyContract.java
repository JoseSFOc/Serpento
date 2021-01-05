package com.example.serpento.dataBase;

import android.provider.BaseColumns;

public final class DifficultyContract {

    private DifficultyContract() {
    }

    public static class DifficultyEntry implements BaseColumns {
        public static final String TABLE_NAME = "difficulty";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SCORE = "score";
        public static final String COLUMN_SPEED = "speed";

        protected static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + " (" +
                DifficultyEntry._ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME + " TEXT," +
                COLUMN_SCORE + " REAL," +
                COLUMN_SPEED + " REAL)";

        protected static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

}
