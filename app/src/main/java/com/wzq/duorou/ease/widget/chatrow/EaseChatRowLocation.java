package com.wzq.duorou.ease.widget.chatrow;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wzq.duorou.MyHelper;
import com.wzq.duorou.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMLocationMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessage.ChatType;
import com.hyphenate.easeui.ui.EaseBaiduMapActivity;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.LatLng;

public class EaseChatRowLocation extends EaseChatRow {

    private TextView locationView;
    private EMLocationMessageBody locBody;
    private TextView nickName;

	public EaseChatRowLocation(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                R.layout.ease_row_received_location : R.layout.ease_row_sent_location, this);
    }

    @Override
    protected void onFindViewById() {
    	locationView = (TextView) findViewById(R.id.tv_location);
        nickName = (TextView) findViewById(R.id.nickName);
    }


    @Override
    protected void onSetUpView() {
		locBody = (EMLocationMessageBody) message.getBody();
		locationView.setText(locBody.getAddress());

        if (message.direct() == EMMessage.Direct.RECEIVE &&
                message.getChatType() == ChatType.GroupChat &&
                MyHelper.getInstance().getIsShow()) {
            nickName.setVisibility(View.VISIBLE);
            String userName = message.getFrom();
            String groupId = message.getTo();
            String groupNick = MyHelper.getInstance().getShowNick().get(userName+groupId);
            if (!TextUtils.isEmpty(groupNick)){
                nickName.setText(groupNick);
            }else {
                if (MyHelper.getInstance().getContactList().containsKey(userName)) {
                    String nick = MyHelper.getInstance().getContactList().get(userName).getNickname();
                    nickName.setText(nick);
                } else {
//                    String nick = MyHelper.getInstance().getOtherUser().get(userName).getUserNick();
//                    nickName.setText(nick);
                }
            }
            adapter.notifyDataSetChanged();
        } else {
            nickName.setVisibility(View.GONE);
        }

		// handle sending message
		if (message.direct() == EMMessage.Direct.SEND) {
		    setMessageSendCallback();
            switch (message.status()) {
            case CREATE:
                progressBar.setVisibility(View.GONE);
                statusView.setVisibility(View.VISIBLE);
                break;
            case SUCCESS:
                progressBar.setVisibility(View.GONE);
                statusView.setVisibility(View.GONE);
                break;
            case FAIL:
                progressBar.setVisibility(View.GONE);
                statusView.setVisibility(View.VISIBLE);
                break;
            case INPROGRESS:
                progressBar.setVisibility(View.VISIBLE);
                statusView.setVisibility(View.GONE);
                break;
            default:
               break;
            }
        }else{
            if(!message.isAcked() && message.getChatType() == ChatType.Chat){
                try {
                    EMClient.getInstance().chatManager().ackMessageRead(message.getFrom(), message.getMsgId());
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onUpdateView() {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onBubbleClick() {
        Intent intent = new Intent(context, EaseBaiduMapActivity.class);
        intent.putExtra("latitude", locBody.getLatitude());
        intent.putExtra("longitude", locBody.getLongitude());
        intent.putExtra("address", locBody.getAddress());
        activity.startActivity(intent);
    }

    /*
	 * listener for map clicked
	 */
	protected class MapClickListener implements OnClickListener {

		LatLng location;
		String address;

		public MapClickListener(LatLng loc, String address) {
			location = loc;
			this.address = address;

		}

		@Override
		public void onClick(View v) {
		   
		}
	}

}
