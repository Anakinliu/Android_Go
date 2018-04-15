package com.example.anakinliu.activitytest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends LogCycleActivity {


    final int mRequestCode = 117;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);

        ActivityManager.addActivity(MainActivity.this);

        setTAG("Main------------Activity ");

        Log.d(TAG, "!onCreate()!");
        Log.d(TAG, "Task id is: " +  getTaskId());
        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,
                        "You Clicked the button1 !", Toast.LENGTH_SHORT).show();
//                显式Intent
//                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                intent.putExtra("extra_data", data);  // 在Intent中向下一个Acti加入数据
//                startActivity(intent);

                // 需要匹配action和category来响应Intent
//                String data = "Hello, second activity, i'm mainActivity!";
//                Intent intent = new Intent("com.example.anakinliu.activitytest.ACTION_START");
//                intent.addCategory("com.example.anakinliu.activitytest.ACTION_SPEC");  //需要在清单文件里声明, 否则程序崩溃
//                startActivity(intent);  //startActivity方法使用android.intent.category.DEFAULT这个category

                //启动Activity并要求返回值
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("extra_data","Hi, Second Activity, give me some result!");
                startActivityForResult(intent, mRequestCode);  //请求代码随意, 不要重复即可
                /*
                当您调用 startActivityForResult() 时，您可以使用明确或隐含 Intent。当启动您自己的 Activity
                以接收结果时，您应使用明确 Intent 确保您可收到预期结果
                 */
            }
        });

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // public static final String ACTION_VIEW = "android.intent.action.VIEW";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.google.com"));  //指定intent操作的数据
                startActivity(intent);
            }
        });

        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:10086"));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;   //true表示允许显示menu
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                Toast.makeText(MainActivity.this,
                        "You clicked add item!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(MainActivity.this,
                        "You clicked remove item!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.finish_item:
                Toast.makeText(MainActivity.this,
                        "Finished, back.", Toast.LENGTH_LONG).show();
                finish();
                break;
            default:
        }
        return true;
    }

    /***
     *
     * @param requestCode 向 startActivityForResult() 传递的请求代码
     * @param resultCode  第二个 Activity 指定的结果代码。如果操作成功，这是 RESULT_OK；如果用户退出或操作出于某种原因失败，则是 RESULT_CANCELED。
     * @param data  传送结果数据的 Intent。
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.d("OSO", "ssr"); // OK
        switch (requestCode) {
            case mRequestCode:
//                Log.d("OSO", "ssr");  //OK 110
                if (resultCode == RESULT_OK) {
                    finish();
//                    onDestroy();
//                    String dateFromSecondActi = data.getStringExtra("data_for_result");
//                    Toast.makeText(MainActivity.this,
//                            dateFromSecondActi, Toast.LENGTH_LONG).show();
//                    Log.d("--Sec: normal!--", dateFromSecondActi);  //OK
//                    if (dateFromSecondActi.equals("finish")) {
//                        Log.d("--Sec: destroy!--", dateFromSecondActi);  //OK
//                        onDestroy();
//                    }
                }
                break;
            default:

        }
    }

    private final static String TAG = "Main----Activity";

//    @Override
//    protected void onStart() {
//        super.onStart();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//    }
//
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.deleteActivity(MainActivity.this);
    }

}
