package com.example.linux.mysimplelayout;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/*
郭霖的Blog之视图绘制流程解析
https://blog.csdn.net/guolin_blog/article/details/16330267
 */
public class SimpleLayout extends ViewGroup {

    public SimpleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /*
    onMeasure在onLayout之前调用
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 这个简单的layout只管第一个子视图
        if (getChildCount() > 0) {
            View childView = getChildAt(0);
            /*
            计算的依据就是布局文件中定义的 MATCH_PARENT、WRAP_CONTENT 等值
             */
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (getChildCount() > 0) {
            View chileView = getChildAt(0);
            /*
            调用 childView.getMeasuredWidth() 和 childView.getMeasuredHeight()
            方法得到的值就是在 onMeasure() 方法中测量出的宽和高.
            而getWidth()和getHeight()得到的是layout()方法(由onLayout()调用)后的, 使用视图左右坐标, 上下坐标相减得到的
             */
            chileView.layout(0, 0, getMeasuredWidth(), getMeasuredHeight());

        }
    }
}
