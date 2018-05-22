package com.jingyu.java.myokhttp.handler;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * okhttp 3.5开始支持websocket
 * 允许app在处于前台时去打开websocket，而当app处于后台时关闭websocket。这就是持续连接被建议使用的方式。使用Service来保持持续连接被认为是错误的行为并且doze模式将使得你的app的声明变得非常艰难（即很容易被系统杀死）
 */
public class MyWebSocketListener extends WebSocketListener {

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        // 异步发送
        // 当WebSocket和远程建立连接时回调；
        webSocket.send("hello world");
        webSocket.send("welcome");
        webSocket.send(ByteString.decodeHex("adef")); // 返回结果（即返回true）仅仅表明消息被插入队列，但是它并不会反应出传送的结果。
        webSocket.close(1000, "再见"); // webSocket.close(0, “bye”); 请求服务器优雅地关闭连接然后等待确认 。在关闭之前，所有已经在队列中的消息将被传送完毕
        // webSocket.cancel(); // 它会丢弃所有已经在队列中的消息然后直接关闭socket
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        // 异步线程回调
        // 就是接收到消息时回调，只是消息内容的类型不同
        output("onMessage: " + text);
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        // 异步线程回调
        // 就是接收到消息时回调，只是消息内容的类型不同
        output("onMessage byteString: " + bytes);
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        // onClosing是当远程端暗示没有数据交互时回调（即此时准备关闭，但连接还没有关闭）
        webSocket.close(1000, null);
        output("onClosing: " + code + "/" + reason);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        // 当连接已经释放的时候被回调
        output("onClosed: " + code + "/" + reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        // 失败时被回调（包括连接失败，发送失败等）
        output("onFailure: " + t.getMessage());
    }

    private void output(final String content) {
        System.out.println(content);
    }

    private void connect() {
        Request request = new Request.Builder()
                .url("ws://echo.websocket.org") // 之前由于公司服务器那边还没处理好应对WebSocket连接，差点都自己搭服务器了，后来发现WebSocket官网就提供了相应url可以测试。
                .build();
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.SECONDS)
                .build();
        client.newWebSocket(request, new MyWebSocketListener() {


        }); // 这一步代码OkHttp将试图和服务器建立连接

        client.dispatcher().executorService().shutdown();
    }
}
