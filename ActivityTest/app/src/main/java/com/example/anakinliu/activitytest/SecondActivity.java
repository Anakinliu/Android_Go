package com.example.anakinliu.activitytest;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SecondActivity extends LogCycleActivity {

    final int PICK_CONTACT_REQUEST = 110;

    final String TAG = "Second------Activity ";
    private void backToMainActivity() {
        Intent intent = new Intent();  //没有指定意图, 只负责传递数据
        intent.putExtra("data_for_result", "OK, MainActivity, this is result");
        setResult(RESULT_OK, intent);  //一般只有RESULT_OK和RESULT_CANCELED
        finish();
    }


    @Override
    public void onBackPressed() {
        backToMainActivity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate 填充
        getMenuInflater().inflate(R.menu.second_ac_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add_item:
                break;
            case R.id.remove_item:
                break;
            case R.id.killer_item:  // 尝试finish()掉Main Ac
                // 由于Second Ac是由Main Ac的startActivityForResult启动
                // 所以可以使用setResult传数据回Main
                // 测试发现Main Ac的onActivityResult会在onRestart方法
                // 之前调用, 所以这个方法看似可行
                Intent intent = new Intent();  //没有指定意图, 只负责传递数据
                intent.putExtra("data_for_result", "finish");
                setResult(RESULT_OK, intent);  //一般只有RESULT_OK和RESULT_CANCELED
                onDestroy();
//                Toast.makeText(SecondActivity.this, "Clicked Killer",
//                        Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ActivityManager.addActivity(SecondActivity.this);

        setTAG("Second------Activity ");

        Log.d(TAG, "Task id is: " + getTaskId());
        Log.d(TAG, "onCreate()");
        Intent intent = getIntent();
        final String dataFromMainActi = intent.getStringExtra("extra_data");
        Toast.makeText(SecondActivity.this, dataFromMainActi, Toast.LENGTH_SHORT).show();
        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMainActivity();
            }
        });

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickContact = new Intent(Intent.ACTION_PICK);
                pickContact.setData(Uri.parse("content://contacts"));
                pickContact.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(pickContact, PICK_CONTACT_REQUEST);
            }
        });

        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICK_CONTACT_REQUEST:
                if (resultCode == RESULT_OK) {
//                    // Get the URI that points to the selected contact
//                    Uri contactUri = data.getData();
//                    // We only need the NUMBER column, because there will be only one row in the result
//                    String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};
//                    Log.d("OSO", "OK");
//                    // Perform the query on the contact to get the NUMBER column
//                    // We don't need a selection or sort order (there's only one result for the given URI)
//                    // CAUTION: The query() method should be called from a separate thread to avoid blocking
//                    // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
//                    // Consider using CursorLoader to perform the query.
//                    Cursor cursor = getContentResolver()
//                            .query(contactUri, projection, null, null, null);
//                    cursor.moveToFirst();
//
//                    // Retrieve the phone number from the NUMBER column
//                    int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
//                    String number = cursor.getString(column);

                    // Do something with the phone number...
                    Toast.makeText(SecondActivity.this,
                            "Got Number", Toast.LENGTH_SHORT).show();
                }
                break;
            default:

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.deleteActivity(SecondActivity.this);
    }
}
