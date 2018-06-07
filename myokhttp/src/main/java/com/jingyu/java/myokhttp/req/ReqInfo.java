package com.jingyu.java.myokhttp.req;

import com.jingyu.java.myokhttp.HttpConstants;
import com.jingyu.java.mytool.bean.CloneBean;
import com.jingyu.java.mytool.util.CollectionUtil;
import com.jingyu.java.mytool.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fengjingyu@foxmail.com
 * http请求的信息
 */
public class ReqInfo extends CloneBean {
    /**
     * POST  GET
     */
    private final ReqType reqType;
    /**
     * 接口地址
     */
    private final String url;
    /**
     * http的请求头
     */
    private final Map<String, List<String>> headersMap;
    /**
     * url?key=value&key1=value1
     */
    private final Map<String, Object> queryMap;
    /**
     * 请求体的内容 key=value(含file)
     */
    private final Map<String, Object> bodyMap;
    /**
     * 请求体的内容
     */
    private final String bodyContent;
    /**
     * 如"application/json"，"text/plain;charset=utf-8"
     */
    private final String contentType;
    /**
     * 标识
     */
    private final Object tag;

    private ReqInfo(Builder builder) {
        this.reqType = builder.reqType;
        this.url = builder.url;
        this.headersMap = builder.headersMap;
        this.queryMap = builder.queryMap;
        this.bodyMap = builder.bodyMap;
        this.bodyContent = builder.bodyContent;
        this.contentType = builder.contentType;
        this.tag = builder.tag;

    }

    public ReqType getReqType() {
        return reqType;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, List<String>> getHeadersMap() {
        return headersMap;
    }

    public Map<String, Object> getQueryMap() {
        return queryMap;
    }

    public Map<String, Object> getBodyMap() {
        return bodyMap;
    }

    public String getContentType() {
        return contentType;
    }

    public String getBodyContent() {
        return bodyContent;
    }

    public Object getTag() {
        return tag;
    }

    public boolean isGet() {
        return getReqType() == ReqType.GET;
    }

    public boolean isPost() {
        return getReqType() == ReqType.POST;
    }

    private static final String AND = "&";

    private static final String EQUAL = "=";

    private static final String QUESTION = "?";

    /**
     * 构建url的参数串
     *
     * @return ?abc=123
     */
    public String buildUrlQuery(Map<String, Object> queryMap) {

        if (CollectionUtil.isNotEmpty(queryMap)) {
            StringBuilder sb = new StringBuilder();

            sb.append(QUESTION);

            for (Map.Entry<String, Object> entry : queryMap.entrySet()) {
                if (entry.getValue() == null) {
                    continue;
                }
                sb.append(entry.getKey() + EQUAL + entry.getValue() + AND);
            }

            return StringUtil.subStringBeforeLastStr(sb.toString(), AND);

        } else {
            // 无参数
            return "";
        }
    }

    @Override
    public String toString() {
        return "ReqInfo{" +
                "reqType=" + reqType +
                ", url='" + url + '\'' +
                ", headersMap=" + headersMap +
                ", queryMap=" + queryMap +
                ", bodyMap=" + bodyMap +
                ", bodyContent='" + bodyContent + '\'' +
                ", contentType='" + contentType + '\'' +
                ", tag=" + tag +
                '}';
    }

    //---------------------------------------------------------------------------------------------------------
    public static class Builder {
        /**
         * POST  GET
         */
        private ReqType reqType;
        /**
         * 接口地址
         */
        private String url;
        /**
         * http的请求头
         */
        private Map<String, List<String>> headersMap;
        /**
         * url?key=value&key1=value1
         */
        private Map<String, Object> queryMap;
        /**
         * 请求体内容 key=value(含file)
         */
        private Map<String, Object> bodyMap;
        /**
         * 请求体内容
         */
        private String bodyContent;
        /**
         * "application/json"，"text/plain;charset=utf-8"
         */
        private String contentType;
        /**
         * 标识
         */
        private Object tag;

        public Builder() {
        }

        public Builder(ReqInfo reqInfo) {
            this.reqType = reqInfo.getReqType();
            this.url = reqInfo.getUrl();
            this.headersMap = reqInfo.getHeadersMap();
            this.queryMap = reqInfo.getQueryMap();
            this.bodyMap = reqInfo.getBodyMap();
            this.bodyContent = reqInfo.getBodyContent();
            this.contentType = reqInfo.getContentType();
            this.tag = reqInfo.getTag();
        }

        public Builder myReqType(ReqType reqType) {
            this.reqType = reqType;
            return this;
        }

        public Builder get() {
            this.reqType = ReqType.GET;
            return this;
        }

        public Builder post() {
            this.reqType = ReqType.POST;
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

        public Builder queryMap(Map<String, Object> queryMap) {
            this.queryMap = queryMap;
            return this;
        }

        public Builder bodyMap(Map<String, Object> bodyMap) {
            this.bodyMap = bodyMap;
            return this;
        }

        public Builder bodyContent(String bodyContent) {
            this.bodyContent = bodyContent;
            return this;
        }

        public Builder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder contentTypeJson() {
            this.contentType = HttpConstants.JSON;
            return this;
        }

        public Builder contentTypeForm() {
            this.contentType = HttpConstants.FORM;
            return this;
        }

        public Builder contentTypeText() {
            this.contentType = HttpConstants.TEXT;
            return this;
        }

        public Builder tag(Object tag) {
            this.tag = tag;
            return this;
        }

        public ReqInfo builder() {
            if (reqType == null) {
                reqType = ReqType.GET;
            }

            if (url == null) {
                url = "";
            }

            if (bodyContent == null) {
                bodyContent = "";
            }

            if (contentType == null) {
                contentType = "";
            }

            if (headersMap == null) {
                headersMap = new HashMap<>();
            }

            if (queryMap == null) {
                queryMap = new HashMap<>();
            }

            if (bodyMap == null) {
                bodyMap = new HashMap<>();
            }

            if (tag == null) {
                tag = "";
            }

            return new ReqInfo(this);
        }
    }
}