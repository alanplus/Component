package com.alan.httplib.request;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Alan
 * 时 间：2019-12-17
 * 简 述：<功能简述>
 */
public class BitmapRequest extends BaseRequest{


    public BitmapRequest(String path) {
        super(path);
    }

    @Override
    protected Request create(String url, Request.Builder builder, String body) {
        if(!TextUtils.isEmpty(body)){
            url += "?" + body;
        }
        return builder.url(url).build();
    }

}
