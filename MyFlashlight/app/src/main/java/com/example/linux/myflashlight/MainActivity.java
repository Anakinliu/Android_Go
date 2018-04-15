package com.example.linux.myflashlight;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;

import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {

    private CameraManager mCameraManager;

    private ImageButton mSwitchButton;
    private String mCameraId;
    private boolean mIsOn;

    private MediaPlayer mMediaPlayer;


    //
    private Camera mCam;
    private Camera.Parameters p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwitchButton = findViewById(R.id.btnSwitch);

        mIsOn = false;
        /*
        检查设备是否支持闪光灯
         */
        boolean mHas = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!mHas) {
            // 不支持
            // 显示警告信息并关闭应用
            AlertDialog alertDialog = new AlertDialog.Builder(
                    MainActivity.this
            ).create();
            alertDialog.setTitle("抱歉!");
            alertDialog.setMessage("你的设备似乎没有闪光灯!");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"好的",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // 关闭
                    finish();
                }
            });
            alertDialog.show();

        }

        // 获得CameraManager对象
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            /*
            非可移动摄像机使用从0开始的整数作为其标识符，而可移动摄像机对每个单独的设备都有唯一的标识符，即使它们是相同的型号。
             */
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        mSwitchButton.setOnClickListener(new  View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    if (mIsOn) {
                        turnOffFlash();
                        mIsOn = false;
                    } else {
                        turnOnFlash();
                        mIsOn = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        /*
        对于5.1及以下使用旧的API
         */
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mCam = Camera.open();
            p = mCam.getParameters();
            p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            mCam.setParameters(p);
            mCam.startPreview();
        }
    }

    /*
    关灯
     */
    private void turnOffFlash() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mCameraManager.setTorchMode(mCameraId, false);
            } else {
                /*
                对于5.1及以下使用旧的API
                 */
                mCam.stopPreview();
                p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCam.setParameters(p);
                mCam.startPreview();
            }
            playSwitchSound();

            mSwitchButton.setImageResource(R.drawable.btn_switch_off);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void turnOnFlash() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mCameraManager.setTorchMode(mCameraId, true);

            } else {
                /*
                对于5.1及以下使用旧的API
                 */
                mCam.stopPreview();
                p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCam.setParameters(p);
                mCam.startPreview();
            }

            playSwitchSound();

            mSwitchButton.setImageResource(R.drawable.btn_switch_on);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playSwitchSound() {
        mMediaPlayer = MediaPlayer.create(MainActivity.this,
                R.raw.switch_sound);
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mMediaPlayer.release();
            }
        });
        mMediaPlayer.start();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mIsOn) {
            turnOffFlash();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mIsOn) {
            turnOnFlash();
        }
    }
}
