package com.example.serpento.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.serpento.R;
import com.example.serpento.dataBase.DifficultyContract;
import com.example.serpento.dataBase.DifficultyDBHelper;
import com.example.serpento.model.Difficulty;
import com.example.serpento.model.SingletonMap;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class DifficultySelectionActivity extends AppCompatActivity {

    private ListView difficultyListView;
    private List<Difficulty> difficultyList;
    private ArrayAdapter<Difficulty> adapter;
    private DifficultyDBHelper dbHelper;
    private Difficulty selectedDifficulty;
    private SortedMap<String,Object> singletonMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_selection);

        hideStatusBar();
        initMap();

        dbHelper = new DifficultyDBHelper(getApplicationContext());
        if((difficultyList = (List)singletonMap.get("difficultyList")) == null) difficultyList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, difficultyList);
        selectedDifficulty = new Difficulty();
        difficultyListView = this.findViewById(android.R.id.list);
        difficultyListView.setOnItemClickListener((parent, view, position, id) -> {
            Difficulty item = (Difficulty)parent.getItemAtPosition(position);
            setSelectedDifficulty(item);
        });

        if(difficultyList.size() == 0) {
            readMapDatabase();
        }
        populateList();
    }

    protected void onResume() {
        super.onResume();
        hideStatusBar();
    }

    private void hideStatusBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
                DifficultyContract.DifficultyEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if(cursor.getCount() == 0) loadMaps();

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DifficultyContract.DifficultyEntry.COLUMN_NAME));
            float scoreMulti = cursor.getFloat(cursor.getColumnIndexOrThrow(DifficultyContract.DifficultyEntry.COLUMN_SCORE));
            float speedMulti = cursor.getFloat(cursor.getColumnIndexOrThrow(DifficultyContract.DifficultyEntry.COLUMN_SPEED));
            difficultyList.add(new Difficulty(name, scoreMulti, speedMulti));
        }
        cursor.close();

        if(difficultyList.size() == 0) throw new SQLException();

        singletonMap.put("difficultyList", difficultyList);
    }

    public Difficulty getSelectedDifficulty() {
        return selectedDifficulty;
    }

    public void setSelectedDifficulty(Difficulty selectedDifficulty) {
        this.selectedDifficulty = selectedDifficulty;
        beginGame();
    }

    private void beginGame() {
        Intent gbIntent = new Intent(this, GameBoardActivity.class);
        singletonMap.put("selectedDifficulty", selectedDifficulty);
        startActivity(gbIntent);
    }

    private void populateList() {
        // Take scores list and insert it into scoreListView
        difficultyListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void loadMaps() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Mapa de valores nuevo
        ContentValues easy = new ContentValues();
        easy.put(DifficultyContract.DifficultyEntry.COLUMN_NAME, getResources().getString(R.string.easy));
        easy.put(DifficultyContract.DifficultyEntry.COLUMN_SCORE, 1);
        easy.put(DifficultyContract.DifficultyEntry.COLUMN_SPEED, 1.1);

        ContentValues normal = new ContentValues();
        normal.put(DifficultyContract.DifficultyEntry.COLUMN_NAME, getResources().getString(R.string.normal));
        normal.put(DifficultyContract.DifficultyEntry.COLUMN_SCORE, 1.5);
        normal.put(DifficultyContract.DifficultyEntry.COLUMN_SPEED, 1.3);

        ContentValues hard = new ContentValues();
        hard.put(DifficultyContract.DifficultyEntry.COLUMN_NAME, getResources().getString(R.string.hard));
        hard.put(DifficultyContract.DifficultyEntry.COLUMN_SCORE, 2);
        hard.put(DifficultyContract.DifficultyEntry.COLUMN_SPEED, 1.5);

        // Insertamos las lineas
        db.insert(DifficultyContract.DifficultyEntry.TABLE_NAME, null, easy);
        db.insert(DifficultyContract.DifficultyEntry.TABLE_NAME, null, normal);
        db.insert(DifficultyContract.DifficultyEntry.TABLE_NAME, null, hard);

        db.close();

        readMapDatabase();
    }

    public void backHandler(View view) {
        finish();
    }
}