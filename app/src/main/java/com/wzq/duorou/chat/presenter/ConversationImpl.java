package com.wzq.duorou.chat.presenter;

import android.content.Context;

import com.hyphenate.chat.EMClient;
import com.wzq.duorou.App;
import com.wzq.duorou.beans.TopUser;
import com.wzq.duorou.chat.impl.TopUserDaoImpl;
import com.wzq.duorou.chat.model.InviteMessgeDao;
import com.wzq.duorou.chat.model.TopUserDao;
import com.wzq.duorou.chat.view.IConversationView;

/**
 * Created by weizhenqing on 2017/4/11.
 */

public class ConversationImpl implements ConversationPresenter {

    private IConversationView iConversationView;
    private TopUserDao topUserDao;
    private Context context;
    private boolean flag;
    public ConversationImpl(Context context, IConversationView iConversationView) {
        topUserDao = new TopUserDaoImpl(context);
        this.context = context;
        this.iConversationView = iConversationView;
    }

    @Override
    public void saveTopConversation(TopUser topUser) {
        long j = App.getInstance().setTopUser(topUser);
        iConversationView.saveTopConversation(j);
    }

    @Override
    public void delTopConversation(String extField) {
        int j = App.getInstance().deleteTopUser(extField);
        iConversationView.delTopConversation(j);
    }

    @Override
    public void delConversation(String userName, boolean isDelRecord) {
        flag = EMClient.getInstance().chatManager().deleteConversation(userName, isDelRecord);
        if (flag){
            InviteMessgeDao inviteMessgeDao = new InviteMessgeDao(context);
            inviteMessgeDao.deleteMessage(userName);
        }
        iConversationView.delConversation(flag);
    }
}
