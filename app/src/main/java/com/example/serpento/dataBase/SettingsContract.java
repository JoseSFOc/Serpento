package com.example.serpento.dataBase;

import android.provider.BaseColumns;

public class SettingsContract {

    private SettingsContract() {}

    public static class SettingsEntry implements BaseColumns {
        public static final String TABLE_NAME = "settings";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_SETTING = "setting";
        public static final String COLUMN_VALUE = "value";

        protected static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_SETTING + " TEXT PRIMARY KEY," +
                COLUMN_VALUE + " TEXT)";

        protected static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

}
