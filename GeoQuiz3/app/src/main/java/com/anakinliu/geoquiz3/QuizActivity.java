package com.anakinliu.geoquiz3;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class QuizActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER = "com.anakinliu.geoquiz3.extra_answer";
    private static final int CHEAT_REQ_CODE = 0;

    private Button mTrueButton;
    private Button mFalseButton;

    private View mToastLayout;
    private TextView mToastTV;
    private ImageView mToastIV;
    private Toast mCustomToast;

    private ImageButton mNextButton;
    private ImageButton mPreButton;
    private TextView mQuizText;
    private Button mCheatButton;

    private double mScorePercentage = 100;
    private int mCurrentIndex;
    private Question[] mQuestions = new Question[]{
            new Question(R.string.quiz1, true),
            new Question(R.string.quiz2, false),
            new Question(R.string.quiz3, true),
            new Question(R.string.quiz4, false),
    };

    private final String KEY_Answered = "answered";
    private boolean[] mAnswered = new boolean[]{
            false, false, false, false
    };

    private final String KEY_UserAnswer = "user_answer";
    private boolean[] mUserAnswer= new boolean[]{
            false, false, false, false
    };


    private final String TAG = "---QuizActivity---";
    private final String KEY_INDEX = "index";

    private final String KEY_IsCheater = "cheater";
    private boolean[] mIsCheater = new boolean[]{
            false, false, false, false
    };

    /* onStop()之前由系统调用
        建议只保存基本类型
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState()");
        /*
        调用该方法时，用户数
        据随即被保存在Bundle对象中，然后操作系统将Bundle对象放入activity记录中。
         */
        outState.putInt(KEY_INDEX, mCurrentIndex);
        outState.putBooleanArray(KEY_IsCheater, mIsCheater);
        outState.putBooleanArray(KEY_Answered, mAnswered);
        outState.putBooleanArray(KEY_UserAnswer, mUserAnswer);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        覆盖onCreate(Bundle)方法时，我们实际是在调用activity超类的onCreate(Bundle)方法，
并传入收到的bundle。在超类代码实现里，通过取出保存的视图状态数据，activity的视图层级结
构得以重建。
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Log.d(TAG, Integer.toString(mCurrentIndex));

        mQuizText = findViewById(R.id.quiz);
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheater = savedInstanceState.getBooleanArray(KEY_IsCheater);
            mAnswered = savedInstanceState.getBooleanArray(KEY_Answered);
            mUserAnswer = savedInstanceState.getBooleanArray(KEY_UserAnswer);
        }
        mQuizText.setText(mQuestions[mCurrentIndex].getTextResId());

        // 加载自定义Toast布局
        LayoutInflater inflater = getLayoutInflater();
        mToastLayout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_container));

        // 设置自定义toast的文字
        mToastTV = (TextView) mToastLayout.findViewById(R.id.text);

        // 设置自定义toast的图片
        mToastIV = mToastLayout.findViewById(R.id.toast_image);

        // 来自google开发文档
        mCustomToast = new Toast(getApplicationContext());

        mCustomToast.setGravity(Gravity.TOP, 0, 100);
        mCustomToast.setDuration(Toast.LENGTH_SHORT);
        mCustomToast.setView(mToastLayout);

        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mNextButton = findViewById(R.id.next_button);
        mPreButton = findViewById(R.id.previous_button);
        mCheatButton = findViewById(R.id.cheat_button);

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFalseButton.setEnabled(false);

                checkAnswer(true);
                setAnswered();

                showScore();
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTrueButton.setEnabled(false);
                checkAnswer(false);
                setAnswered();

                showScore();
            }
        });
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
                mQuizText.setText(mQuestions[mCurrentIndex].getTextResId());
                setAnswerButton();
//                mIsCheater[mCurrentIndex] = false; // 更新作弊状态
            }
        });
        mPreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex > 0) {
                    mCurrentIndex = mCurrentIndex - 1;
                } else {
                    mCurrentIndex = mQuestions.length - 1;
                }
                mQuizText.setText(mQuestions[mCurrentIndex].getTextResId());
                setAnswerButton();
