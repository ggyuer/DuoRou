package com.wzq.duorou.beans;

/**
 * Created by weizhenqing on 2017/4/2.
 */

public class News {

    private int id;
    private String imgUrl;
    private String title;
    private String ping;
    private String time;
    private String who;
    private String location;
    private String httpPath;

    public String getHttpPath() {
        return httpPath;
    }

    public void setHttpPath(String httpPath) {
        this.httpPath = httpPath;
    }

    public News(int id, String imgUrl, String title, String ping, String time, String who, String location) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.title = title;
        this.ping = ping;
        this.time = time;
        this.who = who;

    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public News(int id, String imgUrl, String title, String ping, String who,String httpPath) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.title = title;
        this.ping = ping;
        this.who = who;
        this.httpPath = httpPath;

    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getPing() {
        return ping;
    }

    public void setPing(String ping) {
        this.ping = ping;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
