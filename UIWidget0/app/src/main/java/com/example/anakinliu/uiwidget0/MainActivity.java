package com.example.anakinliu.uiwidget0;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends LifeCycle implements View.OnClickListener{

    private EditText mEditText1;

    private ImageView mImageView1;

    private ProgressBar mProgressBar1;

    private int progress = 0;

    class DialogOnClickListener implements DialogInterface.OnClickListener{


        public DialogOnClickListener() {
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case AlertDialog.BUTTON_POSITIVE:
                    progress += 10;
                    // 设置进度条
                    mProgressBar1.setProgress(progress);
                    break;
                case AlertDialog.BUTTON_NEGATIVE:
                    progress += -10;
                    // 设置进度条
                    mProgressBar1.setProgress(progress);
                    break;
                case AlertDialog.BUTTON_NEUTRAL:
                    progress += 0;
                    // 设置进度条
                    mProgressBar1.setProgress(progress);
                    break;
                default:
                    break;
            }
//            Log.d("---dialog which---", Integer.toString(which));
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button1:
                String text = mEditText1.getText().toString();
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
                mImageView1.setImageResource(R.drawable.images1);

                if (mProgressBar1.getVisibility() == View.VISIBLE) {
                    mProgressBar1.setVisibility(View.INVISIBLE);
                } else {
                    mProgressBar1.setVisibility(View.VISIBLE);
                }

                if (mEditText1.getVisibility() == View.VISIBLE) {
                    mEditText1.setVisibility(View.INVISIBLE);
                } else {
                    mEditText1.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.button2:
//                progress = mProgressBar1.getProgress();

                // Dialog会屏蔽屏幕上的其他控件
                AlertDialog.Builder dialog =
                        new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("这是对话");
                dialog.setMessage("是否保存?");
                // 是否可用返回键取消对话框
                dialog.setCancelable(false);
                // 确定按钮事件
                dialog.setPositiveButton("YES", new DialogOnClickListener());
                // 取消按钮事件
                dialog.setNegativeButton("NO", new DialogOnClickListener());
                // 中立按钮
                dialog.setNeutralButton("Cancel", new DialogOnClickListener());
                // 显示对话框
                dialog.show();

                break;
            case R.id.button3:
                // 此组件已在Android 8中弃用
                ProgressDialog progressDialog =
                        new ProgressDialog(MainActivity.this);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTAG("---Main Activity ---");

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(this);

        mEditText1 = findViewById(R.id.editor1);

        mImageView1 = findViewById(R.id.img1);

        mProgressBar1 = findViewById(R.id.progress1);

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(this);

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(this);

    }

}
