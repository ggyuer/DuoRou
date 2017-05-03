package com.wzq.duorou.chat.presenter;


import com.wzq.duorou.beans.TopUser;

/**
 * Created by weizhenqing on 2017/4/11.
 */

public interface ConversationPresenter {

    /**
     * 保存会话置顶
     * @param topUser
     */
    void saveTopConversation(TopUser topUser);

    /**
     * 取消会话置顶
     * @param extField
     */
    void delTopConversation(String extField);

    /**
     * 删除会话
     * @param userName
     * @param isDelRecord
     */
    void delConversation(String userName, boolean isDelRecord);

}
