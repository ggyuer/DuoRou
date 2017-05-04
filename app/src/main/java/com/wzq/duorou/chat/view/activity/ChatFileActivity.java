package com.wzq.duorou.chat.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.wzq.duorou.Constant;
import com.wzq.duorou.R;
import com.wzq.duorou.base.MyBaseActivity;
import com.wzq.duorou.beans.ChatFile;
import com.wzq.duorou.chat.adapter.ChatFileAdapter;
import com.wzq.duorou.chat.presenter.ChatFilePImpl;
import com.wzq.duorou.chat.presenter.ChatFilePresenter;
import com.wzq.duorou.chat.view.IChatFileView;
import com.wzq.duorou.widget.MyGridView;

import java.util.ArrayList;
import java.util.List;

public class ChatFileActivity extends MyBaseActivity implements
        CompoundButton.OnCheckedChangeListener, IChatFileView {

    private MyGridView chatGridView;
    private ChatFileAdapter adapter;
    private LinearLayout chat_file_bottom;
    List<ChatFile> checkedList = new ArrayList<>();
    Button file_share, file_delect;
    private ChatFilePresenter presenter;

    public static Intent newInstance(Activity activity) {
        return new Intent(activity, ChatFileActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayoutId() {
        return R.layout.chat_file_activity;
    }

    @Override
    protected void initView() {
        presenter = new ChatFilePImpl(this, this);
        chatGridView = (MyGridView) findViewById(R.id.chatGridView);
        leftImg.setVisibility(View.VISIBLE);
        titleName.setText("聊天文件");
        rightImg.setVisibility(View.VISIBLE);
        btnRight.setText("选择");
        btnRight.setVisibility(View.VISIBLE);
        file_share = (Button) findViewById(R.id.chat_file_share);
        file_delect = (Button) findViewById(R.id.chat_file_delect);
        chat_file_bottom = (LinearLayout) findViewById(R.id.chat_file_bottom);
        presenter.getChatFile(getUserId());
    }

    public void execute(View view) {
        switch (view.getId()) {
            case R.id.chat_file_share:
                showToast("分享");
                break;
            case R.id.chat_file_delect:
                presenter.deleteChatFile(checkedList);
                break;
            case R.id.btnRight:
                String str_title_right = btnRight.getText().toString();
                if (str_title_right.equals("选择")) {
                    setViewStyle(true);
                } else if (str_title_right.equals("取消")) {
                    setViewStyle(false);
                }
                break;
        }

    }

    protected List<ChatFile> chatFiles = new ArrayList<>();

    public void delChecked() {
        chatFiles.removeAll(checkedList);
    }

    private void setViewStyle(boolean flag) {
        btnRight.setText(flag ? "取消" : "选择");
        chat_file_bottom.setVisibility(flag ? View.VISIBLE : View.GONE);
        adapter.setFlag(flag);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Object tag = buttonView.getTag();
        if (tag != null) {
            if (tag instanceof Integer) {
                Integer position = (Integer) tag;
                ChatFile chatFile = chatFiles.get(position);
                chatFile.setChecked(isChecked);
                // String path = (String)tag;
                if (isChecked) {
                    checkedList.add(chatFile);
                } else {
                    checkedList.remove(chatFile);
                }
            }
            if (checkedList == null || checkedList.size() == 0) {
                file_share.setEnabled(false);
                file_delect.setEnabled(false);
            } else {
                file_share.setEnabled(true);
                file_delect.setEnabled(true);
            }
        }
    }

    public String getUserId() {
        return getIntent().getStringExtra(Constant.USER_HX_ID);
    }

    @Override
    public void getChatFile(List<ChatFile> files) {
        if (files != null) {
            btnRight.setEnabled(true);
            chatFiles = files;
            adapter = new ChatFileAdapter(ChatFileActivity.this, chatFiles);
            adapter.setOnCheckedListener(this);
            chatGridView.setAdapter(adapter);
        } else {
            btnRight.setEnabled(false);
        }
    }

    @Override
    public void deleteChatFile(boolean flag) {
        if (flag) {
            delChecked();
            adapter.notifyDataSetChanged();
            setViewStyle(false);
            showToast("清除成功！");
        }
    }
}
