package com.wzq.duorou.chat.model;


import com.wzq.duorou.beans.GroupNick;

import java.util.Map;

/**
 * Created by weizhenqing on 2017/4/13.
 */

public interface GroupNickDao {

    String TABLE_NAME = "showNick";
    String SHOW_USER_ID = "userId";
    String SHOW_GROUP_ID = "groupId";
    String SHOW_USER_NICK = "userNick";
    String SHOW_GROUP_NICK = "groupNick";

    String SHOW_NICK_ACTION = "show_nick_action";

    long saveShowNick(GroupNick nick);

    String queryShowNick(String userId, String groupId);

    Map<String,String> queryAllShowNick();
}
