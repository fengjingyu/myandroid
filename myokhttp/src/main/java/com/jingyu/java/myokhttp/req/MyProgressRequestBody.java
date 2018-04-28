package com.jingyu.java.myokhttp.req;

import java.io.IOException;

import com.jingyu.java.myokhttp.handler.IMyHttpHandler;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * 文件长传进度
 */
public class MyProgressRequestBody extends RequestBody {
    private RequestBody requestBody;
    private IMyHttpHandler iMyHttpHandler;
    private ProgressSink progressSink;

    public MyProgressRequestBody(RequestBody requestBody, IMyHttpHandler iMyHttpHandler) {
        this.requestBody = requestBody;
        this.iMyHttpHandler = iMyHttpHandler;
    }

    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        progressSink = new ProgressSink(sink);
        //将ProgressSink转化为BufferedSink供writeTo()使用
        BufferedSink bufferedSink = Okio.buffer(progressSink);
        requestBody.writeTo(bufferedSink);
        bufferedSink.flush();
    }

    protected final class ProgressSink extends ForwardingSink {

        private long byteWritten;

        public ProgressSink(Sink sink) {
            super(sink);
        }

        /**
         * 上传时回调该方法,监听进度
         */
        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            byteWritten += byteCount;
            if (iMyHttpHandler != null) {
                iMyHttpHandler.onUploadProgress(byteWritten, contentLength());
            }
        }
    }

    @Override
    public long contentLength() {
        try {
            return requestBody.contentLength();
        } catch (IOException e) {
            return -1;
        }
    }
}

