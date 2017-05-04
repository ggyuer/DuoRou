package com.wzq.duorou.chat.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.hyphenate.easeui.domain.EaseUser;
import com.wzq.duorou.R;
import com.wzq.duorou.widget.AlertDialog;

public class ForwardMessageActivity extends PickContactNoCheckboxActivity {
	private EaseUser selectUser;
	private String forward_msg_id;

	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

    @Override
    protected void initView() {
        super.initView();
        forward_msg_id = getIntent().getStringExtra("forward_msg_id");
    }

    @Override
	protected void onListItemClick(int position) {
		selectUser = contactAdapter.getItem(position);
		new AlertDialog(this, null, getString(R.string.confirm_forward_to, selectUser.getNick()), null, new AlertDialog.AlertDialogUser() {
            @Override
            public void onResult(boolean confirmed, Bundle bundle) {
                if (confirmed) {
                    if (selectUser == null)
                        return;
                    try {
                        ChatActivity.activityInstance.finish();
                    } catch (Exception e) {
                    }
                    Intent intent = new Intent(ForwardMessageActivity.this, ChatActivity.class);
                    // it is single chat
                    intent.putExtra("userId", selectUser.getUsername());
                    intent.putExtra("forward_msg_id", forward_msg_id);
                    startActivity(intent);
                    finish();
                }
            }
        }, true).show();
	}

}
