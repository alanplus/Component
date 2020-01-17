package com.android.base.state;

/**
 * @author Alan
 * 时 间：2019-11-21
 * 简 述：<功能简述>
 */
public interface IBaseStateView {

    void showLoadingState();

    void showLoadingState(String text);

    void showFailureState(String text, boolean isRetry);

    void showSuccessState();

    void showLoadingDialog(String dialog);

    void dismissLoadingDialog(boolean isSuccess);

    void showToast(String text);
}
