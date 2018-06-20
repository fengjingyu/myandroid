package com.jingyu.app;

import com.jingyu.java.myokhttp.HttpCallBack;
import com.jingyu.java.myokhttp.HttpClient;
import com.jingyu.java.myokhttp.HttpConst;
import com.jingyu.java.myokhttp.HttpUtil;
import com.jingyu.java.myokhttp.handler.BaseHttpHandler;
import com.jingyu.java.myokhttp.handler.FileHttpHandler;
import com.jingyu.java.myokhttp.handler.GsonHttpHandler;
import com.jingyu.java.myokhttp.handler.IHttpHandler;
import com.jingyu.java.myokhttp.handler.StringHttpHandler;
import com.jingyu.java.myokhttp.req.ReqInfo;
import com.jingyu.java.myokhttp.resp.RespInfo;
import com.jingyu.java.mytool.collections.MyMap;
import com.jingyu.java.mytool.util.FileUtil;

import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    static String json = "[{\"id\":\"4\",\"list\":[{\"id\":\"10\",\"inputContent\":\"dfasdf\"}]},{\"id\":\"3\",\"list\":[{\"id\":\"9\",\"inputContent\":\"\"}]},{\"id\":\"2\",\"list\":[{\"id\":\"6\",\"inputContent\":\"\"}]},{\"id\":\"1\",\"list\":[{\"id\":\"4\",\"inputContent\":\"\"}]}]";
    static String json2 = "{\"key\":[{\"id\":\"4\",\"list\":[{\"id\":\"10\",\"inputContent\":\"dfasdf\"}]},{\"id\":\"3\",\"list\":[{\"id\":\"9\",\"inputContent\":\"\"}]},{\"id\":\"2\",\"list\":[{\"id\":\"6\",\"inputContent\":\"\"}]},{\"id\":\"1\",\"list\":[{\"id\":\"4\",\"inputContent\":\"\"}]}]}";

    @Test
    public void test1() {
        HttpUtil.Sync.post("http://localhost:8080/jsonparam/test1", "{\"key\":\"value-a\"}", null, new StringHttpHandler() {
            @Override
            public void onSuccess(ReqInfo reqInfo, RespInfo respInfo, String resultBean) {
                super.onSuccess(reqInfo, respInfo, resultBean);
                System.out.println("a-" + resultBean);
            }
        });
    }

    @Test
    public void test2() {
//        HttpUtil.Sync.post("http://localhost:8080/jsonparam/test2", "{\"key\":\"value-b\"}", "", new StringHttpHandler() {
//            @Override
//            public void onSuccess(ReqInfo reqInfo, RespInfo respInfo, String resultBean) {
//                super.onSuccess(reqInfo, respInfo, resultBean);
//                System.out.println("b-" + resultBean);
//            }
//        });

        HttpUtil.Sync.post("http://localhost:8080/jsonparam/test2?key=dog&key2=dog2", "key={\"key\":\"value-c\"}", "", new StringHttpHandler() {
            @Override
            public void onSuccess(ReqInfo reqInfo, RespInfo respInfo, String resultBean) {
                super.onSuccess(reqInfo, respInfo, resultBean);
                System.out.println("c-" + resultBean);
            }
        });

        HttpUtil.Sync.post("http://localhost:8080/jsonparam/test2?key=dog&key2=dog2", "key={\"key\":\"value-c\"}", HttpConst.JSON, new GsonHttpHandler<TestBean>(TestBean.class) {
            @Override
            public void onSuccess(ReqInfo reqInfo, RespInfo respInfo, TestBean resultBean) {
                super.onSuccess(reqInfo, respInfo, resultBean);
                System.out.println("c-" + resultBean);
            }

            @Override
            public void onParseException(ReqInfo reqInfo, RespInfo respInfo) {
                super.onParseException(reqInfo, respInfo);
                System.out.println("解析异常");
            }
        });
    }

    @Test
    public void test2_1() {
        Map<String, Object> map = new HashMap<>();
        map.put("key", "apple");
        map.put("key2", "orange");

        ReqInfo reqInfo = new ReqInfo.Builder().post().url("http://localhost:8080/jsonparam/test2").queryMap(map).bodyContent("{this is string or json}").builder();
        new HttpClient().httpAsync(reqInfo, new StringHttpHandler() {
            @Override
            public void onSuccess(ReqInfo reqInfo, RespInfo respInfo, String resultBean) {
                super.onSuccess(reqInfo, respInfo, resultBean);
                System.out.println("d-" + resultBean);
            }
        });

        HttpUtil.Sync.post("http://localhost:8080/jsonparam/test2?key=apple&key2=orange",
                "{this is string or json}", "", new BaseHttpHandler() {
                    @Override
                    public Object onParse(ReqInfo reqInfo, RespInfo respInfo, InputStream inputStream, long totalSize) {
                        return "123456";
                    }

                    @Override
                    public void onSuccess(ReqInfo reqInfo, RespInfo respInfo, Object resultBean) {
                        super.onSuccess(reqInfo, respInfo, resultBean);
                        System.out.println(resultBean);
                    }
                });
    }

    @Test
    public void test3() {
        HttpUtil.Sync.post("http://localhost:8080/jsonparam/test3?key=dog", "key2={\"key\":\"value-c\"}", HttpConst.FORM, new FileHttpHandler(FileUtil.createFile("d://", "testFile.txt")) {
            @Override
            public void onSuccess(ReqInfo reqInfo, RespInfo respInfo, File resultBean) {
                super.onSuccess(reqInfo, respInfo, resultBean);
                System.out.println("e-" + resultBean);
            }
        });
    }

    @Test
    public void test4() {
        HttpUtil.Sync.post("http://localhost:8080/jsonparam/test4", "{\"hehe\":\"haha-value--1\"}", HttpConst.JSON, new StringHttpHandler() {
            @Override
            public void onSuccess(ReqInfo reqInfo, RespInfo respInfo, String resultBean) {
                super.onSuccess(reqInfo, respInfo, resultBean);
                System.out.println("a-" + resultBean);
            }
        });
    }

    @Test
    public void testUploadServlet() {
        HttpUtil.Sync.post("http://localhost:80/UploadServlet",
                new MyMap<String, Object>()
                        .myPut("file1", FileUtil.createFile("d://", "testFile.txt"))
                        .myPut("keya", "valuea"), new StringHttpHandler() {

                    @Override
                    public void onUploadProgress(long bytesWritten, long totalSize) {
                        super.onUploadProgress(bytesWritten, totalSize);
                        System.out.println(bytesWritten + "-----" + totalSize);
                    }

                    @Override
                    public void onSuccess(ReqInfo reqInfo, RespInfo respInfo, String resultBean) {
                        super.onSuccess(reqInfo, respInfo, resultBean);
                        System.out.println(resultBean);
                    }
                });
    }

    @Test
    public void test0() {
        HttpClient httpClient = new HttpClient() {
            @Override
            protected HttpCallBack createHttpCallBack(ReqInfo reqInfo, IHttpHandler iHttpHandler) {
                return super.createHttpCallBack(reqInfo, iHttpHandler);
            }

            @Override
            protected ReqInfo interceptBuildRequest(ReqInfo reqInfo) {
                return super.interceptBuildRequest(reqInfo);
            }

            @Override
            protected OkHttpClient initOkHttpClient() {
                return super.initOkHttpClient();
            }
        };
        HttpUtil.setHttpClient(httpClient);
    }

    @Test
    public void test() {
        HttpUtil.Sync.post("http://localhost:8080/test?date=2019-01-30", new MyMap<String, Object>().myPut("date", new Date()), new StringHttpHandler() {
            @Override
            public boolean onMatchAppCode(ReqInfo reqInfo, RespInfo respInfo, String resultBean) {
                return respInfo.getHttpCode() == 200;
            }

            @Override
            public void onAppCodeException(ReqInfo reqInfo, RespInfo respInfo, String resultBean) {
                super.onAppCodeException(reqInfo, respInfo, resultBean);
                System.out.println(resultBean);
            }

            @Override
            public void onSuccess(ReqInfo reqInfo, RespInfo respInfo, String resultBean) {
                super.onSuccess(reqInfo, respInfo, resultBean);
                System.out.println(resultBean);
            }

            @Override
            public void onFailure(ReqInfo reqInfo, RespInfo respInfo) {
                super.onFailure(reqInfo, respInfo);
                System.out.println("failure");
            }
        });
    }
}