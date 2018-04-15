package com.example.anakinliu.justjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "Main Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // 引入布局
        Log.d(TAG, "onCreate debug");
        Log.e(TAG, "onCreate: error!");
        Log.d(TAG, "onCreate() returned: " + "result is well");
        Log.i(TAG, "onCreate: information");
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
    }
}
