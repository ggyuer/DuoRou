package com.wzq.duorou;

import com.hyphenate.easeui.EaseConstant;

import java.util.ArrayList;
import java.util.List;


public class Constant extends EaseConstant {
	public static final int LING = 0;
	public static final int YI = 1;
	public static final int ER = 2;
	public static final int SAN = 3;

	public static final String FLAG = "flag";

	public static final String WEICHA_APPID = "wx8c2eff6b728815cc";
	public static final String QQ_APPID = "1105946639";
	public static final String WEIBO_APPID = "4149161742";

	public static final int REQUEST_CODE_CAMERA = 1000;
	public static final int REQUEST_CODE_GALLERY = 1001;
	public static final int REQUEST_CODE_CROP = 1002;
	public static final int REQUEST_CODE_EDIT = 1003;

	public static final String NEW_FRIENDS_USERNAME = "item_new_friends";
	public static final String GROUP_USERNAME = "item_groups";
	public static final String CHAT_ROOM = "item_chatroom";
	public static final String ACCOUNT_REMOVED = "account_removed";
	public static final String ACCOUNT_CONFLICT = "conflict";
	public static final String ACCOUNT_FORBIDDEN = "user_forbidden";
	public static final String CHAT_ROBOT = "item_robots";
	public static final String MESSAGE_ATTR_ROBOT_MSGTYPE = "msgtype";
	public static final String ACTION_GROUP_CHANAGED = "action_group_changed";
	public static final String ACTION_CONTACT_CHANAGED = "action_contact_changed";
	public static final String Head_IMAGE_NAME = "head.png";

	//个人信息
	public static final String USER_HX_ID = "user_hx_id";//环信id
	public static final String USER_HX_PASS = "user_hx_pass";//环信密码
	public static final String USER_NICK_NAME = "nickName";//昵称
	public static final String USER_AVATAR = "avatar";//头像
	public static final String USER_ACCOUNT = "user_account";//艺盟号
	public static final String USER_SIGNATURE = "user_signature";//个性签名
	public static final String USER_GENDER = "user_gender";//性别
	public static final String USER_PERFECT = "user_perfect";//是否补全了用户资料
	public static final String FIRST_LOGIN = "first_login";//首次进入

	public static final String CUSTOMER_ACCOUNT = "mbcore1314";//客服的服务号
	public static final String GROUP_NICK_NAME = "groupNickName";

	/*******************
	 * 群聊透传消息发送
	 ********************************/

	public static final String GROUP_MESSAGE_TO_ALL = "group_message_to_all";// 群邀请消息类型
	public static final String GROUP_INVITE_MESSAGE_ACTION = "group_invite_message_action";//发送透传消息的action
	public static final String GROUP_INVITE_CONTENT = "group_invite_message";
	public static final String GROUP_INVITE_MESSAGE_SENDER = "sender";//消息发送者
	public static final String GROUP_INVITE_MESSAGE_RECEIVE_GROUP = "receive_group";//邀请信息得接收群
	public static final String GROUP_ID = "group_id";// 消息接受群
	public static final String CONTENT = "content";
	public static final String MEMBERS = "members";
	public static final String AT_LIST = "em_at_list";

	/*******************
	 * 支付消息（展示给用户）
	 ********************************/
	public static final String PAY_MESSAGE_TO_USER = "pay_message_to_user";//支付消息类型
	public static final String PAY_MESSAGE_ACTION = "pay_message_action";//支付消息的action
	public static final String PAY_MESSAGE_EXT = "pay_message_ext";//支付消息的详细信息
	public static final String PAY_MONEY = "pay_money";//支付金额
	public static final String BUSINESS = "business";//收款商户

	public static final String TITLE_NAME = "title_name";
	public static final int GROUP_A_REQUEST = 7001;
	public static final int GROUP_A_RESPONSE = 7002;
	public static final int GROUP_N_REQUEST = 7003;
	public static final int GROUP_N_RESPONSE = 7004;

	/*******************
	 * 微信支付相关参数
	 ********************************/

	public static String APP_ID = "wx8c2eff6b728815cc";//appid 微信分配的公众账号ID
	public static String MCH_ID = "1432582602";//商户号 微信分配的公众账号ID
	public static String API_KEY = "10c64515b556a77b5facd05d0c535946";//API密钥，在商户平台设置

	public static ArrayList<String> callStringList(ArrayList<String> list) {
		if (list.size() >= 2) {
			for (int i = 0; i < list.size(); i++)  //外循环是循环的次数
			{
				for (int j = list.size() - 1; j > i; j--)  //内循环是 外循环一次比较的次数
				{
					if (list.get(i) == list.get(j)) {
						list.remove(j);
					}

				}
			}
		}
		return list;
	}

}
