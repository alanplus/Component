package com.android.base.state;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.alan.common.Logger;
import com.alan.common.ToastManager;
import com.alan.common.exception.XmException;
import com.android.dialog.LoadingDialog;
import com.android.dialog.XmDialogFactory;

/**
 * @author Alan
 * 时 间：2019-11-21
 * 简 述：<功能简述>
 */
public class StateHelper implements IBaseStateView {

    protected IStateView iStateView;

    private static final String EXCEPTION_VIEW_NULL = "root view is null";
    private static final String EXCEPTION_VIEW_NOT_FIND = "state view is not find";

    public static final String DEFAULT_LOADING_TEXT = "正在加载";
    public static final String DEFAULT_LOADING_SUCCESS_TEXT = "加载成功";
    public static final String DEFAULT_LOADING_FAILURE_TEXT = "加载失败";

    private LoadingDialog loadingDialog;
    private Context context;
    private IStateConfig iStateConfig;

    public StateHelper(Context context, ViewGroup viewGroup, IStateConfig iStateConfig) {
        try {
            this.context = context;
            this.iStateConfig = iStateConfig;
            iStateView = initStateView(viewGroup);
        } catch (XmException e) {
            Logger.error(e);
        }
    }

    public StateHelper(Context context, IStateView iStateView, IStateConfig iStateConfig) {
        this.context = context;
        this.iStateConfig = iStateConfig;
        this.iStateView = iStateView;
    }


    private IStateView initStateView(ViewGroup viewGroup) throws XmException {
        if (null == viewGroup) {
            throw new XmException(0, EXCEPTION_VIEW_NULL);
        }
        if (viewGroup instanceof IStateView) {
            return (IStateView) viewGroup;
        }

        int childCount = viewGroup.getChildCount();
        if (childCount == 0) {
            throw new XmException(0, EXCEPTION_VIEW_NOT_FIND);
        } else {
            for (int i = 0; i < childCount; ++i) {
                View v = viewGroup.getChildAt(i);
                if (v instanceof IStateView) {
                    return (IStateView) v;
                }
                if (v instanceof ViewGroup) {
                    IStateView stateView = this.initStateView((ViewGroup) v);
                    if (null != stateView) {
                        return stateView;
                    }
                }
            }
            return null;
        }
    }

    public IStateView getIStateView() {
        return iStateView;
    }

    public void showLoadingDialog(Context context) {
        if (null == loadingDialog) {
            loadingDialog = XmDialogFactory.showLoadingDialog(context, DEFAULT_LOADING_TEXT);
        } else {
            loadingDialog.show();
        }
    }


    @Override
    public void showLoadingState() {
        showLoadingState(DEFAULT_LOADING_TEXT);
    }

    @Override
    public void showLoadingState(String text) {
        if (null != iStateView) {
            iStateView.showLoadingState(text);
        }
    }

    @Override
    public void showFailureState(String text, boolean isRetry) {
        if (null != iStateView) {
            iStateView.showFailureState(text, isRetry);
        }
    }

    @Override
    public void showSuccessState() {
        if (null != iStateView) {
            iStateView.showSuccessState();
        }
    }

    @Override
    public void showLoadingDialog(String dialog) {
        if (null == loadingDialog) {
            loadingDialog = XmDialogFactory.showLoadingDialog(context, dialog);
        } else {
            loadingDialog.show();
        }
    }

    @Override
    public void dismissLoadingDialog(boolean isSuccess) {
        loadingDialog.dismiss(isSuccess ? iStateConfig.getLoadingSuccessText() : iStateConfig.getLoadingFailureText(), isSuccess, iStateConfig.getLoadingDismissLister());
    }

    @Override
    public void showToast(String text) {
        ToastManager.getInstance().showToast(context, text);
    }
}
