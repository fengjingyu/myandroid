myokhttp
=======

## Gradle
```
implementation 'com.jingyu.java:myhttp:0.0.1'
```

## Maven
```
<dependency>
  <groupId>com.jingyu.java</groupId>
  <artifactId>myhttp</artifactId>
  <version>0.0.1</version>
  <type>pom</type>
</dependency>
```

### Example
```
ReqInfo.Builder builder = new ReqInfo.Builder()
        .get()
        .url("http://");
        
ReqInfo.Builder builder = new ReqInfo.Builder()
        .get()
        .headersMap(headerMap)
        .url("http://")
        .queryMap(new MyMap<String, Object>().myPut("key","value").myPut("key2","value2"));

ReqInfo.Builder builder = new ReqInfo.Builder()
        .post()
        .url("http://")
        .bodyContent("{\"key\":\"value\"}")
        .contentTypeJson();

ReqInfo.Builder builder = new ReqInfo.Builder()
        .post()
        .url("http://")
        .bodyContent("abcdefg123")
        .contentTypeText();

ReqInfo.Builder builder = new ReqInfo.Builder()
        .post()
        .url("http://")
        .queryMap(new MyMap<String, Object>().myPut("key","value"))
        .bodyFormMap(new MyMap<String, Object>().myPut("key2","value2").myPut("file",file));

**异步请求**        
new HttpClient().httpAsync(builder.builder(), new GsonHttpHandler<User>(User.class) {
     @Override
     public void onSuccess(ReqInfo reqInfo, RespInfo respInfo, User user) {
         super.onSuccess(reqInfo, respInfo, user);
         System.out.println(user);
     }
});

**同步请求**        
new HttpClient().httpSync(builder.builder(), new GsonHttpHandler<User>(User.class) {
     @Override
     public void onSuccess(ReqInfo reqInfo, RespInfo respInfo, User user) {
         super.onSuccess(reqInfo, respInfo, user);
         System.out.println(user);
     }
});

```

### MyHttp(简化使用)

**同步/异步请求**
```
 // 异步请求
 MyHttp.Async.get(url, new MyMap<String, Object>().myPut("key","value"), null);
 
 // 同步请求
 MyHttp.Sync.post(url, new MyMap<String, Object>().myPut("key","value).myPut("file",file), null);
 
```

**json传参**
```
MyHttp.Sync.post(url, "{\"key\":\"value\"}", HttpConst.JSON, new StringHttpHandler() {
      @Override
      public void onSuccess(ReqInfo reqInfo, RespInfo respInfo, String resultBean) {
          super.onSuccess(reqInfo, respInfo, resultBean);
          System.out.println(resultBean);
      }
});
```

**表单传参**
```
MyHttp.Async.post(url, new MyMap<String, Object>().myPut("key","value").myPut("key2","value2"), new StringHttpHandler() {
      @Override
      public void onSuccess(ReqInfo reqInfo, RespInfo respInfo, String resultBean) {
          super.onSuccess(reqInfo, respInfo, resultBean);
          System.out.println(resultBean);
      }
});

MyHttp.Async.post(url, "key=value&key2=value2", HttpConst.FORM, new StringHttpHandler() {
      @Override
      public void onSuccess(ReqInfo reqInfo, RespInfo respInfo, String resultBean) {
          super.onSuccess(reqInfo, respInfo, resultBean);
          System.out.println(resultBean);
      }
});
```

**multi表单**
```
MyHttp.Async.post(url, new MyMap<String, Object>().myPut("key","value").myPut("file",file), new StringHttpHandler() {
     @Override
     public void onSuccess(ReqInfo reqInfo, RespInfo respInfo, String str) {
         super.onSuccess(reqInfo, respInfo, str);
         System.out.println(str);
     }
});
```

**自动解析**
```
MyHttp.Async.get(url, null, new GsonHttpHandler<User>(User.class) {
     @Override
     public void onSuccess(ReqInfo reqInfo, RespInfo respInfo, User user) {
         super.onSuccess(reqInfo, respInfo, user);
         System.out.println(user);
     }
});
```

**自定义解析, 获取inputStream**
```
MyHttp.Sync.post(url, null, new BaseHttpHandler<User>() {
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
MyHttp.Async.get(url, queryMap, new FileHttpHandler(saveFile) {
    @Override
    public void onSuccess(ReqInfo reqInfo, RespInfo respInfo, File saveFile) {
        super.onSuccess(reqInfo, respInfo, saveFile);
        System.out.println(saveFile);
    }
});
```

**多文件上传,进度监听**
```
  HashMap<String, Object> bodyFormMap = new HashMap<>();
  map.put("key", "value");
  map.put("file", file1);
  map.put("file2", file2);
  map.put("file3", null); //空的file会被过滤
  MyHttp.Async.post(url, bodyFormMap, new StringHttpHandler() {
      @Override
      public void onUploadProgress(long bytesWritten, long totalSize) {
          super.onUploadProgress(bytesWritten, totalSize);
          Logger.i(bytesWritten + "--" + totalSize);
      }
  });
```

**回调执行的设置**
```
//android中,请求是异步发送的,但如果需要把回调统一放到主线程处理
public class MainThreadCallBack extends HttpCallBack {

    private static Handler mHandler = new Handler(Looper.getMainLooper());

    public MainThreadCallBack(ReqInfo reqInfo, IHttpHandler iHttpHandler) {
        super(reqInfo, iHttpHandler);
    }

    @Override
    public void runOnSpecifiedThread(Runnable runnable) {
        if (runnable != null) {
            mHandler.post(runnable);
        }
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
    protected MainThreadCallBack createHttpCallBack(ReqInfo reqInfo, IHttpHandler iHttpHandler) {
      return new MainThreadCallBack(reqInfo, iHttpHandler);
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

MyHttp.setHttpClient(httpClient);
```
