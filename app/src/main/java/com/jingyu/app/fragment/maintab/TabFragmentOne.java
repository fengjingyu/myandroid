package com.jingyu.app.fragment.maintab;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jingyu.android.common.log.Logger;
import com.jingyu.app.R;
import com.jingyu.app.middle.MyBaseFragment;
import com.jingyu.app.middle.MyEnv;
import com.jingyu.app.middle.MyFile;
import com.jingyu.app.middle.MyHttp;
import com.jingyu.app.middle.MyImg;
import com.jingyu.app.middle.MySp;
import com.jingyu.app.middle.okhttp.handler.MyBaseHttpHandler;
import com.jingyu.app.middle.okhttp.handler.MyFileHttpHandler;
import com.jingyu.app.middle.okhttp.handler.MyGsonHttpHandler;
import com.jingyu.app.middle.okhttp.handler.MyJsonHttpHandler;
import com.jingyu.app.model.combination.UsersModel;
import com.jingyu.java.myhttp.handler.GsonHttpHandler;
import com.jingyu.java.myhttp.req.ReqInfo;
import com.jingyu.java.myhttp.resp.RespInfo;
import com.jingyu.java.myhttp.tool.util.IoUtil;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;

public class TabFragmentOne extends MyBaseFragment {
    String ip = "http://192.168.43.93/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return createView(inflater, R.layout.fragment_tab_one, container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //获取控件 -->有自动生成此类代码的工具
        ImageView img = getViewById(R.id.img);

        //图片加载
        MyImg.displayRoundCorner("https://www.baidu.com/img/bd_logo1.png", img, 50);

        //日志
        Logger.i(null);
        Logger.e(null);

        //调试日志
        Logger.dShortToast("调试吐司");
        //吐司
        Logger.shortToast("吐司");

        //sp存储 -->有自动生成此类代码的工具
        MySp.setUserId("123");
        MySp.getUserId();

        //调试环境（域名 吐司 日志 crash管理等）
        Logger.i(MyEnv.CURRENT_RUN_ENVIRONMENT);

        //项目目录管理
        MyFile.getAppDir(getContext());
        MyFile.getFileInAppDir(getContext(), "hello");
        MyFile.getPhotoDir(getContext());
        MyFile.getFileInPhotoDir(getContext(), "hello2");

        //http
        String url = "http://www.baidu.com";
        HashMap<String, Object> param = new HashMap<>();
        param.put("key1", "value1");
        param.put("key2", "value2");

        //http-get 可以不处理回调
        MyHttp.Async.get(url, null, null);

        //http-get + 参数 可以不处理回调
        MyHttp.Async.get(url, param, null);

        //*****
        //JsonHttpHandler适用于不需要建模型的回调
        //http-get 假如request成功 一般不需要判断状态码，BaseHttpHandler会统一处理
        MyHttp.Async.get(url, param, new MyJsonHttpHandler() {
            @Override
            public void onSuccess(ReqInfo reqInfo, RespInfo respInfo, MyJsonBean resultBean) {
                super.onSuccess(reqInfo, respInfo, resultBean);
            }
        });

        //*****
        //GsonHttpHandler适用于需要建模型的回调
        //http-post 假如request成功 一般不需要判断状态码，BaseHttpHandler会统一处理
        MyHttp.Async.post(url, param, new GsonHttpHandler<UsersModel>(UsersModel.class) {
            @Override
            public void onSuccess(ReqInfo reqInfo, RespInfo respInfo, UsersModel resultBean) {
                super.onSuccess(reqInfo, respInfo, resultBean);
            }
        });


        //*****
        //BaseHttpHandler适用于需要手动解析的回调
        //http-post 假如request成功 一般不需要判断状态码，BaseHttpHandler会统一处理
        MyHttp.Async.post(url, param, new MyGsonHttpHandler<UsersModel>(getActivity(), UsersModel.class) {
            @Override
            public UsersModel onParse(ReqInfo reqInfo, RespInfo respInfo, InputStream inputStream, long totalSize) {
                // 也可以手动解析，失败返回null
                return new UsersModel();
            }

            @Override
            public void onSuccess(ReqInfo reqInfo, RespInfo respInfo, UsersModel resultBean) {
                super.onSuccess(reqInfo, respInfo, resultBean);
            }
        });

        //http-post + 参数 处理回调 进度条
        MyHttp.Async.post(url, param, new MyGsonHttpHandler<UsersModel>(getActivity(), UsersModel.class) {
            //该方法一般不需要重写，BaseHttpHandler会处理，这里只是示例，强制返回true，代表业务接口的状态码success
            @Override
            public boolean onMatchAppCode(ReqInfo reqInfo, RespInfo respInfo, UsersModel resultBean) {
                return true;
            }

            //该方法一般不需要重写，BaseHttpHandler的子类里会处理解析，这里只是示例，解析失败返回null则会回调onSuccessButParseWrong
            @Override
            public UsersModel onParse(ReqInfo reqInfo, RespInfo respInfo, InputStream inputStream, long totalSize) {
                return new UsersModel();
            }
        });

        //文件下载
        MyHttp.Async.post(ip + "android/123.txt", null, new MyFileHttpHandler(MyFile.getFileInAppDir(getContext(), "file1")) {
            @Override
            public void onSuccess(ReqInfo reqInfo, RespInfo respInfo, File resultBean) {
                super.onSuccess(reqInfo, respInfo, resultBean);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Logger.shortToast("下载完成");
                    }
                });
            }
        });

        //多文件上传 进度监听
        HashMap<String, Object> map = new HashMap<>();
        File file1 = MyFile.getFileInAppDir(getActivity(), "testfileaaaaaa");
        File file2 = MyFile.getFileInAppDir(getActivity(), "testfilebbbbbb");
        IoUtil.bytes2File("1234567890ABCDEFGH".

                getBytes(), file1);
        IoUtil.bytes2File("0987654321CBA ".

                getBytes(), file2);
        map.put("key1", "value1");
        map.put("file", file1);
        map.put("file2", file2);
        map.put("file3", null);
        MyHttp.Async.post(ip + "UploadServlet", map, new MyBaseHttpHandler() {
            @Override
            public void onUploadProgress(long bytesWritten, long totalSize) {
                super.onUploadProgress(bytesWritten, totalSize);
                Logger.i(bytesWritten + "--" + totalSize);
            }

            @Override
            public Object onParse(ReqInfo reqInfo, RespInfo respInfo, InputStream inputStream, long totalSize) {
                return null;
            }
        });
    }


}
