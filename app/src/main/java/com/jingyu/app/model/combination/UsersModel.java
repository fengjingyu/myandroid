package com.jingyu.app.model.combination;

import com.jingyu.app.model.User;
import com.jingyu.java.mytool.bean.CloneBean;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingyu on 2018/1/26.
 */

public class UsersModel extends CloneBean {
    String msg = "";
    String code = "";
    List<User> data = new ArrayList<>();

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }
}
