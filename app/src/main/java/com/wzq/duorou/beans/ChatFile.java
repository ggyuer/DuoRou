package com.wzq.duorou.beans;

import java.io.Serializable;

/**
 * Created by weizhenqing on 2017/3/14.
 */

public class ChatFile implements Serializable {

    private boolean isChecked;//是否被选中

    private int row_id;


    private String userId;// 群还是个人的username
    private String chatType;//聊天类型
    private String fileType;//聊天文件的类型 图片 视频  网址 文件
    private String path;//文件的路径
    private String thumbPath;//thumb 路径
    private String remotePath;//远程 路径
    private String time;
    private String duration;//视频的持续时间
    private String messageId;//如果本地没有直接网络获取
    private String secret;//如果本地没有直接网络获取

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }


    public int getRow_id() {
        return row_id;
    }

    public void setRow_id(int row_id) {
        this.row_id = row_id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType;
    }

    public String getRemotePath() {
        return remotePath;
    }

    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
    }

    @Override
    public String toString() {
        return "ChatFile{" +
                "isChecked=" + isChecked +
                ", row_id=" + row_id +
                ", userId='" + userId + '\'' +
                ", chatType='" + chatType + '\'' +
                ", fileType='" + fileType + '\'' +
                ", path='" + path + '\'' +
                ", thumbPath='" + thumbPath + '\'' +
                ", remotePath='" + remotePath + '\'' +
                ", time='" + time + '\'' +
                ", duration='" + duration + '\'' +
                ", messageId='" + messageId + '\'' +
                ", secret='" + secret + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof ChatFile) {
            ChatFile chatFile = (ChatFile) obj;
//            if (chatFile.userId.equals(this.userId) && chatFile.chatType.equals(this.chatType) && chatFile.fileType.equals(this.fileType)
//                    && chatFile.path.equals(this.path) && chatFile.thumbPath.equals(this.thumbPath) && chatFile.remotePath.equals(this.remotePath)
//                    && chatFile.time.equals(this.time) && chatFile.duration.equals(this.duration) && chatFile.messageId.equals(this.messageId)
//                    && chatFile.secret.equals(this.secret)) {
            if (chatFile.row_id == this.row_id) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }


}
