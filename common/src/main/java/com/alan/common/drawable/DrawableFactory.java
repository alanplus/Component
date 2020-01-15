package com.alan.common.drawable;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.annotation.ColorInt;

import com.alan.common.AndroidTools;

/**
 * @author Alan
 * 时 间：2019-11-20
 * 简 述：<功能简述>
 */
public class DrawableFactory {

    /**
     * 圆角Drawable
     * @param color
     * @param radius
     * @return
     */
    public static Drawable generateCornerDrawable(int color, int radius) {
        int i = AndroidTools.dip2px(radius);
        float[] out = new float[]{i, i, i, i, i, i, i, i};
        ShapeDrawable drawable = new ShapeDrawable(new RoundRectShape(out, null, null));
        drawable.getPaint().setColor(color);
        return drawable;
    }


    /**
     * 文字点击颜色
     * @param unColor
     * @param color
     * @return
     */
    public static ColorStateList buildClickTextColor(@ColorInt int unColor, @ColorInt int color) {
        int[] colors = new int[]{color, unColor};
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_pressed};
        states[1] = new int[]{};
        return new ColorStateList(states, colors);
    }

}
