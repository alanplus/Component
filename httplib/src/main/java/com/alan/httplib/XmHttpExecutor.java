package com.alan.httplib;

import android.graphics.Bitmap;

import com.alan.httplib.build.XmFileHttpBuilder;
import com.alan.httplib.build.XmHttpBuilder;
import com.alan.httplib.build.XmHttpStreamBuilder;
import com.alan.httplib.request.BaseRequest;
import com.alan.httplib.request.BitmapRequest;
import com.alan.httplib.request.FileRequest;
import com.alan.httplib.request.GetRequest;
import com.alan.httplib.request.PostFileRequest;
import com.alan.httplib.request.PostRequest;

import okhttp3.Call;


/**
 * @author Alan
 * 时 间：2019-12-13
 * 简 述：<功能简述>
 */
public class XmHttpExecutor {


    public static <T> XmHttpBuilder<T> get(String path) {
        return new XmHttpBuilder<>(new GetRequest(path));
    }


    public static <T> XmHttpBuilder<T> post(String path) {
        return new XmHttpBuilder<>(new PostRequest(path));
    }

    public static <T> XmHttpBuilder<T> postFile(String path) {
        return new XmHttpBuilder<>(new PostFileRequest(path));
    }

    public static XmHttpBuilder<Bitmap> bitmap(String path) {
        return new XmHttpStreamBuilder(new BitmapRequest(path));
    }

    public static XmFileHttpBuilder file(String path) {
        return new XmFileHttpBuilder(new FileRequest(path));
    }


}
