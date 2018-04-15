package com.anakinliu.geoquiz3;

/**
 * Created by Anakinliu on 2018/3/15.
 * Day Day UP :)
 */

public class Question {

    // int是问题文本的资源ID
    private int mTextResId;
    // 判断题的答案是对是错
    private boolean mAnswer;

    public Question(int textResId, boolean answer) {
        mTextResId = textResId;
        mAnswer = answer;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswer() {
        return mAnswer;
    }

    public void setAnswer(boolean answer) {
        mAnswer = answer;
    }
}
