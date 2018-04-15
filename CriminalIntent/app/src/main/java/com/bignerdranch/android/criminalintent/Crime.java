package com.bignerdranch.android.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Anakinliu on 2017/3/1.
 */

public class Crime {

    private UUID mId;
    private String mTitle;

    private Date mDate;    //表示crime发生的时间
    private boolean mSolved;    //表示crime是否得到处理

    public Crime() {
        // Generate unique identifier
        mId = UUID.randomUUID();
        mDate = new Date();    //mDate初始化为当前日期,精确到毫秒


    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public UUID getId() {
        return mId;
    }


    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
