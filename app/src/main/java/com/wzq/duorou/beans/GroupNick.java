package com.wzq.duorou.beans;

/**
 * Created by weizhenqing on 2017/4/13.
 * 用户在群中的昵称
 */

public class GroupNick {

    private String userId;
    private String groupId;
    private String userNick;
    private String groupNick;

    public GroupNick(String userId, String groupId, String userNick, String groupNick) {
        this.userId = userId;
        this.groupId = groupId;
        this.userNick = userNick;
        this.groupNick = groupNick;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getGroupNick() {
        return groupNick;
    }

    public void setGroupNick(String groupNick) {
        this.groupNick = groupNick;
    }

    @Override
    public String toString() {
        return "ShowNick{" +
                "userId='" + userId + '\'' +
                ", groupId='" + groupId + '\'' +
                ", userNick='" + userNick + '\'' +
                ", groupNick='" + groupNick + '\'' +
                '}';
    }
}
