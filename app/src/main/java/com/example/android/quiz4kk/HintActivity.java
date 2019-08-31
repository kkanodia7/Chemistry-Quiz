package com.example.android.quiz4kk;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HintActivity extends AppCompatActivity {

    private Button mHintButton;
    private Button mCloseButton;
    private TextView mAnswer;
    private int qHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);

        setResult(RESULT_CANCELED);

        //DisplayMetrics dm = new DisplayMetrics();
        //getWindowManager().getDefaultDisplay().getMetrics(dm);

        //int width = dm.widthPixels;
        //int height = dm.heightPixels;

        //getWindow().setLayout((int)(width * .8), (int)(height * .4));
        getWindow().setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);


        qHint = getIntent().getIntExtra(getString(R.string.q_hint_tag), R.string.no_hint);


        mHintButton = (Button) findViewById(R.id.show_answer_button);
        mCloseButton = (Button) findViewById(R.id.close_hint_button);
        mAnswer = (TextView) findViewById(R.id.answer_text_view);

        mHintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                mAnswer.setVisibility(View.VISIBLE);
                mAnswer.setText(qHint);
                getWindow().setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
                mHintButton.setBackground(getResources().getDrawable(R.drawable.rounded_btn_glow));
                mHintButton.setEnabled(false);
            }
        });

        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
