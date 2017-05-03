package com.wzq.duorou.beans;

/**
 * Created by weizhenqing on 2017/4/2.
 */

public class Topic {

    private int id;
    private String topicName;
    private String content;
    private String join;
    private String url;
    private int reId;


    public int getReId() {
        return reId;
    }

    public void setReId(int reId) {
        this.reId = reId;
    }

    public Topic(int id, String topicName, String content, String join, int reId) {
        this.id = id;
        this.topicName = topicName;
        this.content = content;
        this.join = join;
        this.reId = reId;
    }

    public Topic(int id, String topicName, String content, String join, String url) {
        this.id = id;
        this.topicName = topicName;
        this.content = content;
        this.join = join;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getJoin() {
        return join;
    }

    public void setJoin(String join) {
        this.join = join;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
