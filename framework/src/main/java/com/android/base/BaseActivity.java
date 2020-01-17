package com.android.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;


import com.alan.common.ResourceUtils;
import com.alan.common.statusbar.StatusBarTools;
import com.android.base.activity.ActivityManager;
import com.android.base.state.IBaseStateView;
import com.android.base.state.IStateConfig;
import com.android.base.state.StateHelper;
import com.android.dialog.LoadingDialog;
import com.android.tools.R;

/**
 * @author Alan
 * 时 间：2019-11-20
 * 简 述：<功能简述>
 */
public abstract class BaseActivity extends AppCompatActivity implements IStateConfig, IBaseStateView, LoadingDialog.OnDialogDismissListener {

    protected StateHelper stateHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentId());
        ActivityManager.onCreate(this);
        initStatusBar();
        initView();

    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        stateHelper = new StateHelper(this, (ViewGroup) view, this);

    }

    protected void initStatusBar() {
        int bgColor = ResourceUtils.getColorFromTheme(this, R.attr.status_bar_color, Color.WHITE);
        boolean textColorIsWhite = ResourceUtils.getBoolFromTheme(this, R.attr.status_bar_text_is_white, false);
        StatusBarTools.getStatusBarTools().setStatusBarColor(this, bgColor, textColorIsWhite);
    }

    protected abstract void initView();

    @Override
    protected void onStart() {
        super.onStart();
        ActivityManager.onStart(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityManager.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ActivityManager.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ActivityManager.onStop(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.onDestroy(this);
    }

    protected abstract int getContentId();

    protected Activity getActivity() {
        return this;
    }


    @Override
    public String getLoadingSuccessText() {
        return StateHelper.DEFAULT_LOADING_SUCCESS_TEXT;
    }

    @Override
    public String getLoadingFailureText() {
        return StateHelper.DEFAULT_LOADING_FAILURE_TEXT;
    }

    @Override
    public LoadingDialog.OnDialogDismissListener getLoadingDismissLister() {
        return this;
    }

    @Override
    public void showLoadingState() {
        stateHelper.showLoadingState();
    }

    @Override
    public void showLoadingState(String text) {
        stateHelper.showLoadingState(text);
    }

    @Override
    public void showFailureState(String text, boolean isRetry) {
        stateHelper.showFailureState(text, isRetry);
    }

    @Override
    public void showSuccessState() {
        stateHelper.showLoadingState();
    }

    @Override
    public void showLoadingDialog(String dialog) {
        stateHelper.showLoadingDialog(this);
    }

    @Override
    public void dismissLoadingDialog(boolean isSuccess) {
        stateHelper.dismissLoadingDialog(isSuccess);
    }

    @Override
    public void showToast(String text) {
        stateHelper.showToast(text);
    }

    @Override
    public void onDialogDismiss() {

    }
}
