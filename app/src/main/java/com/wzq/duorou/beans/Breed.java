package com.wzq.duorou.beans;

/**
 * Created by weizhenqing on 2017/3/29.
 */

public class Breed {

    private int id;
    private String title;
    private String img;
    private String time;
    private int likeCount;
    private String shareFrom;//分享人
    private String location;
    private String httpPath;
    private int reId;

    public int getReId() {
        return reId;
    }

    public void setReId(int reId) {
        this.reId = reId;
    }

    public String getHttpPath() {
        return httpPath;
    }

    public void setHttpPath(String httpPath) {
        this.httpPath = httpPath;
    }

    public Breed(int id, String title, String img, String time, int likeCount, String shareFrom, String location,String httpPath,int reId) {
        this.id = id;
        this.title = title;
        this.img = img;
        this.time = time;
        this.likeCount = likeCount;
        this.shareFrom = shareFrom;
        this.location = location;
        this.httpPath = httpPath;
        this.reId = reId;
    }

    public Breed() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getShareFrom() {
        return shareFrom;
    }

    public void setShareFrom(String shareFrom) {
        this.shareFrom = shareFrom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    @Override
    public String toString() {
        return "Breed{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", time='" + time + '\'' +
                ", likeCount=" + likeCount +
                '}';
    }
}
