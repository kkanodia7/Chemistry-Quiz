package com.example.android.quiz4kk;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ScoreActivity extends AppCompatActivity {

    private TextView mScoreText;
    private TextView mHighScoreText;
    private Button mPlayAgainButton;
    private Button mQuitButton;
    private int score;
    private int highScore;


    private MyRecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        setResult(RESULT_CANCELED);

        getWindow().setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

        ArrayList<String> people = new ArrayList<String>();
        String s1 = getIntent().getStringExtra("place1");
        String s2 = getIntent().getStringExtra("place2");
        String s3 = getIntent().getStringExtra("place3");
        people.add(s1);
        people.add(s2);
        people.add(s3);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.workshopParticipants);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, people);
        recyclerView.setAdapter(adapter);

        score = getIntent().getIntExtra(getString(R.string.new_score_tag), 0);
        //highScore = getIntent().getIntExtra(getString(R.string.high_score_tag), 0);

        mScoreText = (TextView) findViewById(R.id.score_label);
        //mHighScoreText = (TextView) findViewById(R.id.high_score_label);
        mPlayAgainButton = (Button) findViewById(R.id.play_again_button);
        mQuitButton = (Button) findViewById(R.id.quit_button);

        mScoreText.setText(mScoreText.getText() + " " + score);
        //mHighScoreText.setText(mHighScoreText.getText() + " " + highScore);

        mPlayAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }
        });

        mQuitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(5);
                finish();
            }
        });

    }
}
