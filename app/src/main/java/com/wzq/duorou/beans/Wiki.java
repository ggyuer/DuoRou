package com.wzq.duorou.beans;

/**
 * Created by weizhenqing on 2017/4/2.
 */

public class Wiki {

    private int id;
    private String title;
    private String url;
    private String local;

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Wiki(int id, String title, String url,String local) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.local = local;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
