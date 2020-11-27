package com.example.serpento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class GameBoardActivity extends AppCompatActivity {

    private TextView scoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);
        hideStatusBar();

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

    public void menuPushed(View view) {
        Toast.makeText(this.getApplicationContext(), "MENU", Toast.LENGTH_SHORT).show();
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