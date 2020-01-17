package com.android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.android.tools.R;

/**
 * Created by Mouse on 2018/7/17.
 */

public abstract class XmBaseDialog extends Dialog {

    protected OnBaseDialogListener onBaseDialogListener;

    public XmBaseDialog(Context context) {
        super(context, R.style.customDialog);
    }

    public XmBaseDialog(@NonNull Context context, OnBaseDialogListener onBaseDialogListener) {
        super(context, R.style.customDialog);
        this.onBaseDialogListener = onBaseDialogListener;
    }

    public XmBaseDialog(@NonNull Context context, int style, OnBaseDialogListener onBaseDialogListener) {
        super(context, style);
        this.onBaseDialogListener = onBaseDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentRes());
        setSize(getDialogWidth());
        initView();
        if (null != onBaseDialogListener) onBaseDialogListener.init(this);
    }

    public abstract int getContentRes();

    public abstract void initView();


    public float getDialogWidth() {
        return 0.75f;
    }

    public void showFullScreen() {
        show();
        setSize(1.0);
    }

    protected void setSize(double widthScale) {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        int windowWidth = outMetrics.widthPixels;
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = (int) (windowWidth * widthScale); // 宽度设置为屏幕的一定比例大小
        //params.height = (int) (outMetrics.heightPixels * widthScale);
        getWindow().setAttributes(params);
    }

}
