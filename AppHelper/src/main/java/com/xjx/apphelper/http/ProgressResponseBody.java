package com.xjx.apphelper.http.progress;

import com.xjx.apphelper.interfaces.ProgressResponseListener;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * 拥有进度的响应体
 */
public class ProgressResponseBody extends ResponseBody {

    private final ResponseBody responseBody;
    private final ProgressResponseListener progressListener;
    private BufferedSource bufferedSource;
    private long total;
    private Object tag;

    public ProgressResponseBody(ResponseBody responseBody, ProgressResponseListener progressListener, Object tag) {
        this.responseBody = responseBody;
        this.progressListener = progressListener;
        total = responseBody.contentLength();
        this.tag = tag;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytes = 0L;
            long bytesRead = 0;

            @Override
            public long read(Buffer sink, long byteCount) {
                if (bytesRead == 0) {
                    if (null != progressListener) {
                        progressListener.onStart(total, tag);
                    }
                }
                try {
                    bytesRead = super.read(sink, byteCount);
                } catch (IOException e) {
                    e.printStackTrace();
                    if (null != progressListener) {
                        progressListener.onFailure(e.getMessage(), tag);
                    }
                }
                if (bytesRead != -1) {
                    totalBytes += bytesRead;
                    if (null != progressListener) {
                        progressListener.onLoading(totalBytes, total, totalBytes == total, tag);
                    }
                }
                if (null != progressListener) {
                    if (bytesRead != -1) {
                        if ((totalBytes > 0) && (totalBytes == total)) {
                            progressListener.onComplete(tag);
                        }
                    }
                }
                return bytesRead;
            }
        };
    }

}
