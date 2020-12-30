package com.example.serpento.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.serpento.R;
import com.example.serpento.dataBase.MapContract;
import com.example.serpento.dataBase.MapDBHelper;
import com.example.serpento.model.Map;
import com.example.serpento.model.SingletonMap;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class MapSelectionActivity extends AppCompatActivity {

    private ListView mapListView;
    private List<Map> mapList;
    private ArrayAdapter<Map> adapter;
    private MapDBHelper dbHelper;
    private Map selectedMap;
    private SortedMap<String,Object> singletonMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_selection);

        hideStatusBar();
        initMap();

        dbHelper = new MapDBHelper(getApplicationContext());
        if((mapList = (List)singletonMap.get("mapList")) == null) mapList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, mapList);
        selectedMap = new Map();
        mapListView = this.findViewById(android.R.id.list);
        mapListView.setOnItemClickListener((parent, view, position, id) -> {
            Map item = (Map)parent.getItemAtPosition(position);
            setSelectedMap(item);
        });

        if(mapList.size() == 0) {
            try{
                readMapDatabase();
            } catch(SQLException e) {
                loadMaps();
                readMapDatabase();
            }
        }
        populateList();
    }

    protected void onResume() {
        super.onResume();
        hideStatusBar();
    }

    private void initMap() {
        singletonMap = (SortedMap<String,Object>)SingletonMap.getInstance().get(MainActivity.SHARED_DATA_KEY);
        if(singletonMap == null) {
            singletonMap = new TreeMap<>();
            SingletonMap.getInstance().put(MainActivity.SHARED_DATA_KEY, singletonMap);
        }
    }


    private void readMapDatabase() throws SQLException {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                MapContract.MapEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(MapContract.MapEntry.COLUMN_NAME));
            int row = cursor.getInt(cursor.getColumnIndexOrThrow(MapContract.MapEntry.COLUMN_ROW));
            int col = cursor.getInt(cursor.getColumnIndexOrThrow(MapContract.MapEntry.COLUMN_COLUMN));
            String direction = cursor.getString(cursor.getColumnIndexOrThrow(MapContract.MapEntry.COLUMN_DIRECTION));
            String mapString = cursor.getString(cursor.getColumnIndexOrThrow(MapContract.MapEntry.COLUMN_MAP));
            mapList.add(new Map(name, row, col, direction, mapString));
        }
        cursor.close();

        if(mapList.size() == 0) throw new SQLException();

        singletonMap.put("mapList",mapList);
    }

    public Map getSelectedMap() {
        return selectedMap;
    }

    public void setSelectedMap(Map selectedMap) {
        this.selectedMap = selectedMap;
        beginGame();
    }

    private void beginGame() {
        Intent gbIntent = new Intent(this, GameBoardActivity.class);
        singletonMap.put("selectedMap", selectedMap);
        startActivity(gbIntent);
    }

    private void hideStatusBar() {
        // Hide the status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void populateList() {
        // Take scores list and insert it into scoreListView
        mapListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void loadMaps() {
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(MapContract.MapEntry.COLUMN_NAME, "MAP 0 - EMPTY");
        values.put(MapContract.MapEntry.COLUMN_ROW, 15);
        values.put(MapContract.MapEntry.COLUMN_COLUMN, 15);
        values.put(MapContract.MapEntry.COLUMN_DIRECTION, "ARRIBA");
        values.put(MapContract.MapEntry.COLUMN_MAP,"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "X                                                                                                  X\n" +
                "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

        // Insert the new row, returning the primary key value of the new row
        db.insert(MapContract.MapEntry.TABLE_NAME, null, values);

        readMapDatabase();
    }

    public void backHandler(View view) {
        finish();
    }
}