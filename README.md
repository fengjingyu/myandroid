## myokhttp
### gradle
```
implementation 'com.jingyu.java:myokhttp:0.3.1'
```

### maven
```
<dependency>
  <groupId>com.jingyu.java</groupId>
  <artifactId>myokhttp</artifactId>
  <version>0.3.1</version>
  <type>pom</type>
</dependency>
```

**同步/异步请求**
```
 // 同步请求
 MyHttpUtil.Async.get(url, null, null);
 // 异步请求
 MyHttpUtil.Sync.post(url, paramMap, null); 
```

**传参json**
```
MyHttpUtil.Sync.post(url, "{\"key\":\"value\"}", HttpConstants.JSON, new MyStringHttpHandler() {
      @Override
      public void onSuccess(MyReqInfo myReqInfo, MyRespInfo myRespInfo, String resultBean) {
          super.onSuccess(myReqInfo, myRespInfo, resultBean);
          System.out.println(resultBean);
      }
});
```

**表单**
```
MyHttpUtil.Async.post(url, "key=value&key2=value2", HttpConstants.FORM,, new MyStringHttpHandler() {
      @Override
      public void onSuccess(MyReqInfo myReqInfo, MyRespInfo myRespInfo, String resultBean) {
          super.onSuccess(myReqInfo, myRespInfo, resultBean);
          System.out.println(resultBean);
      }
});
```


**解析为model**
```
MyHttpUtil.Async.post(url, paramMap, new MyGsonHttpHandler<User>(User.class) {
     @Override
     public void onSuccess(MyReqInfo myReqInfo, MyRespInfo myRespInfo, User user) {
         super.onSuccess(myReqInfo, myRespInfo, user);
         System.out.println(resultBean);
     }
  });
```

**解析为string**
```
MyHttpUtil.Async.post(url, paramMap, new MyStringHttpHandler() {
     @Override
     public void onSuccess(MyReqInfo myReqInfo, MyRespInfo myRespInfo, String str) {
         super.onSuccess(myReqInfo, myRespInfo, str);
         System.out.println(str);
     }
  });
```

**自定义解析**
```
MyHttp.Async.post(url, null, new MyBaseHttpHandler<User>() {
      @Override
      public User onParse(MyReqInfo myReqInfo, MyRespInfo myRespInfo, InputStream inputStream, long totalSize) {
          // inputStream todo
          return new UsersModel();
      }

      @Override
      public void onSuccess(MyReqInfo myReqInfo, MyRespInfo myRespInfo, User user) {
          super.onSuccess(myReqInfo, myRespInfo, user);
      }
});
```


**下载文件**
```
MyHttpUtil.Async.post(url, null, new MyFileHttpHandler(file) {
    @Override
    public void onSuccess(MyReqInfo myReqInfo, MyRespInfo myRespInfo, File file) {
        super.onSuccess(myReqInfo, myRespInfo, file);
        System.out.println(file);
    }
});
```

**文件上传,进度监听**
```
  HashMap<String, Object> paramMap = new HashMap<>();
  map.put("key1", "value1");
  map.put("file", file1);
  map.put("file2", file2);
  map.put("file3", null); //空的file会被过滤
  MyHttp.Async.post(url, map, new MyStringHttpHandler() {
      @Override
      public void onUploadProgress(long bytesWritten, long totalSize) {
          super.onUploadProgress(bytesWritten, totalSize);
          Logger.i(bytesWritten + "--" + totalSize);
      }
  });
```
