package com.android.base.state;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.LibConfig;


/**
 * Created by Mouse on 2018/9/21.
 */

public class StateRelativeLayout extends RelativeLayout implements IStateView {

    protected ILoadingView iLoadingView;
    protected IFailureView iFailureView;


    public StateRelativeLayout(Context context) {
        super(context);
    }

    public StateRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void showLoadingState(String text) {
        reset();
        addView(generateLoadingView(text), generateLoadingLayoutParams());
        iLoadingView.start();
    }

    @Override
    public void showFailureState(String text, boolean isRetry) {
        reset();
        addView(generateFailureView(), generateLoadingLayoutParams());
        iFailureView.setText(text);
        iFailureView.setVisiable(isRetry);
    }

    @Override
    public void showSuccessState() {
        reset();
    }

    @Override
    public TextView getRetryView() {
        return iFailureView.getRetryTextView();
    }

    protected LayoutParams generateLoadingLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    protected View generateLoadingView(String text) {
        if (null == iLoadingView) {
            iLoadingView = LibConfig.getAndroidToolsConfig().getILoadingView(getContext());
        }
        iLoadingView.setText(text);
        return (View) iLoadingView;
    }

    protected View generateFailureView() {
        if (null == iFailureView) {
            iFailureView = LibConfig.getAndroidToolsConfig().getIFailureView(getContext());
        }
        return iFailureView.getView();
    }

    public void reset() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = getChildAt(i);
            if ((null != iLoadingView && iLoadingView.getView() == v) || (null != iFailureView && iFailureView.getView() == v)) {
                removeView(v);
            }
        }
    }

}
