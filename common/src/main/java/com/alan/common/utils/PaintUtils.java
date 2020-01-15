package com.alan.common.utils;

import android.graphics.Paint;

/**
 * @author Alan
 * 时 间：2019-12-25
 * 简 述：<功能简述>
 */
public class PaintUtils {

    public static int getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int)Math.ceil((double)(fm.descent - fm.top)) + 2;
    }

    public static float getFontLeading(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.leading - fm.ascent;
    }

    public static int getStringWidth(String str, Paint paint) {
        return (int)paint.measureText(str);
    }

}
