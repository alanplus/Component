package com.alan.httplib.build;

import android.text.TextUtils;

import com.alan.httplib.IParseStrategy;
import com.alan.httplib.XmHttpConfig;
import com.alan.httplib.request.BaseRequest;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Response;

/**
 * @author Alan
 * 时 间：2019-12-13
 * 简 述：<功能简述>
 */
public class XmHttpBuilder<T> {

    boolean isEncoding;
    BaseRequest baseRequest;

    HashMap<String, String> mParams;
    HashMap<String, String> mHeaders;
    String tag;
    MediaType mediaType;

    IParseStrategy<T> iParseStrategy;
    BaseRequest.OnHttpCallBack<T> onHttpCallBack;


    public XmHttpBuilder(BaseRequest baseRequest) {
        this.baseRequest = baseRequest;
        mParams = new HashMap<>();
        mHeaders = new HashMap<>();
        mParams.putAll(XmHttpConfig.getInstance().getHttpParamMap());
        mHeaders.putAll(XmHttpConfig.getInstance().getHttpHeadMap());
        isEncoding = XmHttpConfig.getInstance().isEncoding();
        this.tag = baseRequest.getUrl();

    }


    public XmHttpBuilder<T> addParam(String key, String value) {
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            mParams.put(key, value);
        }
        return this;
    }

    public XmHttpBuilder<T> addParams(HashMap<String, String> hashMap) {
        if (null != hashMap) {
            mParams.putAll(hashMap);
        }
        return this;
    }

    public XmHttpBuilder<T> addHeader(String key, String value) {
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            mHeaders.put(key, value);
        }
        return this;
    }

    public XmHttpBuilder<T> addAllHeaders(HashMap<String, String> hashMap) {
        if (null != hashMap) {
            mHeaders.putAll(hashMap);
        }
        return this;
    }

    public XmHttpBuilder<T> setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public XmHttpBuilder<T> setParseStrategy(IParseStrategy<T> iParseStrategy) {
        this.iParseStrategy = iParseStrategy;
        return this;
    }

    public XmHttpBuilder<T> setEncoding(boolean encoding) {
        isEncoding = encoding;
        return this;
    }

    public XmHttpBuilder<T> setParams(HashMap<String, String> mParams) {
        this.mParams = mParams;
        return this;
    }

    public XmHttpBuilder<T> setHeaders(HashMap<String, String> mHeaders) {
        this.mHeaders = mHeaders;
        return this;
    }

    public XmHttpBuilder<T> setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public XmHttpBuilder<T> setOnHttpCallBack(BaseRequest.OnHttpCallBack<T> onHttpCallBack) {
        this.onHttpCallBack = onHttpCallBack;
        return this;
    }

     void complete() {
        baseRequest.setEncoding(isEncoding);
        baseRequest.setHeaders(mHeaders);
        baseRequest.setMediaType(mediaType);
        baseRequest.setParams(mParams);
        baseRequest.setTag(tag);
    }

    public T build() {


        try {
            complete();
            String content = baseRequest.execute();
            return iParseStrategy.parse(content);
        } catch (Exception e) {

        }
        return null;
    }

    public void buildOnThread() {
        complete();
        try {
            baseRequest.executeWithThread(callback);
        } catch (Exception e) {
            handlerFailure(null, e);
        }
    }

    Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            handlerFailure(call, e);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if (response.isSuccessful() && null != response.body()) {
                try {
                    handlerSuccess(call, response.body().string());
                } catch (IOException e) {
                    handlerFailure(call, e);
                }
            }
        }
    };

    void handlerSuccess(final Call call, final String string) {
        T t = null;
        if (null != iParseStrategy) {
            t = iParseStrategy.parse(string);
        }
        final T finalT = t;
        XmHttpConfig.getInstance().getHandler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (null != onHttpCallBack) {
                        onHttpCallBack.onSuccess(string, finalT);
                    }
                } catch (Exception e) {
                    handlerFailure(call, e);
                }
            }
        });
    }

    void handlerFailure(final Call call, final Exception e) {
        if (null != onHttpCallBack) {
            XmHttpConfig.getInstance().getHandler().post(new Runnable() {
                @Override
                public void run() {
                    onHttpCallBack.onFailure(call, e);
                }
            });
        }
    }


}
