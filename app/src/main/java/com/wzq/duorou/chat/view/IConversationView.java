package com.wzq.duorou.chat.view;

/**
 * Created by weizhenqing on 2017/4/11.
 */

public interface IConversationView {

    void saveTopConversation(long code);

    void delTopConversation(int code);

    void delConversation(boolean flag);
}
