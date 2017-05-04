package com.wzq.duorou.chat.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.hyphenate.easeui.utils.EaseUserUtils;
import com.wzq.duorou.Constant;
import com.wzq.duorou.R;
import com.wzq.duorou.base.MyBaseActivity;
import com.wzq.duorou.chat.presenter.SingleImpl;
import com.wzq.duorou.chat.presenter.SinglePresenter;
import com.wzq.duorou.chat.view.ISingleDetailView;
import com.wzq.duorou.widget.AlertDialog;

public class SingleDetailActivity extends MyBaseActivity implements ISingleDetailView{

    private ImageView avatarImg;
    private TextView nameTv;
    private LinearLayout addLayout;
    private RelativeLayout singleTopChat, singleMessageFree, singleChatFile, singleFindContent, singleClearRecord;
    private ToggleButton isTopSwitch,isDisturbSwitch;
    private SinglePresenter presenter;
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
        presenter = new SingleImpl(this);
        leftImg.setVisibility(View.VISIBLE);
        titleName.setText("聊天信息");
        avatarImg = (ImageView) findViewById(R.id.avatarImg);
        nameTv = (TextView) findViewById(R.id.nameTv);
        EaseUserUtils.setUserAvatar(this, getUserName(), avatarImg);
        EaseUserUtils.setUserNick(getUserName(), nameTv);
        isTopSwitch = (ToggleButton)findViewById(R.id.isTopSwitch);
        isDisturbSwitch = (ToggleButton)findViewById(R.id.isDisturbSwitch);
        leftImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
        isShow();
    }

    public void execute(View view) {
        switch (view.getId()) {
            case R.id.singleTopChat:
                if (isTopSwitch.isChecked()) {
                    presenter.delTopConversation(getUserName());
                } else {
                    presenter.saveTopConversation(getUserName());
                }
                break;
            case R.id.singleMessageFree:
                if (isDisturbSwitch.isChecked()) {
                    presenter.delDisturb(getUserName());
                } else {
                    presenter.saveDisturb(getUserName());
                }
                break;
            case R.id.singleChatFile:
                Intent chatFile = ChatFileActivity.newInstance(this);
                chatFile.putExtra(Constant.USER_HX_ID, getUserName());
                startActivity(chatFile);
                break;
            case R.id.singleFindContent:
                Intent intent = AllSearchMessageActivity.newInstance(this);
                intent.putExtra(Constant.USER_HX_ID, getUserName());
                startActivity(intent);
                break;
            case R.id.singleClearRecord:
                presenter.clearRecord(getUserName());
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        setResult(Activity.RESULT_OK);
        finish();
        return false;
    }

    public void isShow() {
        presenter.showIsTopChat(getUserName());
        presenter.showIsDisturb(getUserName());
       // ShowUserInfo.setUserAvatar(this, getUserName(), avatarImg);
        //ShowUserInfo.setUserNickName(getUserName(), nameTv);
    }

    @Override
    public void showIsTopChat(boolean flag) {
        isTopSwitch.setChecked(flag);
    }

    @Override
    public void showIsDisturb(boolean flag) {
        isDisturbSwitch.setChecked(flag);
    }

    @Override
    public void saveTopConversation(long code) {
        if (code >= 1) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    isTopSwitch.setChecked(true);
                }
            });
        }
    }

    @Override
    public void delTopConversation(long code) {
        if (code >= 1) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    isTopSwitch.setChecked(false);
                    isTopSwitch.invalidate();
                }
            });
        }
    }

    @Override
    public void saveDisturb(long code) {
        if (code >= 1) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    isDisturbSwitch.setChecked(true);
                    isDisturbSwitch.invalidate();
                }
            });
        }
    }

    @Override
    public void delDisturb(long code) {
        if (code >= 1) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    isDisturbSwitch.setChecked(false);
                    isDisturbSwitch.invalidate();
                }
            });
        }
    }

    @Override
    public void clearRecord(boolean flag) {
        if (flag) {
            String msg = getResources().getString(com.hyphenate.easeui.R.string.Whether_to_empty_all_chats);
            new AlertDialog(this, null, msg, null, new AlertDialog.AlertDialogUser() {

                @Override
                public void onResult(boolean confirmed, Bundle bundle) {
                    if (confirmed) {
                        Toast.makeText(SingleDetailActivity.this, "清除成功！", Toast.LENGTH_SHORT).show();
                    }
                }
            }, true).show();
        } else {
            Toast.makeText(SingleDetailActivity.this, "清除失败！", Toast.LENGTH_SHORT).show();
        }
    }
}
