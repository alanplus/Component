package com.android.base.activity;

import android.app.Activity;

import com.alan.common.Logger;
import com.android.LibConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alan
 * 时 间：2019-11-20
 * 简 述：<功能简述>
 */
public class ActivityManager {

    private static List<Activity> activityList = new ArrayList<>();


    public static void onCreate(Activity activity) {
        activityList.add(activity);
        if (LibConfig.getIActivityListener() != null) {
            LibConfig.getIActivityListener().onCreate(activity);
        }
    }

    public static void onStart(Activity activity) {
        if (LibConfig.getIActivityListener() != null) {
            LibConfig.getIActivityListener().onStart(activity);
        }
    }

    public static void onResume(Activity activity) {
        if (LibConfig.getIActivityListener() != null) {
            LibConfig.getIActivityListener().onResume(activity);
        }
    }

    public static void onPause(Activity activity) {
        if (LibConfig.getIActivityListener() != null) {
            LibConfig.getIActivityListener().onPause(activity);
        }
    }

    public static void onStop(Activity activity) {
        if (LibConfig.getIActivityListener() != null) {
            LibConfig.getIActivityListener().onStop(activity);
        }
    }

    public static void onDestroy(Activity activity) {
        activityList.remove(activity);
        if (LibConfig.getIActivityListener() != null) {
            LibConfig.getIActivityListener().onDestroy(activity);
        }
    }

    public static void finishAll() {
        for (Activity activity : activityList) {
            try {
                activity.finish();
            } catch (Exception e) {
                Logger.error(e);
            }
        }
    }

}
