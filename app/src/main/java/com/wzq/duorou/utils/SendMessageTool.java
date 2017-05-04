package com.wzq.duorou.utils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.wzq.duorou.Constant;
import com.wzq.duorou.chat.model.GroupNickDao;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by weizhenqing on 2017/3/7.
 */

public class SendMessageTool {

    public static boolean sendNameChangeMessage(String toUserName, String name) {
//        EMMessage message = EMMessage.createSendMessage(EMMessage.Type.CMD);
//        message.setChatType(EMMessage.ChatType.GroupChat);
//        EMCmdMessageBody body = new EMCmdMessageBody(GroupDao.GROUP_CMD_ACTION_NAME_CHANGE);
//        message.setTo(toUserName);
//        message.setAttribute(GroupDao.GROUP_ID, toUserName);
//        message.setAttribute(GroupDao.COLUMN_GROUP_NAME, name);
//        message.addBody(body);
//        EMClient.getInstance().chatManager().sendMessage(message);

        return false;
    }

    /**
     * 发送群昵称变化消息
     *
     * @param content
     * @param groupId
     */
    public static void sendCmdShowNickMessage(String content,String defaultNick, String groupId) {

        EMMessage message = EMMessage.createSendMessage(EMMessage.Type.CMD);
        message.setChatType(EMMessage.ChatType.GroupChat);
        EMCmdMessageBody body = new EMCmdMessageBody(GroupNickDao.SHOW_NICK_ACTION);
        message.setTo(groupId);
        message.setAttribute(Constant.GROUP_USERNAME, groupId);
        message.setAttribute(GroupNickDao.SHOW_GROUP_NICK, content);
        message.setAttribute(Constant.USER_NICK_NAME, defaultNick);
        message.addBody(body);
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    /**
     * 发送好友消息
     */
    public static void sendCmdNewFriendAcceptMessage(String userId) {

        EMMessage message = EMMessage.createSendMessage(EMMessage.Type.CMD);
        message.setChatType(EMMessage.ChatType.Chat);
        EMCmdMessageBody body = new EMCmdMessageBody("a");
        message.setTo(userId);
        String mineAvatar = PreferenceManager.getInstance().getValueFromPreferences(Constant.USER_AVATAR, null);
        String mineNick = PreferenceManager.getInstance().getValueFromPreferences(Constant.USER_NICK_NAME, null);
        message.setAttribute("avatar", mineAvatar);
        message.setAttribute("nickName", mineNick);
//        message.setAttribute("text", "我们已经是好友了");
        message.addBody(body);
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    /**
     * 向所有人发送公告消息
     */
    public static void sendTextNoticeMessage(ArrayList<String> m, String content, String groupId) {

        JSONArray atJson = new JSONArray(m);
        EMMessage message = EMMessage.createTxtSendMessage("@所有成员" +
                "\n公告：" + content, groupId);
        message.setChatType(EMMessage.ChatType.GroupChat);
        String avatar = PreferenceManager.getInstance().getValueFromPreferences(Constant.USER_AVATAR, "");
        String nickName = PreferenceManager.getInstance().getValueFromPreferences(Constant.USER_NICK_NAME, "");
        message.setAttribute(Constant.USER_AVATAR, avatar);
        message.setAttribute(Constant.USER_NICK_NAME, nickName);
        message.setAttribute(Constant.AT_LIST, atJson);
        message.setAttribute(Constant.NOTICE_CONTENT, content);
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    public static void sendGroupHaveNewUserNotice(EMMessage message) {

        String groupId = (String) message.ext().get("groupId");
        String texts = (String) message.ext().get("text");
        String fromUser = message.ext().get("fromUser").toString().toLowerCase();
        String currentUser = PreferenceManager.getInstance().getValueFromPreferences(Constant.USER_HX_ID, "");
        if (fromUser.equals(currentUser)) {
            EMMessage cmdMsg = EMMessage.createTxtSendMessage(texts, groupId);
            cmdMsg.setChatType(EMMessage.ChatType.GroupChat);
            //告诉透传消息的接收者，如果GROUP_MESSAGE_TO_ALL 这个属性为true 那么接收这就发送一条 群邀请信息
            cmdMsg.setAttribute(Constant.GROUP_HAVE_NEW_USER_NOTIVE, true);
            cmdMsg.setAttribute(Constant.GROUP_INVITE_CONTENT, texts); //消息体
            //String sender = PreferenceManager.getInstance().getValueFromPreferences(Constant.USER_HX_ID, "");
            cmdMsg.setAttribute(Constant.GROUP_INVITE_MESSAGE_SENDER, currentUser); //发送者
            cmdMsg.setAttribute(Constant.GROUP_INVITE_MESSAGE_RECEIVE_GROUP, groupId); //接收的群
            String nickName = PreferenceManager.getInstance().getValueFromPreferences(Constant.USER_NICK_NAME, "");
            String avatar = PreferenceManager.getInstance().getValueFromPreferences(Constant.USER_AVATAR, "");
            cmdMsg.setAttribute(Constant.USER_NICK_NAME, nickName);
            cmdMsg.setAttribute(Constant.USER_AVATAR, avatar);

            EMClient.getInstance().chatManager().sendMessage(cmdMsg);
        }
    }
}
