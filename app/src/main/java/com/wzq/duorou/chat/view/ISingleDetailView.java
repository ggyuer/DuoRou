package com.wzq.duorou.chat.view;

/**
 * Created by weizhenqing on 2017/4/11.
 */

public interface ISingleDetailView {

    void showIsTopChat(boolean flag);

    void showIsDisturb(boolean flag);

    void saveTopConversation(long code);

    void delTopConversation(long code);

    void saveDisturb(long code);

    void delDisturb(long code);

    void clearRecord(boolean flag);
}
