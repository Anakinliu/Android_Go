package com.bignerdranch.android.criminalintent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

public class CrimeActivity extends SingleFragmentActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        //protected
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_fragment);
//
//        /*
//        要想以代码的方式将fragment添加到activity中，
//        可直接调用activity的FragmentManager。
//        首先， 我们需要获取FragmentManager本身。
//
//        注意,需要支持库版本的fragmentManager,非系统自带版本的!
//
//        因为我们使用了支持库及FragmentActivity类，
//        所以这里调用的方法是getSupport FragmentManager()
//       。如果不考虑以前版本的兼容性问题，可考虑继承Activity类以此调用 getFragmentManager()方法。
//         */
//        FragmentManager fm = getSupportFragmentManager();
//
//        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
//
//        /*
//        这段代码创建 并且 提交了一个fragment事务
//
//        fragment事务被用来添加、移除、附加、分离或替换fragment队列中的fragment
//
//        这是使用 fragment在运行时组装和重新组装用户界面的关键。FragmentManager管理着fragment事务回 退栈。
//
//         */
//        if (fragment == null) {
//            fragment = new CrimeFragment();
//
//            /*
//            FragmentManager.beginTransaction()方法  创建  并提交  FragmentTransaction  实例。
//
//
//            FragmentTransaction类使用了名为fluent interface的接口方法，
//            通过该方法配置Fragment- Transaction返回FragmentTransaction类对象，
//            而不是void，由此可得到一个Fragment- Transaction队列。
//
//            add方法是整个事务的核心,参数是容器视图资源ID和新创建的CrimeFragment
//
//            参数一是定义在Activity_crime.XML中的FrameLayout组件的资源ID
//            作用是:
//                1. 告诉FragmentManager,fragment视图应该出现在activity的什么位置;
//                2.用作FragmentManager队列中fragment的唯一标识符
//             因此,可以使用容器资源ID从FragmentManager中获取CrimeFragment
//
//             */
//            // Create a new fragment transaction, include one add operation in it, and then commit it.
//            fm.beginTransaction()
//                    .add(R.id.fragment_container, fragment)
//                    .commit();
//        }
//
//    }

    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }
}
