package com.wzq.duorou.chat.view.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.easeui.widget.EaseExpandGridView;
import com.hyphenate.exceptions.HyphenateException;
import com.wzq.duorou.App;
import com.wzq.duorou.Constant;
import com.wzq.duorou.MyHelper;
import com.wzq.duorou.R;
import com.wzq.duorou.base.MyBaseActivity;
import com.wzq.duorou.beans.Disturb;
import com.wzq.duorou.beans.GroupNick;
import com.wzq.duorou.beans.TopUser;
import com.wzq.duorou.utils.PreferenceManager;
import com.wzq.duorou.utils.SendMessageTool;
import com.wzq.duorou.widget.AlertDialog;

public class GroupDetailActivity extends MyBaseActivity implements CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "GroupDetailsActivity";
    private String groupId;
    private TextView tv_group_name, tv_group_desc, tv_group_announcement, tv_group_nick_value;

    private RelativeLayout rl_group_desc, rl_group_announcement,
            rl_public, rl_invite, rl_addfriend, rl_group_search_message;

    private ToggleButton tog_add_friend, tog_nickname_detail,
            tog_invite, tog_public, tog_block_message, tog_topchat_detail;
    private EaseExpandGridView gridView;
    private String ADDFRIEND = "allow_be_friend";
    private String INVITE = "allow_invite";
    private String PUBLIC = "is_public";
    private Button exitBtn, dismissBtn;
    private static final int REQUEST_CODE_ADD_USER = 0;
    private static final int REQUEST_CODE_EXIT = 1;
    private static final int REQUEST_CODE_EXIT_DELETE = 2;
    private static final int REQUEST_CODE_EDIT_GROUPNAME = 5;
    private GroupMemberAdapter adapter;
    private EMGroup group;
    public static final int BODY = 0;
    public static final int MINUS = 1;
    public static final int ADDITION = 2;

    public static Intent getInstance(Activity activity) {
        return new Intent(activity, GroupDetailActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayoutId() {
        return R.layout.activity_group_detail;
    }

    public String getGroupId() {
        return getIntent().getStringExtra("groupId");
    }

    public String getToUserName() {
        return getIntent().getStringExtra("username");
    }

    public String getOwner() {
        return getIntent().getStringExtra("owner");
    }

    public int getMemmber() {
        return group.getMemberCount();
    }

    @Override
    protected void initView() {
        group = EMClient.getInstance().groupManager().getGroup(getToUserName());
        leftImg.setVisibility(View.VISIBLE);
        leftImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回结果
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
        exitBtn = (Button) findViewById(R.id.btn_exit_group);
        gridView = (EaseExpandGridView) findViewById(R.id.group_detail_gridView);
        dismissBtn = (Button) findViewById(R.id.btn_dissmiss_group);
        rl_group_announcement = (RelativeLayout) findViewById(R.id.announcement_content);
        rl_group_desc = (RelativeLayout) findViewById(R.id.introduce_content);
        tv_group_name = (TextView) findViewById(R.id.group_detail_group_name);
        tv_group_name.setText(group.getGroupName());
        tv_group_desc = (TextView) findViewById(R.id.group_detail_introduce);
        tv_group_desc.setText(group.getDescription());
        tv_group_announcement = (TextView) findViewById(R.id.group_detail_announcement);
        tv_group_nick_value = (TextView) findViewById(R.id.tv_group_nick_value);
        String userName = PreferenceManager.getInstance().getValueFromPreferences(Constant.USER_HX_ID, null);
        rl_invite = (RelativeLayout) findViewById(R.id.groupCanInviteFriend);
        rl_addfriend = (RelativeLayout) findViewById(R.id.groupCanAddFriend);
        titleName.setText(group.getGroupName()+"("+group.getMembers()+")");
        if (userName.equals(EMClient.getInstance().getCurrentUser())) {
            //rl_invite.setVisibility(View.VISIBLE);
            //rl_addfriend.setVisibility(View.VISIBLE);
            exitBtn.setVisibility(View.GONE);
            dismissBtn.setVisibility(View.VISIBLE);
            adapter = new GroupMemberAdapter(getMemmber(), true);
        } else {
            exitBtn.setVisibility(View.VISIBLE);
            dismissBtn.setVisibility(View.GONE);
            //rl_addfriend.setVisibility(View.GONE);
            //rl_invite.setVisibility(View.GONE);
            adapter = new GroupMemberAdapter(getMemmber(), false);
        }
        gridView.setAdapter(adapter);
        rl_group_search_message = (RelativeLayout) findViewById(R.id.groupSearchMessage);
        tog_add_friend = (ToggleButton) findViewById(R.id.tog_addfriend_detail);
        tog_block_message = (ToggleButton) findViewById(R.id.tog_blockmessage_detail);
        tog_topchat_detail = (ToggleButton) findViewById(R.id.tog_topchat_detail);
        tog_invite = (ToggleButton) findViewById(R.id.tog_invite_detail);

        tog_nickname_detail = (ToggleButton) findViewById(R.id.tog_nickname_detail);
        tog_invite.setOnCheckedChangeListener(this);
        tog_add_friend.setOnCheckedChangeListener(this);
        tog_block_message.setOnCheckedChangeListener(this);
        tog_nickname_detail.setOnCheckedChangeListener(this);
        tog_topchat_detail.setOnCheckedChangeListener(this);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("---aaaa---", "init");
                //   LoadingDialog.show(GroupDetailsActivity.this, getString(R.string.loading_message));
            }
        });
        groupId = getIntent().getStringExtra("groupId");
        isShow();
        initData();
    }

    public void isShow() {
        if (MyHelper.getInstance().getDisturbList().containsKey(groupId)) {
            tog_block_message.setChecked(true);
        } else {
            tog_block_message.setChecked(false);
        }

        if (App.getInstance().getTopUserList().containsKey(groupId)) {
            tog_topchat_detail.setChecked(true);
        } else {
            tog_topchat_detail.setChecked(false);
        }

        if (MyHelper.getInstance().getIsShow()) {
            tog_nickname_detail.setChecked(true);
        } else {
            tog_nickname_detail.setChecked(false);
        }
    }

    private void initData() {
//        groupBean = dao.getGroupBean(groupId);
//        if (groupBean != null) {
//            if (!TextUtils.isEmpty(groupBean.getName())) {
//                titleName.setText(groupBean.getName() + "( " + groupBean.getMembers_count() + " )");
//                tv_group_name.setText(groupBean.getName());
//            }
//            if (!TextUtils.isEmpty(groupBean.getDescription())) {
//                rl_group_desc.setVisibility(View.VISIBLE);
//                tv_group_desc.setText(groupBean.getDescription());
//            }
//            if (groupBean.getAllow_be_friend().equals("1")) {
//                tog_add_friend.setChecked(true);
//            }
//            if (groupBean.getIs_public().equals("1")) {
//                tog_public.setChecked(true);
//            }
//            if (groupBean.getAllow_invite().equals("1")) {
//                tog_invite.setChecked(true);
//            }
//
//        }
//
        String username = PreferenceManager.getInstance().getValueFromPreferences(Constant.USER_HX_ID, "");
        String userNick = PreferenceManager.getInstance().getValueFromPreferences(Constant.USER_NICK_NAME, "");
        String groupNickName = MyHelper.getInstance().getShowNick().get(username + groupId);
        if (!TextUtils.isEmpty(groupNickName)) {
            tv_group_nick_value.setText(groupNickName);
        } else {
            tv_group_nick_value.setText(userNick);
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    /**
     * 修改我的群昵称
     */
    public void editGroupNickName() {
        final Button cancel, sure;
        final Dialog dialog = new Dialog(GroupDetailActivity.this, R.style.Dialog);
        dialog.setContentView(R.layout.chat_group_nick_view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        final EditText nickNameForThisGroup = (EditText) dialog.findViewById(R.id.nickNameForThisGroup);
        final String groupNickName = tv_group_nick_value.getText().toString().trim();
        if (!TextUtils.isEmpty(groupNickName)) {
            nickNameForThisGroup.setText(groupNickName);
            nickNameForThisGroup.setSelection(groupNickName.length());
        }

        cancel = (Button) dialog.findViewById(R.id.cancel);
        sure = (Button) dialog.findViewById(R.id.sure);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickNameForGroup = nickNameForThisGroup.getText().toString().trim();
                if (TextUtils.isEmpty(nickNameForGroup)) {
                    showToast("输入不能为空！");
                    return;
                }
                if (groupNickName.equals(nickNameForGroup)) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    return;
                }
                tv_group_nick_value.setText(nickNameForGroup);
                String username = PreferenceManager.getInstance().getValueFromPreferences(Constant.USER_HX_ID, "");
                String userNick = PreferenceManager.getInstance().getValueFromPreferences(Constant.USER_NICK_NAME, "");
                GroupNick nick = new GroupNick(username, groupId, userNick, nickNameForGroup);
                if (MyHelper.getInstance().setShowNick(nick) >= 0) {
                    showToast("修改成功！");
                    SendMessageTool.sendCmdShowNickMessage(nickNameForGroup,userNick, groupId);
                } else {
                    showToast("修改失败！");
                }
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    public void execute(View v){
        switch (v.getId()){
            case R.id.groupMessageFree:
                if (tog_block_message.isChecked()) {
                    tog_block_message.setChecked(false);
                    MyHelper.getInstance().deleteDisturb(groupId);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                EMClient.getInstance().groupManager().unblockGroupMessage(groupId);//需异步处理
                            } catch (HyphenateException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } else {
                    tog_block_message.setChecked(true);
                    Disturb disturb = new Disturb();
                    disturb.setUserId(groupId);
                    MyHelper.getInstance().saveDisturb(disturb);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                EMClient.getInstance().groupManager().blockGroupMessage(groupId);//需异步处理
                            } catch (HyphenateException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                break;
            case R.id.groupShowMemberNick:
                if (tog_nickname_detail.isChecked()) {
                    tog_nickname_detail.setChecked(false);
                    MyHelper.getInstance().setIsShow(false);
                } else {
                    tog_nickname_detail.setChecked(true);
                    MyHelper.getInstance().setIsShow(true);
                }
                break;
            case R.id.groupTopChat:
                if (tog_topchat_detail.isChecked()) {
                    tog_topchat_detail.setChecked(false);
                    App.getInstance().deleteTopUser(groupId);
                } else {
                    tog_topchat_detail.setChecked(true);
                    TopUser user = new TopUser();
                    user.setUserName(groupId);
                    user.setTime(System.currentTimeMillis());
                    App.getInstance().setTopUser(user);
                }
                break;
            case R.id.groupMyNick:
                editGroupNickName();
                break;
            case R.id.groupSearchMessage:
                Intent data = AllSearchMessageActivity.newInstance(this);
                data.putExtra(Constant.USER_HX_ID, groupId);
                startActivity(data);
                break;
            case R.id.groupClearMessage:
                String msg = getResources().getString(com.hyphenate.easeui.R.string.Whether_to_empty_all_chats);
                new AlertDialog(this, null, msg, null, new AlertDialog.AlertDialogUser() {

                    @Override
                    public void onResult(boolean confirmed, Bundle bundle) {
                        if (confirmed) {
                            clearGroupHistory();
                        }
                    }
                }, true).show();
                break;
            case R.id.groupMassageFile:
                Intent chatIntent = ChatFileActivity.newInstance(this);
                chatIntent.putExtra(Constant.USER_HX_ID, groupId);
                startActivity(chatIntent);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        setResult(Activity.RESULT_OK);
        finish();
        return false;
    }

    /**
     * 清空群聊天记录
     */
    private void clearGroupHistory() {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(groupId,
                EMConversation.EMConversationType.GroupChat);
        if (conversation != null) {
            conversation.clearAllMessages();
        }
        Toast.makeText(this, "清除成功！", Toast.LENGTH_SHORT).show();
    }

    class GroupMemberAdapter extends BaseAdapter {

        private int ids;
        private boolean isOwner = false;

        public GroupMemberAdapter(int ids, boolean isOwner) {
            this.ids = ids;
            this.isOwner = isOwner;
        }

        @Override
        public int getCount() {
            if (isOwner) {
                return ids + 2;
            }
            return ids;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            Holder holder = null;
            if (view == null) {
                view = LayoutInflater.from(GroupDetailActivity.this).inflate(R.layout.group_item, null);
                holder = new Holder(view);
                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }
            if (isOwner) {
                if (position == getCount() - 1) {
                    holder.iv_avatar.setImageResource(R.drawable.chat_smiley_minus_btn);
                    holder.iv_avatar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showToast("456");
                        }
                    });
                } else if (position == getCount() - 2) {
                    holder.iv_avatar.setImageResource(R.drawable.chat_smiley_add_btn);
                    holder.iv_avatar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showToast("123");
                        }
                    });
                } else {
                    String username = (String) getItem(position);
                    Log.e(TAG, "username: " + username);
                    EaseUserUtils.setUserNick(username, holder.tv_name);
                    EaseUserUtils.setUserAvatar(GroupDetailActivity.this, username, holder.iv_avatar);
                }
            } else {
                String username = (String) getItem(position);
                EaseUserUtils.setUserNick(username, holder.tv_name);
                EaseUserUtils.setUserAvatar(GroupDetailActivity.this, username, holder.iv_avatar);
            }
            return view;
        }
    }

    class Holder {
        ImageView iv_avatar;
        TextView tv_name;
        ImageView badge_delete;

        public Holder(View view) {
            iv_avatar = (ImageView) view.findViewById(R.id.iv_avatar);
            badge_delete = (ImageView) view.findViewById(R.id.badge_delete);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
        }
    }
}
