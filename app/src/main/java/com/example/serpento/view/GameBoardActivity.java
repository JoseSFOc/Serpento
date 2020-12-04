package com.example.serpento.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.serpento.R;
import com.example.serpento.dataBase.ScoreContract.ScoreEntry;
import com.example.serpento.dataBase.ScoreDBHelper;

public class GameBoardActivity extends AppCompatActivity {

    private ScoreDBHelper dbHelper;
    private TextView scoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);
        hideStatusBar();

        dbHelper = new ScoreDBHelper(getApplicationContext());
        scoreTextView = this.findViewById(R.id.scoreText);
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

    private void saveHighScore() {
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ScoreEntry.COLUMN_PLAYER, "AAA");
        values.put(ScoreEntry.COLUMN_SCORE, 9999);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(ScoreEntry.TABLE_NAME, null, values);
    }

    public void menuPushed(View view) {
        Toast.makeText(this.getApplicationContext(), "MENU", Toast.LENGTH_SHORT).show();
        saveHighScore();
    }

    public void upPushed(View view) {
        Toast.makeText(this.getApplicationContext(), "UP", Toast.LENGTH_SHORT).show();
    }

    public void rightPushed(View view) {
        Toast.makeText(this.getApplicationContext(), "RIGHT", Toast.LENGTH_SHORT).show();
    }

    public void downPushed(View view) {
        Toast.makeText(this.getApplicationContext(), "DOWN", Toast.LENGTH_SHORT).show();
    }

    public void leftPushed(View view) {
        Toast.makeText(this.getApplicationContext(), "LEFT", Toast.LENGTH_SHORT).show();
    }

    public TextView getScoreTextView() {
        return scoreTextView;
    }

    public void setScoreTextView(TextView scoreTextView) {
        this.scoreTextView = scoreTextView;
    }
}