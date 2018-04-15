package com.example.anakinliu.activitytest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ThirdActivity extends LogCycleActivity {

    final String TAG = "Third----Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityManager.addActivity(this);

        setContentView(R.layout.activity_third);
        Log.d(TAG, "onCreate()");
        Log.d(TAG, "Task id is: " + getTaskId());
        Button button = findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThirdActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.third_ac_menu, menu);
        return true;   //true表示允许显示menu
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                Toast.makeText(ThirdActivity.this,
                        "You clicked add item!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(ThirdActivity.this,
                        "You clicked remove item!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.exit_item:
                Toast.makeText(ThirdActivity.this,
                        "Finished, back.", Toast.LENGTH_LONG).show();
                ActivityManager.deleteAllActivity();
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.deleteActivity(ThirdActivity.this);
    }
}
