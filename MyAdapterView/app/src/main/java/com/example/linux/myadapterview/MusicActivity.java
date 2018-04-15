package com.example.linux.myadapterview;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ListView;

import java.util.ArrayList;

public class MusicActivity extends AppCompatActivity {

    private ListView mListView;
    private ArrayList<Music> mMusics;
    private MusicAdapter mMusicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        // 在setContentView方法中, Android会在最外层嵌套一个FrameLayout, 所以
        // R.layout.activity_music的layout_height等才会有效果
        // 验证
        ConstraintLayout main = findViewById(R.id.main);
        ViewParent viewParent = main.getParent();
        Log.d("--D--", "main's parent is " + viewParent);
        // 输出:  main's parent is android.support.v7.widget.ContentFrameLayout{40294...........

        mListView = findViewById(R.id.lv3);
        mMusics = new ArrayList<>();
        Music music;
        for (int i = 0; i < 30; i++) {
            music = new Music(i,
                    getImgResId(i),
                    "music" + Integer.toString(i + 100),
                    false);
            mMusics.add(music);
        }

        mMusicAdapter = new MusicAdapter(MusicActivity.this, mMusics);
        mListView.setAdapter(mMusicAdapter);

    }

    private int getImgResId(int musicId) {
        if (musicId % 2 == 0) {
            return R.drawable.ic_cached_black_24dp;
        }
        if (musicId % 21 == 0) {
            return R.drawable.ic_bug_report_black_24dp;
        } else {
            return R.drawable.ic_visibility_off_black_24dp;
        }
    }
}
