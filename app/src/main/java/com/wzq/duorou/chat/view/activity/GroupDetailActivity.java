package com.wzq.duorou.chat.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.easeui.widget.EaseExpandGridView;
import com.hyphenate.exceptions.HyphenateException;
import com.wzq.duorou.Constant;
import com.wzq.duorou.R;
import com.wzq.duorou.base.MyBaseActivity;
import com.wzq.duorou.utils.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class GroupDetailActivity extends MyBaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

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
        if (userName.equals(EMClient.getInstance().getCurrentUser())) {
            rl_invite.setVisibility(View.VISIBLE);
            rl_addfriend.setVisibility(View.VISIBLE);
            adapter = new GroupMemberAdapter(getMemmber(), true);
        } else {
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

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    @Override
    public void onClick(View v) {

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
