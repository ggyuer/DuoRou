package com.wzq.duorou.beans;

import java.io.Serializable;

/**
 * Created by mbcore on 2017/1/8.
 */

public class UserBean implements Serializable {
    /**
     * id :
     * name : 昵称
     * avatar : 头像
     * gender : 性别
     * account : 艺盟号
     * qrcode : 二维码
     * signature : 个性签名
     * password : 密码 0 未设置密码  1 已经设置了密码
     * api_token : 令牌口令
     * huanxin_user : 环信用户名
     * huanxin_pass : 环信密码
     * huanxin_uid : 环信id
     * created_at : 注册时间
     * updated_at : 更新时间
     * deleted_at : 删除时间
     */

    private String nickName;
    private String id;
    private String name;
    private String avatar;
    private int gender;
    private String account;
    private String qrcode;
    private String signature;
    private int password;
    private String api_token;
    private String huanxin_user;
    private String huanxin_pass;
    private String huanxin_uid;
    private String created_at;
    private String updated_at;
    private Object deleted_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public String getHuanxin_user() {
        return huanxin_user;
    }

    public void setHuanxin_user(String huanxin_user) {
        this.huanxin_user = huanxin_user;
    }

    public String getHuanxin_pass() {
        return huanxin_pass;
    }

    public void setHuanxin_pass(String huanxin_pass) {
        this.huanxin_pass = huanxin_pass;
    }

    public String getHuanxin_uid() {
        return huanxin_uid;
    }

    public void setHuanxin_uid(String huanxin_uid) {
        this.huanxin_uid = huanxin_uid;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Object getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(Object deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}

