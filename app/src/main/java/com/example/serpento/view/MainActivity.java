package com.example.serpento.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.serpento.R;
import com.example.serpento.dataBase.SettingsContract;
import com.example.serpento.dataBase.SettingsDBHelper;
import com.example.serpento.model.SingletonMap;

import java.util.SortedMap;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    public static final String SHARED_DATA_KEY = "SHARED_MAP_KEY";
    private SortedMap<String, Object> singletonMap;
    private SettingsDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideStatusBar();

        dbHelper = new SettingsDBHelper(getApplicationContext());
        initMap();
        this.deleteDatabase("serpentoMaps.db");
    }

    private void initMap() {
        singletonMap = (SortedMap<String,Object>)SingletonMap.getInstance().get(MainActivity.SHARED_DATA_KEY);
        if(singletonMap == null) {
            singletonMap = new TreeMap<>();
            SingletonMap.getInstance().put(SHARED_DATA_KEY, singletonMap);
        }
    }

    protected void onResume() {
        super.onResume();
        hideStatusBar();
    }

    private void readSettings() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                SettingsContract.SettingsEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        while(cursor.moveToNext()) {
            String setting = cursor.getString(cursor.getColumnIndexOrThrow(SettingsContract.SettingsEntry.COLUMN_SETTING));
            String value = cursor.getString(cursor.getColumnIndexOrThrow(SettingsContract.SettingsEntry.COLUMN_VALUE));

            if(setting.equals("LHANDED")) singletonMap.put("LHANDED", value);
        }
    }

    private void hideStatusBar() {
        // Fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void playHandler(View view) {
        readSettings();
        Intent gbIntent = new Intent(this, DifficultySelectionActivity.class);
        startActivity(gbIntent);
    }

    public void settingsHandler(View view) {
        Intent gbIntent = new Intent(this, SettingsActivity.class);
        startActivity(gbIntent);
    }

    public void scoresHandler(View view) {
        Intent gbIntent = new Intent(this, ScoreActivity.class);
        startActivity(gbIntent);
    }


}