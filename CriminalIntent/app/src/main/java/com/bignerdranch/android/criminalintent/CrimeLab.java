package com.bignerdranch.android.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Anakinliu on 2017/4/3.
 */

public class CrimeLab {
    // 静态变量以s打头
    private static CrimeLab sCrimeLab;

    // 用来保存Crime对象
    private List<Crime> mCrimes;

    // TODO: 传入的是Context对象
    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab (Context context) {
        mCrimes = new ArrayList<>();

        // 批量存入100个Crime对象
        // 一个满是数据的模型层
        for (int i=0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0);
            mCrimes.add(crime);
        }
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    // 返回带有指定ID的Crime对象
    public Crime getCrime(UUID id) {
        for (Crime crime : mCrimes) {
            if (crime.getId().equals(id)) {
                return crime;
            }
        }
        return null;
    }
}



