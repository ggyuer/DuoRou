package com.wzq.duorou.chat.presenter;

import com.hyphenate.chat.EMClient;
import com.wzq.duorou.App;
import com.wzq.duorou.MyHelper;
import com.wzq.duorou.beans.Disturb;
import com.wzq.duorou.beans.TopUser;
import com.wzq.duorou.chat.view.ISingleDetailView;

/**
 * Created by weizhenqing on 2017/4/11.
 */

public class SingleImpl implements SinglePresenter{

    private ISingleDetailView iSingleDetailView;
    private long index;
    private boolean flag;

    public SingleImpl(ISingleDetailView iSingleDetailView) {
        this.iSingleDetailView = iSingleDetailView;
    }

    @Override
    public void showIsTopChat(String toChatUserName) {
        flag = App.getInstance().getTopUserList().containsKey(toChatUserName);
        iSingleDetailView.showIsTopChat(flag);
    }

    @Override
    public void showIsDisturb(String toChaUserName) {
        flag = MyHelper.getInstance().getDisturbList().containsKey(toChaUserName);
        iSingleDetailView.showIsDisturb(flag);
    }


    @Override
    public void saveTopConversation(String toChatUserName) {
        TopUser user = new TopUser();
        user.setUserName(toChatUserName);
        user.setTime(System.currentTimeMillis());
        index =  App.getInstance().setTopUser(user);
        iSingleDetailView.saveTopConversation(index);
    }

    @Override
    public void delTopConversation(String toChatUserName) {
        index = App.getInstance().deleteTopUser(toChatUserName);
        iSingleDetailView.delTopConversation(index);
    }

    @Override
    public void saveDisturb(String toChatUserName) {
        Disturb disturb = new Disturb();
        disturb.setUserId(toChatUserName);
        index = MyHelper.getInstance().saveDisturb(disturb);
        iSingleDetailView.saveDisturb(index);
    }

    @Override
    public void delDisturb(String toChatUserName) {
        index = MyHelper.getInstance().deleteDisturb(toChatUserName);
        iSingleDetailView.delDisturb(index);
    }

    @Override
    public void clearRecord(String toChatUserName) {
        flag = EMClient.getInstance().chatManager().deleteConversation(toChatUserName, true);
        iSingleDetailView.clearRecord(flag);
    }
}
