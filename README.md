myokhttp
=======

## Gradle
```
implementation 'com.jingyu.java:myokhttp:0.5.0'
```

## Maven
```
<dependency>
  <groupId>com.jingyu.java</groupId>
  <artifactId>myokhttp</artifactId>
  <version>0.5.0</version>
  <type>pom</type>
</dependency>
```

### Example
```
ReqInfo.Builder builder = new ReqInfo.Builder()
        .get()
        .headersMap(headerMap)
        .url("http://")
        .queryMap(new MyMap<String, Object>().myPut("key","value").myPut("key2","value2"));

ReqInfo.Builder builder = new ReqInfo.Builder()
        .post()
        .headersMap(headerMap)
        .url("http://")
        .queryMap(queryMap)
        .bodyContent(json)
        .contentTypeJson();

ReqInfo.Builder builder = new ReqInfo.Builder()
        .post()
        .headersMap(headerMap)
        .url("http://")
        .queryMap(queryMap)
        .bodyContent("abcdefg123")
        .contentTypeText();

ReqInfo.Builder builder = new ReqInfo.Builder()
        .post()
        .headersMap(headerMap)
        .url("http://")
        .bodyMap(new MyMap<String, Object>().myPut("key","value").myPut("file",file));
        
new HttpClient().httpAsync(builder.builder(), new GsonHttpHandler<User>(User.class) {
     @Override
     public void onSuccess(ReqInfo reqInfo, RespInfo respInfo, User user) {
         super.onSuccess(reqInfo, respInfo, user);
         System.out.println(user);
     }
});

```

### HttpUtil(简化使用)

**同步/异步请求**
```
 // 同步请求
 HttpUtil.Async.get(url, queryMap, null);
 // 异步请求
 HttpUtil.Sync.post(url, new MyMap<String, Object>().myPut("key","value).myPut("file",file), null);
 
```

**json传参**
```
HttpUtil.Sync.post(url, "{\"key\":\"value\"}", HttpConstants.JSON, new StringHttpHandler() {
      @Override
      public void onSuccess(ReqInfo reqInfo, RespInfo respInfo, String resultBean) {
          super.onSuccess(reqInfo, respInfo, resultBean);
          System.out.println(resultBean);
      }
});
```

**表单传参**
```
HttpUtil.Async.post(url, "key=value&key2=value2", HttpConstants.FORM, new StringHttpHandler() {
      @Override
      public void onSuccess(ReqInfo reqInfo, RespInfo respInfo, String resultBean) {
          super.onSuccess(reqInfo, respInfo, resultBean);
          System.out.println(resultBean);
      }
});
```

**multi表单**
```
HttpUtil.Async.post(url, new MyMap<String, Object>().myPut("key","value").myPut("file",file), new StringHttpHandler() {
     @Override
     public void onSuccess(ReqInfo reqInfo, RespInfo respInfo, String str) {
         super.onSuccess(reqInfo, respInfo, str);
         System.out.println(str);
     }
});
```

**自动解析**
```
HttpUtil.Async.post(url, null, new GsonHttpHandler<User>(User.class) {
     @Override
     public void onSuccess(ReqInfo reqInfo, RespInfo respInfo, User user) {
         super.onSuccess(reqInfo, respInfo, user);
         System.out.println(user);
     }
});
```

**自定义解析**
```
HttpUtil.Sync.post(url, null, new BaseHttpHandler<User>() {
      @Override
      public User onParse(ReqInfo reqInfo, RespInfo respInfo, InputStream inputStream, long totalSize) {
          // todo, inputStream
          return new User();
      }

      @Override
      public void onSuccess(ReqInfo reqInfo, RespInfo respInfo, User user) {
          super.onSuccess(reqInfo, respInfo, user);
      }
});
```


**下载文件**
```
HttpUtil.Async.get(url, queryMap, new FileHttpHandler(saveFile) {
    @Override
    public void onSuccess(ReqInfo reqInfo, RespInfo respInfo, File saveFile) {
        super.onSuccess(reqInfo, respInfo, saveFile);
        System.out.println(saveFile);
    }
});
```

**文件上传,进度监听**
```
  HashMap<String, Object> bodyMap = new HashMap<>();
  map.put("key", "value");
  map.put("file", file1);
  map.put("file2", file2);
  map.put("file3", null); //空的file会被过滤
  HttpUtil.Async.post(url, bodyMap, new StringHttpHandler() {
      @Override
      public void onUploadProgress(long bytesWritten, long totalSize) {
          super.onUploadProgress(bytesWritten, totalSize);
          Logger.i(bytesWritten + "--" + totalSize);
      }
  });
```

**回调执行的线程设置**
```
//android中,请求是异步发送的,但如果需要把回调统一放到主线程处理
public class NewHttpCallBack<T> extends HttpCallBack<T> {

    public static Handler handler = new Handler(Looper.getMainLooper());

    public NewHttpCallBack(ReqInfo reqInfo, IHttpHandler<T> iHttpHandler) {
        super(reqInfo, iHttpHandler);
    }

    @Override
    public void runOnSpecifiedThread(Runnable runnable) {
        handler.post(runnable);
    }
}

HttpClient httpClient = new HttpClient() {
    @Override
    protected ReqInfo interceptBuildRequest(ReqInfo reqInfo) {
      // 统一配置请求头
      return reqInfo.newBuilder()
              .addHeader("token", "abcdefg1234567890")
              .addHeader("os", "android")
              .builder();
    }

    @Override
    protected MyHttpCallBack createHttpCallBack(ReqInfo reqInfo, IHttpHandler iHttpHandler) {
      return new MyHttpCallBack(reqInfo, iHttpHandler);
    }

    @Override
    protected OkHttpClient initOkHttpClient() {
      return new OkHttpClient.Builder()
              .connectTimeout(10, TimeUnit.SECONDS)
              .readTimeout(20, TimeUnit.SECONDS)
              //.addInterceptor()
              .build();
    }
};
```
