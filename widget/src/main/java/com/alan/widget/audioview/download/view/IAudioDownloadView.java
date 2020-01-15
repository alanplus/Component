package com.alan.widget.audioview.download.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.alan.widget.audioview.download.IDownloadConfig;

/**
 * Created by Mouse on 2019/1/29.
 */
public interface IAudioDownloadView {

    void init(Context context, View view, AttributeSet attributeSet);
    void onClickEvent();
    void setAudioDownloadConfig(IDownloadConfig iDownloadConfig);
}
