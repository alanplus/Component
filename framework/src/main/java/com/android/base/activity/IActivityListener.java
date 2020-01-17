package com.android.base.activity;

import android.app.Activity;

/**
 * @author Alan
 * 时 间：2019-10-30
 * 简 述：<功能简述>
 */
public interface IActivityListener {

    void onCreate(Activity activity);

    void onStart(Activity activity);

    void onResume(Activity activity);

    void onPause(Activity activity);

    void onStop(Activity activity);

    void onDestroy(Activity activity);
}
