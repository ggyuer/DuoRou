package com.wzq.duorou.chat.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.wzq.duorou.R;
import com.wzq.duorou.base.MyBaseActivity;
import com.wzq.duorou.beans.InviteMessage;
import com.wzq.duorou.chat.adapter.NewFriendsMsgAdapter;
import com.wzq.duorou.chat.model.InviteMessgeDao;

import java.util.List;

/**
 * Application and notification
 *
 */
public class NewFriendsMsgActivity extends MyBaseActivity {

	public static Intent getInstance(Activity activity){
		return new Intent(activity,NewFriendsMsgActivity.class);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected int initLayoutId() {
		return R.layout.em_activity_new_friends_msg;
	}

	@Override
	protected void initView() {
		titleName.setText("消息通知");
		leftImg.setVisibility(View.VISIBLE);
		ListView listView = (ListView) findViewById(R.id.list);
		InviteMessgeDao dao = new InviteMessgeDao(this);
		List<InviteMessage> msgs = dao.getMessagesList();
		NewFriendsMsgAdapter adapter = new NewFriendsMsgAdapter(this, 1, msgs);
		listView.setAdapter(adapter);
		dao.saveUnreadMessageCount(0);
	}

}
