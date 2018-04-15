package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

/**
 * Created by Anakinliu on 2017/3/1.
 */

public class CrimeFragment extends Fragment {


    private final String TAG = "CrimeFragment";

    private Crime mCrime;

    private EditText mTitleField;

    private Button mDataButton;

    private CheckBox mSolvedCheckBox;


    /*
    注意,是公共方法,因为有人--activity需要调用他们
    调用以执行片段的初始创建。
    这在onAttach（Activity）和onCreateView（LayoutInflater，ViewGroup，Bundle）之前调用。
    请注意，当片段的活动仍在创建过程中时，可以调用此方法。
    因此，您不能依赖于此时初始化的活动的内容视图层次结构。
    如果您想在创建活动后进行工作，请参阅onActivityCreated（Bundle）。
    任何恢复的子片段将在基础Fragment.onCreate方法返回之前创建。
     */
    /*
    Bundle：如果片段是从先前保存的状态重新创建的，从它得到恢复。
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrime = new Crime();
    }

    /*
    调用以使该片段实例化其用户界面视图。这是可选的，非图形片段可以返回null（这是默认实现）。
    这将在onCreate（Bundle）和onActivityCreated（Bundle）之间调用。
    如果从这里返回一个视图，当视图被释放时，将在onDestroyView（）中调用。
     */
    /*
    LayoutInflater：LayoutInflater对象，可用于展开片段中的任何视图，

    ViewGroup：如果非空，这是片段的UI应该附加到的父视图。该片段不应该添加视图本身，
    但是这可以用于生成视图的LayoutParams。

    Bundle：如果非空，以此Bundle从给出的先前保存的状态当中重建视图。

    View 返回该片段的UI的视图，或null。
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        /*
        以前直接在ACtivity中可以直接使用findViewById是因为Activity中有相应的方法:
         public View findViewById(int id) {
                return getWindow().findViewById(id);
        }


        而Fragment类没有!需要自己调用
         */
        mTitleField = (EditText)v.findViewById(R.id.crime_title);
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mCrime.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // intentionally left blank
            }
        });

        mDataButton = (Button)v.findViewById(R.id.crime_date);
        mDataButton.setText(DateFormat.format("yyyy'年' M'月' d'日' E, HH:mm:ss" ,mCrime.getDate()));    //显示时间

        mDataButton.setEnabled(false);    // 暂时设置按钮不可用

        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d(TAG, b + " ");

                // set checked property
                mCrime.setSolved(b);
            }
        });

        return v;
    }
}
