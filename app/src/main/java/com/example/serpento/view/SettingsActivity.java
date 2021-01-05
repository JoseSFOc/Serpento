package com.example.serpento.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.example.serpento.R;
import com.example.serpento.dataBase.SettingsContract;
import com.example.serpento.dataBase.SettingsDBHelper;
import com.example.serpento.model.SingletonMap;

import java.util.SortedMap;
import java.util.TreeMap;

public class SettingsActivity extends AppCompatActivity {

    private SettingsDBHelper dbHelper;
    private Switch leftSwitch;
    private SortedMap<String, Object> singletonMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        dbHelper = new SettingsDBHelper(getApplicationContext());
        leftSwitch = findViewById(R.id.leftSwitch);

        initMap();
        readSettings();
        hideStatusBar();
    }

    private void initMap() {
        singletonMap = (SortedMap<String,Object>) SingletonMap.getInstance().get(MainActivity.SHARED_DATA_KEY);
        if(singletonMap == null) {
            singletonMap = new TreeMap<>();
            SingletonMap.getInstance().put(MainActivity.SHARED_DATA_KEY, singletonMap);
        }
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

            if(setting.equals("LHANDED")) leftSwitch.setChecked(Boolean.parseBoolean(value));
        }
    }

    private void hideStatusBar() {
        // Hide the status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void applyChanges(View view) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SettingsContract.SettingsEntry.COLUMN_SETTING, "LHANDED");
        values.put(SettingsContract.SettingsEntry.COLUMN_VALUE, leftSwitch.isChecked() + "");

        db.replace(SettingsContract.SettingsEntry.TABLE_NAME, null, values);
        singletonMap.put("LHANDED", leftSwitch.isChecked());


        Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.saved), Toast.LENGTH_SHORT);
        toast.show();

    }

    public void backHandler(View view) {
        finish();
    }


}