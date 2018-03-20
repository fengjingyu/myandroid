package com.jingyu.app.model;

import com.jingyu.java.mytool.bean.CloneBean;

/**
 * @author fengjingyu@foxmail.com
 *         根据model自动生成db的较常用方法
 */
public class User extends CloneBean {

    /**
     * 静态字段不会生成在数据库中
     */
    private static final long serialVersionUID = 7806487539561621886L;

    /**
     * 含有ignore注解的字段不会生成在数据库中
     */
    //@Ignore 这里未引入库
    //String testIgnore = "";

    /**
     * 身份证（该字段的注释会生成在db中）
     */
    String uniqueId = "";
    /**
     * 名字（该字段的注释会生成在db中）
     */
    String name = "";
    /**
     * 性别 0 男 ，1 女（该字段的注释会生成在db中）
     */
    String gender = "";
    /**
     * 年龄（该字段的注释会生成在db中）
     */
    String age = "";
    /**
     * 得分（该字段的注释会生成在db中）
     */
    String score = "";
    /**
     * 爱好（该字段的注释会生成在db中）
     */
    String hobby = "";

    public User() {
    }

    public User(String uniqueId, String name, String gender, String age, String score, String hobby) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.score = score;
        this.hobby = hobby;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    @Override
    public String toString() {
        return "Model{" +
                "uniqueId='" + uniqueId + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age='" + age + '\'' +
                ", score='" + score + '\'' +
                ", hobby='" + hobby + '\'' +
                '}';
    }
}

