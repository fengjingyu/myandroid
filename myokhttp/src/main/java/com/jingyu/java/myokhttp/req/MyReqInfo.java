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
     * 如"application/json"，"text/plain;charset=utf-8"
     */
    private final String contentType;
    /**
     * 请求体的内容
     */
    private final String content;
    /**
     * 标识
     */
    private final Object tag;

    private MyReqInfo(Builder builder) {
        this.myReqType = builder.myReqType;
        this.url = builder.url;
        this.headersMap = builder.headersMap;
        this.paramsMap = builder.paramsMap;
        this.contentType = builder.contentType;
        this.content = builder.content;
        this.tag = builder.tag;
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

    public String getContentType() {
        return contentType;
    }

    public String getContent() {
        return content;
    }

    public Object getTag() {
        return tag;
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
     * 构建url的参数串
     *
     * @return ?abc=123
     */
    public String buildUrlParams(Map<String, Object> params) {

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
                ", contentType='" + contentType + '\'' +
                ", content='" + content + '\'' +
                ", tag=" + tag +
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
        private String contentType;
        /**
         * postString的内容
         */
        private String content;
        /**
         * 标识
         */
        private Object tag;

        public Builder() {
        }

        public Builder(MyReqInfo myReqInfo) {
            this.myReqType = myReqInfo.getMyReqType();
            this.url = myReqInfo.getUrl();
            this.headersMap = myReqInfo.getHeadersMap();
            this.paramsMap = myReqInfo.getParamsMap();
            this.contentType = myReqInfo.getContentType();
            this.content = myReqInfo.getContent();
            this.tag = myReqInfo.getTag();
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
            this.contentType = postStringContentType;
            return this;
        }

        public Builder postString(String postString) {
            this.content = postString;
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

            if (content == null) {
                content = "";
            }

            if (contentType == null) {
                contentType = "";
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