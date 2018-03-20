package com.jingyu.android.common.exception;

import java.io.Serializable;

/**
 * @author  fengjingyu@foxmail.com
 */
public class ExceptionInfo implements Serializable {

    private static final long serialVersionUID = 7806487539561621206L;

    /**
     * 异常的详细信息
     */
    private String info = "";
    /**
     * 异常发生的时间
     */
    private String exceptionTime = "";
    /**
     * 是否上传成功 0 UPLOAD_NO 为没上传或上传失败，1 UPLOAD_YES 为上传成功
     */
    private String uploadSuccess = "";
    /**
     * 哪一个用户，这个字段需要项目中去设置（可选）
     */
    private String userId = "";
    /**
     * 唯一标识 = exceptionTime + "_" + uuid
     */
    private String uniqueId = "";

    public static final String UPLOAD_YES = "1";

    public static final String UPLOAD_NO = "0";

    @Override
    public String toString() {
        return "ExceptionInfo{" +
                "info='" + info + '\'' +
                ", exceptionTime='" + exceptionTime + '\'' +
                ", uploadSuccess='" + uploadSuccess + '\'' +
                ", userId='" + userId + '\'' +
                ", uniqueId='" + uniqueId + '\'' +
                '}';
    }

    public ExceptionInfo() {
    }

    public ExceptionInfo(String info, String exceptionTime, String uploadSuccess, String userId, String uniqueId) {
        this.info = info;
        this.exceptionTime = exceptionTime;
        this.uploadSuccess = uploadSuccess;
        this.userId = userId;
        this.uniqueId = uniqueId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getExceptionTime() {
        return exceptionTime;
    }

    public void setExceptionTime(String exceptionTime) {
        this.exceptionTime = exceptionTime;
    }

    public String getUploadSuccess() {
        return uploadSuccess;
    }

    public void setUploadSuccess(String uploadSuccess) {
        this.uploadSuccess = uploadSuccess;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
}
