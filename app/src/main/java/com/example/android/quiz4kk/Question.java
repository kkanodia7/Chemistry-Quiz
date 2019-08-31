package com.example.android.quiz4kk;

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;
    private int mHintResId;

    public Question(int textResId, boolean answerTrue, int hintResId) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mHintResId = hintResId;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) { mAnswerTrue = answerTrue; }

    public int getHintResId() {
        return mHintResId;
    }

    public void setHintResId(int hintResId) {
        mHintResId = hintResId;
    }
}
