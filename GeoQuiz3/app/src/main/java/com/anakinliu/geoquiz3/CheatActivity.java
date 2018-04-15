package com.anakinliu.geoquiz3;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import static android.os.Build.VERSION;
import static android.os.Build.VERSION_CODES;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Anakinliu on 2018/3/18.
 * Day Day UP :)
 */

/*
注意 需要在AndroidManifest.xml中注册!
 */
public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER = "com.anakinliu.geoquiz3.extra_answer";
    private static final String EXTRA_ANSWER_SHOWN = "com.anakinliu.geoquiz3.extra_answer_shown";

    private TextView mAnswerTV;
    private Button mBackButton;
    private boolean mAnswer;
    private final String KEY_CHEAT = "cheat";
    public boolean mIsCheater;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);


        mAnswerTV = findViewById(R.id.answer_tv);
        mBackButton = findViewById(R.id.cheat_button);

        // Activity.getIntent() 方 法 返 回 了
        // 由 QuizActivity.startActivity(Intent) 方 法 转 发 的
        // Intent对象。
        Intent intent =  getIntent();
        mAnswer = intent.getBooleanExtra(EXTRA_ANSWER, false);

        setAnswerTV();



        mAnswerTV.setVisibility(View.INVISIBLE);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAnswerShown(true);
                anim();
            }

        });

        if (savedInstanceState != null) {
            isAnswerShown(savedInstanceState.getBoolean(KEY_CHEAT));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_CHEAT, mIsCheater);

    }

    public static boolean isCheater(Intent data) {
        return data.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    private void isAnswerShown(boolean b) {
        mAnswerTV.setVisibility(View.VISIBLE);

        mIsCheater = true;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ANSWER_SHOWN, b);
        // int, intent
        setResult(Activity.RESULT_OK, intent);

    }

    private void anim() {
        int width = mBackButton.getWidth() / 2;
        int height = mBackButton.getHeight() / 2;
        int centerX = width;
        int centerY = height;
        float radius = Math.max(width, height);
        // ViewAnimationUtils在API21才加入, 这里直接写会提示编译错误
        if(VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Animator anim = ViewAnimationUtils
                    .createCircularReveal(mBackButton,
                            centerX, centerY, radius, 0);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mBackButton.setVisibility(View.INVISIBLE);
                }
            });
            anim.start();
        } else {
            mBackButton.setVisibility(View.INVISIBLE);
        }
    }

    private void setAnswerTV() {
        if (mAnswer) {
            mAnswerTV.setText(R.string.true_chinese);
        } else {
            mAnswerTV.setText(R.string.false_chinese);
        }
        mAnswerTV.setVisibility(View.INVISIBLE);
    }
}
