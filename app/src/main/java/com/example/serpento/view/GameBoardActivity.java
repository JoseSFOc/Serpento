package com.example.serpento.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.serpento.R;
import com.example.serpento.dataBase.ScoreContract.ScoreEntry;
import com.example.serpento.dataBase.ScoreDBHelper;
import com.example.serpento.model.Map;
import com.example.serpento.model.SingletonMap;

import java.util.SortedMap;
import java.util.TreeMap;

public class GameBoardActivity extends AppCompatActivity {

    private ScoreDBHelper dbHelper;
    private TextView scoreTextView;
    private SortedMap<String,Object> singletonMap;
    private String[] options = {"Exit"};
    private Map selectedMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        initMap();
        if((singletonMap.get("LHANDED") == null) || !Boolean.parseBoolean((String)singletonMap.get("LHANDED"))) {
            setContentView(R.layout.activity_game_board);
        } else {
            setContentView(R.layout.activity_game_board_left);
        }

        dbHelper = new ScoreDBHelper(getApplicationContext());
        scoreTextView = this.findViewById(R.id.scoreText);
        selectedMap = (Map)singletonMap.get("selectedMap");

        //Game game= new Game(CargarMapita, 1000, this.findViewById(R.id.gameBoardImgGreen),scoreTextView);
        //game.loop();
    }

    protected void onResume() {
        super.onResume();
        hideStatusBar();
    }

    private void initMap() {
        singletonMap = (SortedMap<String,Object>) SingletonMap.getInstance().get(MainActivity.SHARED_DATA_KEY);
        if(singletonMap == null) {
            singletonMap = new TreeMap<>();
            SingletonMap.getInstance().put(MainActivity.SHARED_DATA_KEY, singletonMap);
        }
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
        // Intent for returning to Main
        Intent openMainActivity = new Intent(this, MainActivity.class);
        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Menu")
               .setItems(options, (dialog, which) ->  startActivityIfNeeded(openMainActivity, 0));
        builder.show();
    }

    public void upPushed(View view) {
        Toast.makeText(this.getApplicationContext(), "UP", Toast.LENGTH_SHORT).show();
        //game.cambiarDireccion(Snake.ARRIBA);

    }

    public void rightPushed(View view) {
        Toast.makeText(this.getApplicationContext(), "RIGHT", Toast.LENGTH_SHORT).show();
        //game.cambiarDireccion(Snake.DERECHA);

    }

    public void downPushed(View view) {
        Toast.makeText(this.getApplicationContext(), "DOWN", Toast.LENGTH_SHORT).show();
        //game.cambiarDireccion(Snake.ABAJO);
    }

    public void leftPushed(View view) {
        Toast.makeText(this.getApplicationContext(), "LEFT", Toast.LENGTH_SHORT).show();
        //game.cambiarDireccion(Snake.IZQUIERDA);
    }

    public TextView getScoreTextView() {
        return scoreTextView;
    }

    public void setScoreTextView(TextView scoreTextView) {
        this.scoreTextView = scoreTextView;
    }
}