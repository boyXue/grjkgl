package com.example.zxdc.pojo;

import java.io.Serializable;

public class jbxx implements Serializable {
    String id;
    String title;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    String state;

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    String pics;
    String uid;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    String time;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    String uname;
    String msg;
    String mdd;
    String jbsj;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    String head;

    public jbxx() {
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMdd() {
        return mdd;
    }

    public void setMdd(String mdd) {
        this.mdd = mdd;
    }

    public String getJbsj() {
        return jbsj;
    }

    public void setJbsj(String jbsj) {
        this.jbsj = jbsj;
    }
}
