package com.wzq.duorou.ease.widget.chatrow;

import android.content.Context;
import android.text.Spannable;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.wzq.duorou.MyHelper;
import com.wzq.duorou.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessage.ChatType;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.hyphenate.exceptions.HyphenateException;

public class EaseChatRowText extends EaseChatRow{

	private TextView contentView;
	private TextView nickName;

    public EaseChatRowText(Context context, EMMessage message, int position, BaseAdapter adapter) {
		super(context, message, position, adapter);
	}

	@Override
	protected void onInflateView() {
		inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
				R.layout.ease_row_received_message : R.layout.ease_row_sent_message, this);
	}

	@Override
	protected void onFindViewById() {
		contentView = (TextView) findViewById(R.id.tv_chatcontent);
        nickName = (TextView) findViewById(R.id.nickName);
	}

    @Override
    public void onSetUpView() {
        EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();
        Spannable span = EaseSmileUtils.getSmiledText(context, txtBody.getMessage());
        // 设置内容
        contentView.setText(span, BufferType.SPANNABLE);

        if (message.direct() == EMMessage.Direct.RECEIVE &&
                message.getChatType() == ChatType.GroupChat &&
                MyHelper.getInstance().getIsShow()) {
            nickName.setVisibility(View.VISIBLE);
            String userName = message.getFrom();
            String groupId = message.getTo();
            String groupNick = MyHelper.getInstance().getShowNick().get(userName + groupId);
            if (!TextUtils.isEmpty(groupNick)) {
                nickName.setText(groupNick);
            } else {
                if (MyHelper.getInstance().getContactList().containsKey(userName)) {
                    String nick = MyHelper.getInstance().getContactList().get(userName).getNickname();
                    nickName.setText(nick);
                } else {
                    //String nick = MyHelper.getInstance().getOtherUser().get(userName).getUserNick();
                    //nickName.setText(nick);
                }
            }
            adapter.notifyDataSetChanged();
        } else {
            nickName.setVisibility(View.GONE);
        }

        handleTextMessage();
    }

    protected void handleTextMessage() {
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
        // TODO Auto-generated method stub
        
    }



}
