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
    private IMyHttpHandler IMyHttpHandler;
    private ProgressSink progressSink;

    public MyProgressRequestBody(RequestBody requestBody, IMyHttpHandler IMyHttpHandler) {
        this.requestBody = requestBody;
        this.IMyHttpHandler = IMyHttpHandler;
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
         * 上传时调用该方法,在其中调用回调函数将上传进度暴露出去,该方法提供了缓冲区的自己大小
         */
        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            byteWritten += byteCount;
            IMyHttpHandler.onUploadFileProgress(byteWritten, contentLength());
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

