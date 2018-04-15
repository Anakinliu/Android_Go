package com.bignerdranch.android.geoquiz;

/**
 * Created by Anakinliu on 2017/2/6.
 */

public class Question {

    //问题文本，why int？ 用来保存地理问题字符串的资源ID就是int类型。
    private int mTextResId;

    //问题答案
    private boolean mAnswerTrue;

    public Question (int textResId, boolean AnswerTrue) {

        mTextResId = textResId;
        mAnswerTrue = AnswerTrue;

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

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
