package com.example.anakinliu.activitytest;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anakinliu on 2018/2/23.
 * Day Day UP :)
 */

public class ActivityManager {
    private static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void deleteActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void deleteAllActivity() {
        for (Activity ac : activities) {
            if (!ac.isFinishing()) {
                Log.d("AcManag", ac.getClass().getCanonicalName() + " not finish , NOW finish");
                ac.finish();
            } else {
                Log.d("AcManag", ac.getClass().getCanonicalName());
            }
        }
        activities.clear();
    }
}
