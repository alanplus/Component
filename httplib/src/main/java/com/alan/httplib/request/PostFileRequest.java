package com.alan.httplib.request;

import android.text.TextUtils;

import com.alan.httplib.LogUtil;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * @author Alan
 * 时 间：2019-12-13
 * 简 述：<功能简述>
 */
public class PostFileRequest extends BaseRequest {


    public PostFileRequest(String path) {
        super(path);
    }

    @Override
    protected Request create(String url, Request.Builder builder, String str) {
        builder.url(url);
        MultipartBody.Builder body = new MultipartBody.Builder();
        body.setType(MultipartBody.FORM);
        // 添加参数
        if (!mParams.isEmpty()) {
            for (String key : mParams.keySet()) {
                String value = mParams.get(key);
                File file = new File(value);
                if (file.exists() && file.isFile()) {
                    // 如果这是一个文件
                    body.addFormDataPart(key, file.getName(), new UpdateRequestBody(file));
                } else {
                    // 如果这是一个参数
                    if(!TextUtils.isEmpty(value)){
                        body.addFormDataPart(key, value);
                    }
                }
            }
        }
        return builder.post(body.build()).build();
    }

    private class UpdateRequestBody extends RequestBody {

        private File mFile;

        private UpdateRequestBody(File file) {
            mFile = file;
        }

        @Override
        public MediaType contentType() {
            return MediaType.parse("application/octet-stream");
        }

        @Override
        public long contentLength() {
            return mFile.length();
        }

        @Override
        public void writeTo(BufferedSink sink) throws IOException {
            Source source = null;
            try {
                source = Okio.source(mFile);
                Buffer buffer = new Buffer();
                // 文件大小
                long totalSize = contentLength();
                // 当前进度
                long currentSize = 0;
                long readCount;
                while ((readCount = source.read(buffer, 2048)) != -1) {
                    sink.write(buffer, readCount);
                    currentSize += readCount;
                    LogUtil.d(mFile.getName() + "文件总字节：" + totalSize + "，已上传字节：" + currentSize);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (source != null) {
                    source.close();
                }
            }
        }
    }
}
