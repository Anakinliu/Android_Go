package com.bignerdranch.android.geoquiz;

//content,内容
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;    //android.util.Log类能够发送日志信息到系统级别的共享日志中心


/*AppCompatActivity是Activity的子类，主要是为Android旧版本系统提供兼容性支持
*
 */
public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;

    //add
    private Button mNextButton;
    private TextView mQuestionTextView;

    //add
    private Button mPreButton;

    //add
    private ImageButton mFirstImageButton;
    private ImageButton mLastImageButton;


    //log
    private static final String TAG = "QuizActivity";

    //存储index
    private static final String QUESTION_INDEX = "index";

    //存储是否作弊
    private static final String IS_CHEAT = "isCheat";

    //
    private static final String ANSWER_IS_KNOWN = "ANSWER_IS_KNOWN";

    //请求代码。请求代码是先发送给子 activity，
    // 然后再返回给父activity的用户定义整数值。
    //当一个activity启动多个不同类型的子activity，
    // 且需要判断区分消息回馈方时，通常会用到该请求代码
    //虽然QuizActivity只启动一种类型的 子activity，
    // 但为应对未来的需求变化，现在就应设置请求代码常量
    private static final int REQUEST_CODE_CHEAT = 0;


    //保存CheatActivity传回的变量
    //! 注意，有了cheat数组后已弃用！
    //private boolean mConfirmCheat;

    //在本书后续应用开发中， 会介绍更好的模型数据存储管理方式。
    // 现在，简单起见，我们选择在控制层代码中创建数组
    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private boolean[] mAnswerIsKnown = new boolean[] {
            false, false, false, false,false
    };

    private int mCurrentIndex = 0;


    //启动第二个activity的按钮
    private Button mCheatButton;


    /*用此方法查找由onCreate（Bundle）中处理的XML的id属性标识的视图。*/
    //!    public View findViewById(int in)

    /*
    在设备配置变化时，保存一些基本类型的值
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState() called");
        //参数是key,value关系
        outState.putInt(QUESTION_INDEX, mCurrentIndex);
        //outState.putBoolean(IS_CHEAT, mConfirmCheat);
        outState.putBooleanArray(ANSWER_IS_KNOWN, mAnswerIsKnown);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //在onCreate(...)方法里，必须首先调用超类的实现方法，然后再调用 其他方法，这一点很关键。
        super.onCreate(savedInstanceState);


        //添加日志输出代码
        Log.d(TAG, "onCreate(Bundle) called");

        //Set the activity content from a layout resource.
        setContentView(R.layout.activity_quiz);
        //R.layout.activity_quiz是activity_quiz.xml定义的布局的资源ID

        //注意，需要判断savedInstanceState是否为空，
        //测试发现，刚打开应用时savedInstanceState是空
        if (savedInstanceState != null) {
            //得到存储的index,如果第一个参数不存在，那么返回第二个参数的值。
            mCurrentIndex = savedInstanceState.getInt(QUESTION_INDEX, 0);
            //mConfirmCheat = savedInstanceState.getBoolean(IS_CHEAT, false);
            mAnswerIsKnown = savedInstanceState.getBooleanArray(ANSWER_IS_KNOWN);
        }

        //观察异常
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        //错误，设备反转后，更新activity，会再次执行onCreate方法，传入的Bundle中有上一个
        //activity的mCurrentIndex对应得值，不能简单地用下标0！！！
        // ！ mQuestionTextView.setText(mQuestionBank[0].getTextResId());

        //显示第一个问题时调用
        //以及设备配置更改时调用！！！
        updateQuestion();

        //点击问题会进入下一题！！！
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        //返回的是View对象，需要转换成View的子类--Button
        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkQuestion(true);
            }
        });

        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkQuestion(false);
            }
        });

        //NEXT按钮
        //递增数组索引并相应更新显示TextView的文本内容。
        mNextButton = (Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;


                //从第二个问题开始更新
                updateQuestion();
                //mConfirmCheat = false;
            }
        });

        //Pre按钮
        mPreButton = (Button)findViewById(R.id.pre_button);
        mPreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex == 0) {
                    mCurrentIndex = mQuestionBank.length - 1;
                } else {
                    mCurrentIndex = mCurrentIndex - 1;
                }
                updateQuestion();
                //mConfirmCheat = false;
            }
        });

        //ImageButton按钮
        mFirstImageButton = (ImageButton) findViewById(R.id.first_imagebutton);
        mFirstImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = 0;
                updateQuestion();
                //mConfirmCheat = false;
            }
        });

        mLastImageButton = (ImageButton) findViewById(R.id.last_imagebutton);
        mLastImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = mQuestionBank.length - 1 ;
                updateQuestion();
                //mConfirmCheat = false;
            }
        });


        //cheat button
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到问题的正确答案
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

                // startActivity(i);
                //“接受”已经在子Activity中定义好的键值对
                //启动第二个avtivity！！！！！！！！
                Intent i = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(i, REQUEST_CODE_CHEAT);
            }
        });

    }

    /*
    重复的代码放到方法中来
    更新问题组件
    更新作弊状态
    */
    private void updateQuestion() {
        //log a message at "debug" log level
        //Log.d(TAG, "Updating question text for question #" + mCurrentIndex, new Exception());  //throw exception in hand

        //更新问题时也会调用此方法，故不能这么做，放到具体的按钮监听器里去
        //mConfirmCheat = false;

        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);

    }
    
    


    /*
    检查答案对错，作弊与否,依此显示不同的吐司
    */
    private void checkQuestion(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        //Log.d(TAG,"User is Cheat(checkQuestion) ?  " + mConfirmCheat);
        //局部变量的初始化
        int messageResId = 0;

        if (mAnswerIsKnown[mCurrentIndex]) {
            messageResId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        //显示吐司！！！
        Toast.makeText(this,messageResId, Toast.LENGTH_SHORT).show();
    }

    /*
    处理CheatActivity的返回结果
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, R.string.not_bad, Toast.LENGTH_SHORT).show();
            //没有点击确认作弊的按钮
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            //因为大的应用启动的子activity有很多
            //所以需要判断data是否是null
            if (data == null) {
                return;
            }
            //此问题答案已经通过作弊知道了，
            //无法通过一直点击NEXT调到此题来消除作弊记录。
            //而且需要onSaveInstanceState暂存！！！！！！！！
            //wasAnswerShown解析出CheatActivity的EXTRA_ANSWER_SHOWN
            mAnswerIsKnown[mCurrentIndex] = CheatActivity.wasAnswerShown(data);
        }


    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}
