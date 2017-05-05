package com.wzq.duorou.beans;

/**
 * Created by ggyuer on 2017/5/5.
 */

public class TopicUser {

    private String avatar;
    private String nick;
    private String content;

    public TopicUser(String avatar, String nick, String content) {
        this.avatar = avatar;
        this.nick = nick;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
