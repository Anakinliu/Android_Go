package com.example.linux.myadapterview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleAdapterActivity extends AppCompatActivity {

    private ListView mListView;
    private List<Map<String, Object>> mData;
    private Map<String, Object> mMap;
    private SimpleAdapter mSimpleAdapter;

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_adapter);

        mListView = findViewById(R.id.lv2);

        mData = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            mMap = new HashMap<>();
            if (i % 2 == 0) {
//                mMap.put("icon", R.drawable.ic_accessible_black_24px);
//                mMap.put("enSW", false);
                mMap.put("tv", "option: " + "奇数");
            }
            else {
//                mMap.put("icon", R.drawable.ic_accessibility_black_24dp);
//                mMap.put("enSW", true);
                mMap.put("tv",  "option: " + "偶数");
            }


            mData.add(mMap);
        }

//        String[] from = {"icon", "tv", "enSW"};
        String[] from = {"tv"};
        int[] to = {R.id.tv};
        mSimpleAdapter = new SimpleAdapter(SimpleAdapterActivity.this,
                mData, R.layout.item_simple_adapter, from, to);

        mListView.setAdapter(mSimpleAdapter);

        mButton = findViewById(R.id.next);
        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(SimpleAdapterActivity.this,
                        MusicActivity.class));
            }
        });
    }
}
