package com.bignerdranch.android.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE =
            "com.bignerdranch.android.geoquiz.answer_is_true";

    private static final String ANSWER_IS_TRUE = "ANSWER_IS_TRUE";
    private boolean mAnswerIsTrue;

    private TextView mAnswerTextView;
    private Button mShowAnswer;

    private boolean mAnswerIsShown;
    private static final String ANSWER_SHOWN = "ANSWER_SHOWN";

    private static final String EXTRA_ANSWER_SHOWN =
                "com.bignerdranch.android.geoquiz.answer_shown";
    /*
    Context packageContext是QuizActivity
     */
    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent i = new Intent(packageContext, CheatActivity.class);
        //将extra数据信息添加给intent
        //然后将键值关系附加到此Intent中，“传递给”QuizActivity这个父Activity
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }

    /*
    作弊后，防止因旋转屏幕，而丢失记录是否作弊的变量值
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(ANSWER_SHOWN, mAnswerIsShown);
        outState.putBoolean(ANSWER_IS_TRUE, mAnswerIsTrue);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsShown = false;

        if (savedInstanceState != null) {
            mAnswerIsShown = savedInstanceState.getBoolean(ANSWER_SHOWN, false);
            setAnswerShownResult(true);
        }

        //从传递进来的Intent中，获取答案
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_View);

        //屏幕旋转后仍然显示答案
        showAnswerAfterOnClick();

        mShowAnswer = (Button) findViewById(R.id.show_Answer_Button);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (mAnswerIsTrue) {
//                    mAnswerTextView.setText(R.string.true_button);
//                } else {
//                    mAnswerTextView.setText(R.string.false_button);
//                }
                mAnswerIsShown = true;
                showAnswerAfterOnClick();
                setAnswerShownResult(true);

                cheatButtonAnim();
            }
        });

    }

    //是否显示答案，mAnswerTextView显示什么
    private void showAnswerAfterOnClick() {
        if(mAnswerIsShown) {
            if (mAnswerIsTrue) {
                mAnswerTextView.setText(R.string.true_button);
            } else {
                mAnswerTextView.setText(R.string.false_button);
            }
        }
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        isAnswerShown = mAnswerIsShown;
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);

       /* 在父activity需要依据子activity的完成结果采取不同操作时，
         设置结果代码很有帮助。
         例如，假设子activity有一个OK按钮和一个Cancel按钮，
         并且为每个按钮的单击动作分别设 置了不同的结果代码。
         根据不同的结果代码，父activity会采取不同的操作。
        也可以不调用，此时单机后退按钮，父activity会收到
        Activity.RESULT_CANCELED
        */
        //给父类的onActivityResult()
        setResult(RESULT_OK, data);
    }

    /*
    *协助解析出 QuizActivity能用的信息
      */
    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    private void cheatButtonAnim() {
        //保持兼容性比较好的做法是
        // 将高API级别代码置于检查Android设备版本的条件语句中
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            int cx = mShowAnswer.getWidth() / 2;
            int cy = mShowAnswer.getHeight() / 2;
            float radius = mShowAnswer.getWidth();

                    /*动画 since API 21,测试如何在较低系统中加入高API代码
                     */

            //参数: 首先，指定要显 示或隐藏的View，然后是动画的中心位置、起始半径和结束半径
            Animator anim = ViewAnimationUtils
                    .createCircularReveal(mShowAnswer, cx, cy, radius, 0);


            anim.addListener(new AnimatorListenerAdapter() {

                //动画结束时的动作
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mShowAnswer.setVisibility(View.INVISIBLE);
                }
            });

            anim.start();
        } else {
            // 如果API级别比较低, 不用动画
            mShowAnswer.setVisibility(View.INVISIBLE);
        }
    }
}
