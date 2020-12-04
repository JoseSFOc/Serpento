package com.example.serpento.view;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.serpento.R;
import com.example.serpento.dataBase.ScoreContract.ScoreEntry;
import com.example.serpento.dataBase.ScoreDBHelper;
import com.example.serpento.model.Score;

import java.util.ArrayList;
import java.util.List;


public class ScoreActivity extends AppCompatActivity {

    private ListView scoresListView;
    private List<Score> scoreList;
    private ArrayAdapter<Score> adapter;
    private ScoreDBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        dbHelper = new ScoreDBHelper(getApplicationContext());
        scoreList = new ArrayList<>();
        adapter = new ArrayAdapter<Score>(this, android.R.layout.simple_list_item_1, android.R.id.text1, scoreList);
        scoresListView = this.findViewById(android.R.id.list);

        hideStatusBar();
        readScoreDatabase();
        populateList();
    }

    private void readScoreDatabase() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                ScoreEntry.COLUMN_PLAYER,
                ScoreEntry.COLUMN_SCORE
        };

        // Filter results WHERE "title" = 'My Title'
        //String selection = ScoreEntry.COLUMN_PLAYER + " = ?";
        //String[] selectionArgs = {"My Player"};

        // How you want the results sorted in the resulting Cursor
        String sortOrder = ScoreEntry.COLUMN_SCORE + " DESC";

        Cursor cursor = db.query(
                ScoreEntry.TABLE_NAME, // The table to query
                projection,            // The array of columns to return (pass null to get all)
                null,         //selection,  The columns for the WHERE clause
                null,     //selectionArgs, The values for the WHERE clause
                null,         // don't group the rows
                null,           // don't filter by row groups
                sortOrder               // The sort order
        );

        while (cursor.moveToNext()) {
            String player = cursor.getString(cursor.getColumnIndexOrThrow(ScoreEntry.COLUMN_PLAYER));
            Long score = cursor.getLong(cursor.getColumnIndexOrThrow(ScoreEntry.COLUMN_SCORE));
            scoreList.add(new Score(player, score.intValue()));
        }
        cursor.close();
    }

    protected void onResume() {
        super.onResume();
        hideStatusBar();
    }

    private void hideStatusBar() {
        // Hide the status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void populateList() {
        // Take scores list and insert it into scoreListView
        scoresListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void backHandler(View view) {
        finish();
    }

}