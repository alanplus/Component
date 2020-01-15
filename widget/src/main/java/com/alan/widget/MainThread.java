package com.alan.widget;

import com.alan.httplib.XmHttpConfig;

/**
 * @author Alan
 * 时 间：2019-12-25
 * 简 述：<功能简述>
 */
public class MainThread {

    public static void run(Runnable runnable) {
        XmHttpConfig.getInstance().getHandler().post(runnable);
    }
}