//                mIsCheater = false; // 更新作弊状态

            }
        });

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cheat = new Intent(QuizActivity.this, CheatActivity.class);
                // 将数据放入Intent中
                cheat.putExtra(EXTRA_ANSWER, mQuestions[mCurrentIndex].isAnswer());
                // startActivityXXX()并非简单的静态方法, 而是将调用activity
                // 请求实际发给了操作系统, 即ActivityManager
                startActivityForResult(cheat, CHEAT_REQ_CODE);
            }
        });
    }

    /*
    子activity是以调用startActivityForResult(...)
方法启动的，结果代码则总是会返回给父activity。在没有调用setResult(...)方法的情况下，
如果用户按了后退按钮，
父activity则会收到
Activity.RESULT_CANCELED
的结果代码。
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == CHEAT_REQ_CODE) {
            // 后面用data需要判断null
            if (data == null) {
                return;
            }
            mIsCheater[mCurrentIndex] = CheatActivity.isCheater(data);

//            Toast.makeText(QuizActivity.this, "resultCode:"+Integer.toString(resultCode), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean allAnswered() {
        for (boolean e : mAnswered) {
            if (!e) {
                return false;
            }
        }
        return true;
    }

    private void showScore() {
        if (allAnswered()) {
            Toast.makeText(this,
                    "正确率: " +Double.toString(mScorePercentage) + "%",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void setAnswered() {
//        Log.d(TAG, "66666666", new Exception());
        if (!mAnswered[mCurrentIndex]) {
            mAnswered[mCurrentIndex] = true;
        }
    }

    /*
    设置按钮状态
     */
    private void setAnswerButton() {
        Log.d(TAG, "answered: " + Arrays.toString(mAnswered));
        Log.d(TAG, "user answer: " + Arrays.toString(mUserAnswer));
        if (mAnswered[mCurrentIndex]) {
            // 用户已回答"对"
            if (mUserAnswer[mCurrentIndex]) {
                mTrueButton.setEnabled(true);
                mFalseButton.setEnabled(false);
            } else {
                // 用户已回答"错"
                mFalseButton.setEnabled(true);
                mTrueButton.setEnabled(false);
            }
        } else {
            // 用户未回答此题
            mFalseButton.setEnabled(true);
            mTrueButton.setEnabled(true);
        }
    }

    // 回答正确Toast
    private void correctToast() {
        // 设置layout的背景色
        mToastLayout.setBackgroundColor(ContextCompat
                .getColor(QuizActivity.this,
                        R.color.colorPrimary));

        // 设置自定义Toast布局的 TextView
        mToastTV.setText(R.string.correct_toast);

        // 设置自定义Toast布局的 ImageView
        mToastIV.setImageResource(R.drawable.correct);

        mCustomToast.show();
    }

    // 回答错误Toast
    private void incorrectToast() {
        // 设置layout的背景色
        mToastLayout.setBackgroundColor(ContextCompat
                .getColor(QuizActivity.this,
                        R.color.colorAccent));

        // 设置自定义Toast布局的TextView
        mToastTV.setText(R.string.incorrect_toast);

        // 设置自定义Toast布局的ImageView
        mToastIV.setImageResource(R.drawable.incorrect);

        mCustomToast.show();
    }

    // 判断回答正误, 显示相应的Toast
    private void checkAnswer(boolean userAnswer) {
        mUserAnswer[mCurrentIndex] = userAnswer;
        double quizValue = ((1.0 / (double)mQuestions.length) * 100.0);
        if(mIsCheater[mCurrentIndex]) {
            Toast.makeText(QuizActivity.this, "你作弊了", Toast.LENGTH_SHORT).show();
        } else {
            if (mQuestions[mCurrentIndex].isAnswer() == userAnswer) {
                correctToast();
            } else {
                incorrectToast();
                if (!mAnswered[mCurrentIndex]) {
                    mScorePercentage -= quizValue;
                }
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 屏幕旋转时会调用, mCurrentIndex会从内存中抹掉
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }
}
