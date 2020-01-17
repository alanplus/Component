package com.android.base.state;

import android.widget.TextView;

/**
 * Created by Mouse on 2018/9/20.
 */

public interface IStateView {

    void showLoadingState(String text);
    void showFailureState(String text, boolean isRetry);
    void showSuccessState();
    TextView getRetryView();
}
