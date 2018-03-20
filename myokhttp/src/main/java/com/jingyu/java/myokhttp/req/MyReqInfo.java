package com.jingyu.java.myokhttp.req;

import com.jingyu.java.mytool.bean.CloneBean;
import com.jingyu.java.mytool.util.CollectionsUtil;
import com.jingyu.java.mytool.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fengjingyu@foxmail.com
 *         http请求的信息
 */
public class MyReqInfo extends CloneBean {
    /**
     * POST  GET
     */
    private final MyReqType myReqType;
    /**
     * 接口地址
     */
    private final String url;
    /**
     * http的请求头
     */
    private final Map<String, List<String>> headersMap;
    /**
     * http的请求参数
     */
    private final Map<String, Object> paramsMap;
    /**
     * postString的ContentType, 如"application/json"，"text/plain;charset=utf-8"
     */
    private final String postStringContentType;
    /**
     * postString的内容
     */
    private final String postString;
    /**
     * 标识
     */
    private final Object tag;
    /**
     * 是否是下载
     */
    private final boolean isDownload;

    private MyReqInfo(Builder builder) {
        this.myReqType = builder.myReqType;
        this.url = builder.url;
        this.headersMap = builder.headersMap;
        this.paramsMap = builder.paramsMap;
        this.postStringContentType = builder.postStringContentType;
        this.postString = builder.postString;
        this.tag = builder.tag;
        this.isDownload = builder.isDownload;
    }

    public MyReqType getMyReqType() {
        return myReqType;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, List<String>> getHeadersMap() {
        return headersMap;
    }

    public Map<String, Object> getParamsMap() {
        return paramsMap;
    }

    public String getPostStringContentType() {
        return postStringContentType;
    }

    public String getPostString() {
        return postString;
    }

    public Object getTag() {
        return tag;
    }

    public boolean isDownload() {
        return isDownload;
    }

    public boolean isGet() {
        return getMyReqType() == MyReqType.GET;
    }

    public boolean isPost() {
        return getMyReqType() == MyReqType.POST;
    }

    private static final String AND = "&";

    private static final String EQUAL = "=";

    private static final String QUESTION = "?";

    /**
     * 构建get方式的参数串（未拼接ur）
     *
     * @return ?abc=123
     */
    public String buildGetParams(Map<String, Object> params) {

        if (CollectionsUtil.isMapAvaliable(params)) {
            StringBuilder sb = new StringBuilder();

            sb.append(QUESTION);

            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() == null) {
                    continue;
                }
                sb.append(entry.getKey() + EQUAL + entry.getValue() + AND);
            }

            return StringUtil.subStringBeforeLastSimbol(sb.toString(), AND);

        } else {
            // 无参数
            return "";
        }
    }

    @Override
    public String toString() {
        return "MyReqInfo{" +
                "myReqType=" + myReqType +
                ", url='" + url + '\'' +
                ", headersMap=" + headersMap +
                ", paramsMap=" + paramsMap +
                ", postStringContentType='" + postStringContentType + '\'' +
                ", postString='" + postString + '\'' +
                ", tag=" + tag +
                ", isDownload=" + isDownload +
                '}';
    }

    //---------------------------------------------------------------------------------------------------------
    public static class Builder {
        /**
         * POST  GET
         */
        private MyReqType myReqType;
        /**
         * 接口地址
         */
        private String url;
        /**
         * http的请求头
         */
        private Map<String, List<String>> headersMap;
        /**
         * http的请求参数
         */
        private Map<String, Object> paramsMap;
        /**
         * postString的ContentType, 如"application/json"，"text/plain;charset=utf-8"
         */
        private String postStringContentType;
        /**
         * postString的内容
         */
        private String postString;
        /**
         * 标识
         */
        private Object tag;
        /**
         * 是否是下载
         */
        private boolean isDownload;

        public Builder() {
        }

        public Builder(MyReqInfo myReqInfo) {
            this.myReqType = myReqInfo.getMyReqType();
            this.url = myReqInfo.getUrl();
            this.headersMap = myReqInfo.getHeadersMap();
            this.paramsMap = myReqInfo.getParamsMap();
            this.postStringContentType = myReqInfo.getPostStringContentType();
            this.postString = myReqInfo.getPostString();
            this.tag = myReqInfo.getTag();
            this.isDownload = myReqInfo.isDownload();
        }

        public Builder myReqType(MyReqType myReqType) {
            this.myReqType = myReqType;
            return this;
        }

        public Builder get() {
            this.myReqType = MyReqType.GET;
            return this;
        }

        public Builder post() {
            this.myReqType = MyReqType.POST;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder headersMap(Map<String, List<String>> headersMap) {
            this.headersMap = headersMap;
            return this;
        }

        public Builder paramsMap(Map<String, Object> paramsMap) {
            this.paramsMap = paramsMap;
            return this;
        }

        public Builder postStringContentType(String postStringContentType) {
            this.postStringContentType = postStringContentType;
            return this;
        }

        public Builder postString(String postString) {
            this.postString = postString;
            return this;
        }

        public Builder isDownload(boolean isDownload) {
            this.isDownload = isDownload;
            return this;
        }

        public Builder tag(Object tag) {
            this.tag = tag;
            return this;
        }

        public MyReqInfo builder() {
            if (myReqType == null) {
                myReqType = MyReqType.GET;
            }

            if (url == null) {
                url = "";
            }

            if (postString == null) {
                postString = "";
            }

            if (postStringContentType == null) {
                postStringContentType = "";
            }

            if (tag == null) {
                tag = "";
            }

            if (headersMap == null) {
                headersMap = new HashMap<>();
            }

            if (paramsMap == null) {
                paramsMap = new HashMap<>();
            }

            return new MyReqInfo(this);
        }
    }
}