package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/*
    与CrimeActivity代码唯一的区别就是，
    为了实例化新的fragment，
    我们新增了 名为createFragment()的抽象方法。
    令SingleFragmentActivity的子类去实现该方法，来返回 由activity托管的fragment实例
 */

public abstract class SingleFragmentActivity extends FragmentActivity {
    protected abstract Fragment createFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //protected
        super.onCreate(savedInstanceState);

        // activity_fragment.xml布局里实例化activity视图
        setContentView(R.layout.activity_fragment);


        FragmentManager fm = getSupportFragmentManager();

        // 在容器中查 找FragmentManager里的fragment。
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        // 如果找不到，就新建fragment并将其添加到容器中
        if (fragment == null) {
            // 子类实现
            fragment = createFragment();

            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

    }
}
