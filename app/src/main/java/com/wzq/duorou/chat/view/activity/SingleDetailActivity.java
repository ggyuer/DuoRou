package com.wzq.duorou.chat.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.wzq.duorou.R;
import com.wzq.duorou.base.MyBaseActivity;

public class SingleDetailActivity extends MyBaseActivity {

    private ImageView avatarImg;
    private TextView nameTv;
    private LinearLayout addLayout;
    private RelativeLayout singleTopChat, singleMessageFree, singleChatFile, singleFindContent, singleClearRecord;
    private ToggleButton isTopSwitch,isDisturbSwitch;
    public static Intent genInstance(Activity activity) {
        return new Intent(activity, SingleDetailActivity.class);
    }

    public String getUserName() {
        return getIntent().getStringExtra("username");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayoutId() {
        return R.layout.activity_single_detail;
    }

    @Override
    protected void initView() {
        leftImg.setVisibility(View.VISIBLE);
        titleName.setText("聊天信息");
        avatarImg = (ImageView) findViewById(R.id.avatarImg);
        nameTv = (TextView) findViewById(R.id.nameTv);
        EaseUserUtils.setUserAvatar(this, getUserName(), avatarImg);
        EaseUserUtils.setUserNick(getUserName(), nameTv);
        isTopSwitch = (ToggleButton)findViewById(R.id.isTopSwitch);
        isDisturbSwitch = (ToggleButton)findViewById(R.id.isDisturbSwitch);
    }

    public void execute(View view) {
        switch (view.getId()) {
            case R.id.singleTopChat:
                break;
            case R.id.singleMessageFree:
                break;
            case R.id.singleChatFile:
                break;
            case R.id.singleFindContent:
                break;
            case R.id.singleClearRecord:
                clearGroupHistory();
                break;
        }
    }

    /**
     * 清空群聊天记录
     */
    private void clearGroupHistory() {

        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(getUserName(), EMConversation.EMConversationType.Chat);
        if (conversation != null) {
            conversation.clearAllMessages();
        }
        Toast.makeText(this, R.string.messages_are_empty, Toast.LENGTH_SHORT).show();
    }
}
