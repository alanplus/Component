package com.alan.common;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Handler;

/**
 * Created by Mouse on 2019-06-26.
 */
public class ExitUtils {


    private static ExitUtils exitTools;

    protected int count = 0;

    private ExitUtils() {

    }

    public static ExitUtils getInstance() {
        if (null == exitTools) {
            exitTools = new ExitUtils();
        }
        return exitTools;
    }

    public void exit(Activity activity) {
        if (count == 1) {
            activity.finish();
            return;
        }
        count++;
        ToastManager.getInstance().showToast(activity, ResourceUtils.getResourceStr(activity,R.string.home_exit_msg));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                count = 0;
            }
        }, ResourceUtils.getResourceInteger(activity,R.integer.home_exit_duration));
    }
}
