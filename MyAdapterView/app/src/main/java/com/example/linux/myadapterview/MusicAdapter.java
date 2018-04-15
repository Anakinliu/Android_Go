package com.example.linux.myadapterview;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MusicAdapter extends BaseAdapter {

    static class ViewHolder {
        ImageView iv;
        TextView tv;
        Switch enSW;
        int position;
    }

    private ArrayList<Music> mMusicArrayList;
    private Context mContext;

    /**
     *
     * @param context 上下文对象
     * @param musicArrayList 数据列表
     */
    public MusicAdapter(Context context, ArrayList<Music> musicArrayList) {
        this.mMusicArrayList = musicArrayList;
        this.mContext = context;

    }

    /**
     *
     * @return 数据个数即ListView的条数, 也是getView的回调次数
     */
    @Override
    public int getCount() {
        return mMusicArrayList.size();
    }

    /**
     * ListView的getItemAtPosition会用到
     * @param position 条目的位置
     * @return 此位置的对象
     */
    @Override
    public Object getItem(int position) {
        return mMusicArrayList.get(position);
    }

    /**
     * ListView的getItemIdAtPosition会用
     * @param position 条目的位置
     * @return 条目的位置ID
     */
    @Override
    public long getItemId(int position) {
        return mMusicArrayList.get(position).getId();
    }

    /**
     *
     * @param position 索引位置0开始
     * @param convertView
     * @param parent item的容器视图
     * @return
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();

        if (convertView == null) {
            // 减少重复的inflater与findViewById操作
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
//            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_custom_adapter, parent, false);
            // attachToRoot是false是因为item布局的各个组件已经有了RelativeLayout作为root布局, 不需要root布局作为父布局
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (parent != null)
                    Log.d("--D--", parent.toString());
            }
            viewHolder.iv = convertView.findViewById(R.id.iv3);
            viewHolder.tv = convertView.findViewById(R.id.tv3);
            viewHolder.enSW = convertView.findViewById(R.id.sw3);
            convertView.setTag(viewHolder);
            Log.d("--D--", "inflater");
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 获取当前需要绘制的视图的数据源
        Music music = mMusicArrayList.get(position);
        viewHolder.tv.setText(music.getMusicName());

        viewHolder.enSW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "You Clicked switch" + position + " !",
                        Toast.LENGTH_SHORT).show();
                // 更新数据switch状态
                boolean isLike = mMusicArrayList.get(position).isLike();
                mMusicArrayList.get(position).setLike(!isLike);
            }
        });

        // 然后更新视图switch状态
        viewHolder.enSW.setChecked(music.isLike());

        /*
         更新数据的图标资源Id
         // 放到初始化MusicList的地方了
          */
//        music.setMusicImg(getImgResId(music.getId()));
        // 然后更新视图的显示
        viewHolder.iv.setImageResource(music.getMusicImg());

        return convertView;
    }

//    private int getImgResId(int musicId) {
//        if (musicId % 2 == 0) {
//            return R.drawable.ic_cached_black_24dp;
//        }
//        if (musicId % 21 == 0) {
//            return R.drawable.ic_bug_report_black_24dp;
//        } else {
//            return R.drawable.ic_visibility_off_black_24dp;
//        }
//    }
}
