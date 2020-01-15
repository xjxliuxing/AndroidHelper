package com.xjx.helper.http.progress;

import com.xjx.helper.interfaces.ProgressResponseListener;

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

    public ProgressResponseBody(ResponseBody responseBody, ProgressResponseListener progressListener) {
        this.responseBody = responseBody;
        this.progressListener = progressListener;
        total = responseBody.contentLength();
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
            public long read(Buffer sink, long byteCount) throws IOException {
                if (bytesRead == 0) {
                    if (null != progressListener) {
                        progressListener.onStart();
                    }
                }

                bytesRead = super.read(sink, byteCount);
                if (bytesRead != -1) {
                    totalBytes += bytesRead;
                    if (null != progressListener) {
                        progressListener.onLoading(totalBytes, total, totalBytes == total);
                    }
                }
                if (null != progressListener) {
                    if ((totalBytes > 0) && (totalBytes == total)) {
                        progressListener.onComplete("");
                    }
                }
                return bytesRead;
            }
        };
    }

}
