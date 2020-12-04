package com.example.serpento.dataBase;

import android.provider.BaseColumns;

public final class MapContract {

    private MapContract() {
    }

    public static class MapEntry implements BaseColumns {
        public static final String TABLE_NAME = "map";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ROW = "row";
        public static final String COLUMN_COLUMN = "column";
        public static final String COLUMN_DIRECTION = "direction";
        public static final String COLUMN_MAP = "map";

        protected static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + " (" +
                MapEntry._ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME + " TEXT," +
                COLUMN_ROW + " INTEGER," +
                COLUMN_COLUMN + " INTEGER," +
                COLUMN_DIRECTION + " TEXT," +
                COLUMN_MAP + " TEXT)";

        protected static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

}
