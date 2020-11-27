package com.example.serpento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideStatusBar();
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

    public void playHandler(View view) {
        Intent gbIntent = new Intent(this, GameBoardActivity.class);
        startActivity(gbIntent);
    }

}