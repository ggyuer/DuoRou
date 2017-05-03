package com.wzq.duorou.chat.model;

import android.annotation.SuppressLint;


import com.wzq.duorou.beans.TopUser;

import java.util.List;
import java.util.Map;

public interface TopUserDao {

    public static final String TABLE_NAME = "top_user";
    public static final String COLUMN_NAME_ID = "username";
    public static final String COLUMN_NAME_TIME = "time";
    public static final String COLUMN_NAME_IS_GOUP = "is_group";



    /**
     * 保存好友list
     *
     * @param contactList
     */
    public void saveTopUserList(List<TopUser> contactList) ;

    /**
     * 获取会话置顶所有好友的消息
     *
     * @return
     */
    @SuppressLint("DefaultLocale")
    public Map<String, TopUser> getTopUserList();

    /**
     * 删除一个联系人
     *
     * @param username
     */
    public int deleteTopUser(String username);

    /**
     * 保存一个联系人
     *
     * @param user
     */
    public long saveTopUser(TopUser user) ;
}
