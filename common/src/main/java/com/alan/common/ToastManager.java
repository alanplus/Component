package com.alan.common;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Mouse on 2017/9/19.
 */

public class ToastManager {

    private Toast mToast;
    private static ToastManager manager;

    public static ToastManager getInstance() {
        if (null == manager) {
            manager = new ToastManager();
        }
        return manager;
    }

    public Toast getToast(Context context) {
        if (null != mToast) {
            mToast.cancel();
        }
        mToast = Toast.makeText(context.getApplicationContext(), "", Toast.LENGTH_SHORT);
        return mToast;
    }

    public void showToast(Context context, String str) {
        try {
            mToast = getToast(context.getApplicationContext());
            mToast.setText(str);
            mToast.show();
        } catch (Exception ignored) {
        } catch (Error ignored) {
        }
    }

    public void cancelToast() {
        if (null != mToast) {
            mToast.cancel();
        }
        mToast = null;
    }

}
