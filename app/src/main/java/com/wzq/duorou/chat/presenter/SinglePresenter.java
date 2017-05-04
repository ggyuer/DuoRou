package com.wzq.duorou.chat.presenter;

/**
 * Created by weizhenqing on 2017/4/11.
 */

public interface SinglePresenter {


    /**
     * 聊天否是置顶
     * @param toChatUserName
     */
    void showIsTopChat(String toChatUserName);

    /**
     * 是否免打扰
     * @param toChaUserName
     */
    void showIsDisturb(String toChaUserName);

    /**
     * 会话置顶
     * @param toChatUserName
     */
    void saveTopConversation(String toChatUserName);

    /**
     * 取消会话置顶
     * @param toChatUserName
     */
    void delTopConversation(String toChatUserName);

    /**
     * 消息免打扰
     * @param toChatUserName
     */
    void saveDisturb(String toChatUserName);

    /**
     * 删除消息免打扰
     * @param toChatUserName
     */
    void delDisturb(String toChatUserName);

    /**
     * 清空聊天记录
     * @param toChatUserName
     */
    void clearRecord(String toChatUserName);


}
