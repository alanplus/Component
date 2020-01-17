package com.android.base.fragment;

import android.view.ViewGroup;

import com.android.base.state.IBaseStateView;
import com.android.base.state.IStateConfig;
import com.android.base.state.StateHelper;
import com.android.dialog.LoadingDialog;

/**
 * @author Alan
 * 时 间：2020-01-17
 * 简 述：<功能简述>
 */
public abstract class BaseStateFragment extends BaseFragment implements IStateConfig, IBaseStateView, LoadingDialog.OnDialogDismissListener {

    protected StateHelper stateHelper;

    @Override
    protected void initView() {
        stateHelper = new StateHelper(getActivity(), (ViewGroup) mRoot, this);
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
        stateHelper.showLoadingDialog(getActivity());
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
