package com.bignerdranch.android.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/*AppCompatActivity是Activity的子类，主要是为Android旧版本系统提供兼容性支持
*
 */
public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;

    //add
    private Button mNextButton;
    private TextView mQuestionTextView;

    //在本书后续应用开发中， 会介绍更好的模型数据存储管理方式。
    // 现在，简单起见，我们选择在控制层代码中创建数组
    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private int mCurrentIndex = 0;


    /*用此方法查找由onCreate（Bundle）中处理的XML的id属性标识的视图。*/
    //!    public View findViewById(int in)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set the activity content from a layout resource.
        setContentView(R.layout.activity_quiz);
        //R.layout.activity_quiz是activity_quiz.xml定义的布局的资源ID

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

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
            }
        });

        //显示第一个问题时调用
        updateQuestion();
    }

    /*重复的代码放到方法中来*/
    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }


    /*检查答案*/
    private void checkQuestion(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;


        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }

        //显示吐司！！！
        Toast.makeText(this,messageResId, Toast.LENGTH_SHORT).show();
    }
}
