package com.jingyu.app;

/**
 * @author jy
 * @date 2018/3/29 13:54
 * @desc
 */
public class TestBean {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static void main(String[] args) {
        System.out.println(new TestBean().getId());
    }

}
