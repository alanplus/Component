package com.android.tools;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;

import com.android.base.activity.IActivityListener;
import com.android.base.state.IFailureView;
import com.android.base.state.ILoadingView;
import com.android.base.state.view.CommonFailureView;
import com.android.base.state.view.CommonLoadingView;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Mouse on 2018/10/18.
 */
public abstract class AndroidToolsConfig {

    public static AndroidToolsConfig androidToolsConfig;

    public Context context;

    public abstract Handler getHandler();

    public boolean isDebug() {
        return true;
    }


    public static void init(AndroidToolsConfig androidToolsConfig) {
        AndroidToolsConfig.androidToolsConfig = androidToolsConfig;
    }

    /**
     * 获取Activity管理器
     *
     * @return
     */
    public IActivityListener getIActivityListener() {
        return null;
    }

    /**
     * Loading状态的View
     * @param context
     * @return
     */
    public ILoadingView getILoadingView(Context context) {
        return new CommonLoadingView(context);
    }

    /**
     * 加载失败状态的View
     * @param context
     * @return
     */
    public IFailureView getIFailureView(Context context){
        return new CommonFailureView(context);
    }

}
