package com.example.linux.myadapterview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ArrayAdapterUsageActivity extends AppCompatActivity {

    private ListView mListView;
    private List<String> mData;
    private ArrayAdapter<String> mControl;
    private final String TAG = "--D--";
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_array_adapter);

        mListView = findViewById(R.id.lv);
        mButton = findViewById(R.id.next);

        mData = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            mData.add("item" + i + ". ");
        }

        // 将adapter控制器与item视图, 数据源绑定
        mControl = new ArrayAdapter<>(
                ArrayAdapterUsageActivity.this,
                R.layout.item_array_adapter,
                mData
                );
        // 设置ListView的adapter
        mListView.setAdapter(mControl);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            /***
             *
             * @param adapterView 发生点击动作的ListView对象
             * @param view ListView中被点击的View
             * @param i
             * @param l
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "i= " + i + " ; l= " + l);
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                mData.remove(i);
                mControl.notifyDataSetChanged();
                Log.d(TAG, mData.size() + "");
                return true;
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(ArrayAdapterUsageActivity.this,
                        SimpleAdapterActivity.class));
            }
        });
    }
}
