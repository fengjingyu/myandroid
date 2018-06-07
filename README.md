myokhttp
=======

## Gradle
```
implementation 'com.jingyu.java:myokhttp:0.3.3'
```

## Maven
```
<dependency>
  <groupId>com.jingyu.java</groupId>
  <artifactId>myokhttp</artifactId>
  <version>0.3.3</version>
  <type>pom</type>
</dependency>
```

### Example
```
MyReqInfo.Builder builder = new MyReqInfo.Builder()
        .get()
        .headersMap(headerMap)
        .url("http://")
        .queryMap(new MyMap<String, Object>().myPut("key","value").myPut("key2","value2"));

MyReqInfo.Builder builder = new MyReqInfo.Builder()
        .post()
        .headersMap(headerMap)
        .url("http://")
        .queryMap(queryMap)
        .bodyContent(json)
        .contentTypeJson();

MyReqInfo.Builder builder = new MyReqInfo.Builder()
        .post()
        .headersMap(headerMap)
        .url("http://")
        .queryMap(queryMap)
        .bodyContent("abcdefg123")
        .contentTypeText();

MyReqInfo.Builder builder = new MyReqInfo.Builder()
        .post()
        .headersMap(headerMap)
        .url("http://")
        .bodyMap(bodyMap);  
        
new MyHttpClient().httpAsync(builder.builder(), new MyGsonHttpHandler<User>(User.class) {
     @Override
     public void onSuccess(MyReqInfo reqInfo, MyRespInfo respInfo, User user) {
         super.onSuccess(reqInfo, respInfo, user);
         System.out.println(user);
     }
});

```

### MyHttpUtil(简化使用)

**同步/异步请求**
```
 // 同步请求
 MyHttpUtil.Async.get(url, queryMap, null);
 // 异步请求
 MyHttpUtil.Sync.post(url, new MyMap<String, Object>().myPut("key","value).myPut("file",file), null);
 
```

**json传参**
```
MyHttpUtil.Sync.post(url, "{\"key\":\"value\"}", HttpConstants.JSON, new MyStringHttpHandler() {
      @Override
      public void onSuccess(MyReqInfo reqInfo, MyRespInfo respInfo, String resultBean) {
          super.onSuccess(reqInfo, respInfo, resultBean);
          System.out.println(resultBean);
      }
});
```

**表单传参**
```
MyHttpUtil.Async.post(url, "key=value&key2=value2", HttpConstants.FORM, new MyStringHttpHandler() {
      @Override
      public void onSuccess(MyReqInfo reqInfo, MyRespInfo respInfo, String resultBean) {
          super.onSuccess(reqInfo, respInfo, resultBean);
          System.out.println(resultBean);
      }
});

MyHttpUtil.Async.post(url, new MyMap<String, Object>().myPut("key","value").myPut("key2","value2"), new MyStringHttpHandler() {
     @Override
     public void onSuccess(MyReqInfo reqInfo, MyRespInfo respInfo, String str) {
         super.onSuccess(reqInfo, respInfo, str);
         System.out.println(str);
     }
});
```

**自动解析**
```
MyHttpUtil.Async.post(url, bodyMap, new MyGsonHttpHandler<User>(User.class) {
     @Override
     public void onSuccess(MyReqInfo reqInfo, MyRespInfo respInfo, User user) {
         super.onSuccess(reqInfo, respInfo, user);
         System.out.println(user);
     }
});
```

**自定义解析**
```
MyHttpUtil.Async.post(url, bodyMap, new MyBaseHttpHandler<User>() {
      @Override
      public User onParse(MyReqInfo reqInfo, MyRespInfo respInfo, InputStream inputStream, long totalSize) {
          // todo, inputStream
          return new User();
      }

      @Override
      public void onSuccess(MyReqInfo reqInfo, MyRespInfo respInfo, User user) {
          super.onSuccess(reqInfo, respInfo, user);
      }
});
```


**下载文件**
```
MyHttpUtil.Async.get(url, queryMap, new MyFileHttpHandler(saveFile) {
    @Override
    public void onSuccess(MyReqInfo reqInfo, MyRespInfo respInfo, File saveFile) {
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
  MyHttpUtil.Async.post(url, bodyMap, new MyStringHttpHandler() {
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
public class NewHttpCallBack<T> extends MyHttpCallBack<T> {

    public static Handler handler = new Handler(Looper.getMainLooper());

    public NewHttpCallBack(MyReqInfo reqInfo, IMyHttpHandler<T> iHttpHandler) {
        super(reqInfo, iHttpHandler);
    }

    @Override
    public void runOnSpecifiedThread(Runnable runnable) {
        handler.post(runnable);
    }
}

MyHttpClient httpClient = new MyHttpClient() {
    @Override
    protected MyHttpCallBack getHttpCallBack(MyReqInfo reqInfo, IMyHttpHandler iHttpHandler) {
        return new NewHttpCallBack(reqInfo, iHttpHandler);
    }
};
```
