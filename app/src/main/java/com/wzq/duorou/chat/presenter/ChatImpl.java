package com.wzq.duorou.chat.presenter;


import com.wzq.duorou.MyHelper;
import com.wzq.duorou.chat.view.IChatView;

/**
 * Created by weizhenqing on 2017/4/11.
 */

public class ChatImpl implements ChatPresenter {

    private IChatView iChatView;
    private boolean flag;

    public ChatImpl(IChatView iChatView) {
        this.iChatView = iChatView;
    }

    @Override
    public void isShowDisturb(String toChatUserName) {
        flag = MyHelper.getInstance().getDisturbList().containsKey(toChatUserName);
        iChatView.isShowDisturb(flag);
    }
}
