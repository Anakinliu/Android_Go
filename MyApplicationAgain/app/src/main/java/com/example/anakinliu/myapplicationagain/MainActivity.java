package com.example.anakinliu.myapplicationagain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    /**
     * 您的键是一个公共常量 EXTRA_MESSAGE，因为下一个 Activity 将使用该键来检索文本值。
     * 为 Intent extra 定义键时最好使用应用的软件包名称作为前缀。
     * 这可以确保在您的应用与其他应用交互过程中这些键始终保持唯一。
     * 随便写也不会报错
     */
    public final static String EXTRA_MESSAGE = "com.example.anakinliu.myapplicationagain.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**Called when the user clicks the Send button */
    public void sendMessage(View view) {
        //Do something in response to button
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        /**
         * Intent 构造函数采用两个参数：

         Context 是第一个参数（之所以使用 this ，是因为 Activity 类是 Context 的子类）
         应用组件的 Class，系统应将 Intent（在本例中，为应启动的 Activity）传递至该类。
         */



        //获得昵称，没有用户名
        EditText editText = (EditText) findViewById(R.id.edit_nickname);
        String message = "用户名：" + ((EditText) findViewById(R.id.edit_userName)).getText().toString() + "," + "昵称：" + editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE,message);
        /**
         * putExtra() 方法将 EditText 的值添加到 Intent。
         * Intent 能够以名为 extra 的键值对形式携带数据类型。
         * 您的键是一个公共常量 EXTRA_MESSAGE，
         * 因为下一个 Activity 将使用该键来检索文本值。
         * 为 Intent extra 定义键时最好使用应用的软件包名称作为前缀。
         * 这可以确保在您的应用与其他应用交互过程中这些键始终保持唯一
         */
        startActivity(intent);
        /**
         * startActivity() 方法将启动 Intent 指定的 DisplayMessageActivity
         * 实例。现在，您需要创建类。
         */
    }
}
