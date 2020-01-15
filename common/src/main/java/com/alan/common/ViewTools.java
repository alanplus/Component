package com.alan.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


/**
 * Created by Mouse on 2018/10/15.
 */
public class ViewTools {

    public static void setClickEffection(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setAlpha(0.6f);
                        break;
                    case MotionEvent.ACTION_OUTSIDE:
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_SCROLL:
                        v.setAlpha(1.0f);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    public static Bitmap getViewScreenBitmap(View v) {
        if (v == null) {
            return null;
        }
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        Bitmap bitmap = v.getDrawingCache();
        return bitmap;
    }


    /**
     * 获取View的高度，但是不一定成功
     *
     * @param view
     * @return
     */
    public static int getTargetHeight(View view) {
        int measuredHeight = view.getMeasuredHeight();
        if (measuredHeight > 0) {
            return measuredHeight;
        }
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        return view.getMeasuredHeight();
    }


    /**
     * 获取View的宽度，但是不一定成功
     *
     * @param view
     * @return
     */
    public static int getTargetWidth(View view) {
        int measuredWidth = view.getMeasuredWidth();
        if (measuredWidth > 0) {
            return measuredWidth;
        }
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        return view.getMeasuredWidth();
    }

    /**
     * 判断 是否是横屏
     *
     * @param context
     * @return
     */
    public static boolean isLandScape(Context context) {
        int[] screenSize = AndroidTools.getScreenSize(context);
        return screenSize[0] > screenSize[1];
    }

    public static <T extends View> T findView(Class<T> tClass, ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view.getClass() == tClass) {
                return (T) view;
            } else if (view instanceof ViewGroup) {
                return findView(tClass, (ViewGroup) view);
            }
        }
        return null;
    }
}
