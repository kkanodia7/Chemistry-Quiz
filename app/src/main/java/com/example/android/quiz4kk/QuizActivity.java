package com.example.android.quiz4kk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mHintButton;
    private TextView mQuestionTextView;
    private TextView mQuestionNumber;
    private TextView mScoreText;
    private TextView mNameText;
    private Button mSubmitButton;
    private EditText mNameSpace;

    private ProgressBar mProgressBar;

    private int score = 0;
    private int points = 10;

    private int val1 = -1;
    private int val2 = -1;
    private int val3 = -1;
    private String name1 = "";
    private String name2 = "";
    private String name3 = "";
    private String s1 = "";
    private String s2 = "";
    private String s3 = "";


    private String myName = "";


    SharedPreferences scoreData;
    SharedPreferences.Editor editor;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref1 = database.getReference("first");
    private DatabaseReference ref2 = database.getReference("second");
    private DatabaseReference ref3 = database.getReference("third");


    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.q1, true, R.string.q1_hint),
            new Question(R.string.q2, true, R.string.q2_hint),
            new Question(R.string.q3, false, R.string.q3_hint),
            new Question(R.string.q4, false, R.string.q4_hint),
            new Question(R.string.q5, true, R.string.q5_hint),
            new Question(R.string.q6, false, R.string.q6_hint),
            new Question(R.string.q7, true, R.string.q7_hint),
            new Question(R.string.q8, false, R.string.q8_hint),
            new Question(R.string.q9, true, R.string.q9_hint),
            new Question(R.string.q10, false, R.string.q10_hint),
    };
    private int mCurrentIndex = 0;


    private void checkAnswer(boolean userPressed) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        if (userPressed == answerIsTrue) {
            messageResId = R.string.correct_toast;
            score += points;
            mHintButton.setEnabled(false);
            mHintButton.setBackground(getResources().getDrawable(R.drawable.rounded_btn));
        } else {
            messageResId = R.string.incorrect_toast;
            mHintButton.setBackground(getResources().getDrawable(R.drawable.rounded_btn_glow));

        }

        mNextButton.setEnabled(true);
        mTrueButton.setEnabled(false);
        mFalseButton.setEnabled(false);
        points = 10;
        mScoreText.setText(getString(R.string.score_label) + " " + score);
        Toast.makeText(QuizActivity.this, messageResId, Toast.LENGTH_SHORT).show();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mNameText = (TextView) findViewById(R.id.name_txt);
        mNameSpace = (EditText) findViewById(R.id.name_space);
        mSubmitButton = (Button) findViewById(R.id.submit_btn);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);

        mQuestionNumber = (TextView) findViewById(R.id.question_number);
        mQuestionNumber.setText(getString(R.string.word_question) + " " + (mCurrentIndex + 1) + "/" + mQuestionBank.length);

        mScoreText = (TextView) findViewById(R.id.score_text);
        mScoreText.setText(getString(R.string.score_label) + " " + score);


        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mNextButton = (Button) findViewById(R.id.next_button);
        mHintButton = (Button) findViewById(R.id.hint_button);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);


        scoreData = getSharedPreferences("myScore", MODE_PRIVATE);
        editor = scoreData.edit();


        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!((""+mNameSpace.getText()).equals(""))) {
                    myName = ""+mNameSpace.getText();
                    mTrueButton.setEnabled(true);
                    mFalseButton.setEnabled(true);
                    mNameText.setVisibility(View.GONE);
                    mNameSpace.setVisibility(View.GONE);
                    mSubmitButton.setVisibility(View.GONE);
                }
            }
        });


        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex++;
                //editor.clear().commit();
                mProgressBar.setProgress(mCurrentIndex);
                if (mCurrentIndex >= mQuestionBank.length) {
                    //if (score > scoreData.getInt(getString(R.string.high_score_tag), 0)) {
                    if (score > val1) {
                        editor.putInt(getString(R.string.high_score_tag), score).commit();
                        ref3.setValue(name2 + " " + val2);
                        ref2.setValue(name1 + " " + val1);
                        ref1.setValue(myName + " " + score);
                        s3 = name2 + " - " + val2;
                        s2 = name1 + " - " + val1;
                        s1 = myName + " - " + score;
                    }
                    else if (score > val2) {
                        ref3.setValue(name2 + " " + val2);
                        ref2.setValue(myName + " " + score);
                        s3 = name2 + " - " + val2;
                        s1 = name1 + " - " + val1;
                        s2 = myName + " - " + score;
                    }
                    else if (score > val3) {
                        ref1.setValue(myName + " " + score);
                        s2 = name2 + " - " + val2;
                        s1 = name1 + " - " + val1;
                        s3 = myName + " - " + score;
                    }
                    else {
                        s2 = name2 + " - " + val2;
                        s1 = name1 + " - " + val1;
                        s3 = name3 + " - " + val3;
                    }
                    mHintButton.setEnabled(false);
                    mHintButton.setBackground(getResources().getDrawable(R.drawable.rounded_btn));
                    Intent i = new Intent(QuizActivity.this, ScoreActivity.class);
                    i.putExtra(getString(R.string.new_score_tag), score);
                    //if (val1 != null && !val1.equals("-1"))
                    //    i.putExtra(getString(R.string.high_score_tag), Integer.parseInt(val1));
                    //else
                    //i.putExtra(getString(R.string.high_score_tag), scoreData.getInt(getString(R.string.high_score_tag), 0));
                    i.putExtra("place1", s1);
                    i.putExtra("place2", s2);
                    i.putExtra("place3", s3);
                    startActivityForResult(i, 19);
                }
                else {
                    if (mCurrentIndex == mQuestionBank.length - 1) {
                        mNextButton.setText(R.string.finish_button);
                        mNextButton.setBackground(getResources().getDrawable(R.drawable.rounded_btn_glow));
                    }
                    mNextButton.setEnabled(false);
                    mTrueButton.setEnabled(true);
                    mFalseButton.setEnabled(true);
                    mHintButton.setEnabled(true);
                    mQuestionNumber.setText(getString(R.string.word_question) + " " + (mCurrentIndex + 1) + "/" + mQuestionBank.length);
                    int question = mQuestionBank[mCurrentIndex].getTextResId();
                    mQuestionTextView.setText(question);
                    mHintButton.setBackground(getResources().getDrawable(R.drawable.rounded_btn));
                }
            }
        });

        mHintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QuizActivity.this, HintActivity.class);
                int hint = mQuestionBank[mCurrentIndex].getHintResId();
                i.putExtra("qHint", hint);
                startActivityForResult(i, 8);
            }
        });



        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data = ""+dataSnapshot.getValue();
                name1 = data.split(" ")[0];
                val1 = Integer.parseInt(data.split(" ")[1]);
                //editor.putInt(getString(R.string.high_score_tag), Integer.parseInt(val1)).commit();
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });


        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data = ""+dataSnapshot.getValue();
                name2 = data.split(" ")[0];
                val2 = Integer.parseInt(data.split(" ")[1]);
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });


        ref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data = ""+dataSnapshot.getValue();
                name3 = data.split(" ")[0];
                val3 = Integer.parseInt(data.split(" ")[1]);
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });





    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 8 && resultCode == RESULT_OK && points == 10)
            points = 7;
        else if (requestCode == 19) {
            if (resultCode == RESULT_OK) {
                mCurrentIndex = 0;
                mNextButton.setEnabled(false);
                mTrueButton.setEnabled(false);
                mFalseButton.setEnabled(false);
                mHintButton.setEnabled(true);

                mNameText.setVisibility(View.VISIBLE);
                mNameSpace.setVisibility(View.VISIBLE);
                mSubmitButton.setVisibility(View.VISIBLE);

                mProgressBar.setProgress(0);

                mQuestionNumber.setText(getString(R.string.word_question) + " " + (mCurrentIndex + 1) + "/" + mQuestionBank.length);
                int question = mQuestionBank[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(question);
                mHintButton.setBackground(getResources().getDrawable(R.drawable.rounded_btn));
                score = 0;
                mScoreText.setText(getString(R.string.score_label) + " " + score);
                mNextButton.setBackground(getResources().getDrawable(R.drawable.rounded_btn));
                mNextButton.setText(getString(R.string.next_button));
            }
            else if (resultCode == 5)
                finish();
        }
    }


}
