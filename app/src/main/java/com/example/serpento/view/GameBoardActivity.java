package com.example.serpento.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.serpento.R;
import com.example.serpento.controller.Juego;
import com.example.serpento.dataBase.ScoreContract.ScoreEntry;
import com.example.serpento.dataBase.ScoreDBHelper;
import com.example.serpento.model.Map;
import com.example.serpento.model.Game;
import com.example.serpento.model.SingletonMap;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class GameBoardActivity extends AppCompatActivity {

    private ScoreDBHelper dbHelper;
    private TextView scoreTextView;
    private SortedMap<String, Object> singletonMap;
    private List<String> options;
    private Map selectedMap;
    private Game game;

    private Juego juego;
    private int width, height;
    private SurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initMap();
        if ((singletonMap.get("LHANDED") == null) || !Boolean.parseBoolean((String) singletonMap.get("LHANDED"))) {
            setContentView(R.layout.activity_game_board);
        } else {
            setContentView(R.layout.activity_game_board_left);
        }

        options = new ArrayList<>();
        options.add(getResources().getString(R.string.menuExit));

        dbHelper = new ScoreDBHelper(getApplicationContext());
        scoreTextView = this.findViewById(R.id.scoreText);
        selectedMap = (Map) singletonMap.get("selectedMap");
        surfaceView = findViewById(R.id.boardSurface);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        //
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        width = Math.round((1 - (260 / dpWidth)) * size.x);
        height = size.y;



        //game = new Game(selectedMap, 100000, this.findViewById(R.id.gameBoardImgGreen), scoreTextView, this);

        juego = new Juego(this, new Point(width, height), this);
        //setContentView(gameEngine);
    }

    @Override
    protected void onResume() {
        super.onResume();
        juego.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        juego.pause();
    }


    private void initMap() {
        singletonMap = (SortedMap<String, Object>) SingletonMap.getInstance().get(MainActivity.SHARED_DATA_KEY);
        if (singletonMap == null) {
            singletonMap = new TreeMap<>();
            SingletonMap.getInstance().put(MainActivity.SHARED_DATA_KEY, singletonMap);
        }
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
        juego.pause();

        Intent openMainActivity = new Intent(this, MainActivity.class);
        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Menu")
                .setItems(options.toArray(new String[options.size()]), (dialog, which) -> startActivityIfNeeded(openMainActivity, 0));
        builder.setOnDismissListener(arg0 -> juego.resume());
        builder.show();
    }

    public void upPushed(View view) {
        //game.cambiarDireccion(Snake.ARRIBA);
        juego.setDireccion(Juego.direccion.ARRIBA);
    }

    public void rightPushed(View view) {
        //game.cambiarDireccion(Snake.DERECHA);
        juego.setDireccion(Juego.direccion.DERECHA);
    }

    public void downPushed(View view) {
        //game.cambiarDireccion(Snake.ABAJO);
        juego.setDireccion(Juego.direccion.ABAJO);
    }

    public void leftPushed(View view) {
        //game.cambiarDireccion(Snake.IZQUIERDA);
        juego.setDireccion(Juego.direccion.IZQUIERDA);
    }

    public TextView getScoreTextView() {
        return scoreTextView;
    }

    public void setScoreText(String score) {
        this.scoreTextView.setText(score);
    }

    public SurfaceView getSurfaceView() {
        return surfaceView;
    }

    public void setSurfaceView(SurfaceView surfaceView) {
        this.surfaceView = surfaceView;
    }
}