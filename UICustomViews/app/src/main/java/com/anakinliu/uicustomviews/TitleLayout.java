package com.anakinliu.uicustomviews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Anakinliu on 2018/3/15.
 * Day Day UP :)
 */

public class TitleLayout extends LinearLayout {
    public TitleLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // from静态方法构造一个LayoutInflater, 其inflater可以动态加载布局文件
        LayoutInflater.from(context).inflate(R.layout.title, this);
        Button back = findViewById(R.id.bt_back);
        Button edit = findViewById(R.id.bt_edit);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "BACK", Toast.LENGTH_SHORT).show();
            }
        });
        edit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "EDIT", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
