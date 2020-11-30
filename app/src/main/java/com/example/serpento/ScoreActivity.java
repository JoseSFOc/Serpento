package com.example.serpento;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ScoreActivity extends AppCompatActivity {

    private ListView scoresListView;
    private List<Score> scoreList;
    private ArrayAdapter<Score> adapter;


    // Mockup for the list
    private class Score {
        public long getId() {
            return 0;
        }
        public String toString() {
            return new Random().nextInt() + "";
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        scoreList = new ArrayList<>();

        scoreList.add(new Score());
        scoreList.add(new Score());
        scoreList.add(new Score());

        adapter = new ArrayAdapter<Score>(this, android.R.layout.simple_list_item_1 ,scoreList);

        hideStatusBar();

        scoresListView = this.findViewById(android.R.id.list);
        populateList();
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