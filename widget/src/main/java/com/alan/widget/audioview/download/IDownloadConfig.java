package com.alan.widget.audioview.download;

/**
 * Created by Mouse on 2019/1/29.
 */
public interface IDownloadConfig {
    String getUrl();

    String getDestName(String name);

    String getAudioName();
}
