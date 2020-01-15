package com.alan.common;

/**
 * @author Alan
 * 时 间：2019-12-31
 * 简 述：<功能简述>
 */
public class CommonConfig {

    private static boolean isDebug;
    private static String tag = "alan_log";

    public static void init(ICommonConfig iCommonConfig) {
        if (null == iCommonConfig) {
            return;
        }
        CommonConfig.isDebug = iCommonConfig.isDebug();
        CommonConfig.tag = iCommonConfig.getTag();

    }

    public static String getTag() {
        return tag;
    }

    public static boolean isDebug() {
        return isDebug;
    }

    public interface ICommonConfig {
        boolean isDebug();
        String getTag();

    }
}
