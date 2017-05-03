package com.wzq.duorou.chat.view.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMConversation.EMConversationType;
import com.hyphenate.chat.EMMessage;

import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.util.NetUtils;
import com.wzq.duorou.Constant;
import com.wzq.duorou.R;
import com.wzq.duorou.activitys.MainActivity;
import com.wzq.duorou.beans.TopUser;
import com.wzq.duorou.chat.model.InviteMessgeDao;
import com.wzq.duorou.chat.presenter.ConversationImpl;
import com.wzq.duorou.chat.presenter.ConversationPresenter;
import com.wzq.duorou.chat.view.IConversationView;
import com.wzq.duorou.chat.view.activity.ChatActivity;
import com.wzq.duorou.ease.ui.EaseConversationListFragment;
import com.wzq.duorou.ease.widget.EaseConversationList;

import java.util.HashMap;
import java.util.Map;

public class ConversationListFragment extends EaseConversationListFragment implements IConversationView{

    private TextView errorText;
    private ConversationPresenter presenter = new ConversationImpl(getActivity(),this);
    @Override
    protected void initView() {
        super.initView();
        View errorView = (LinearLayout) View.inflate(getActivity(), R.layout.chat_neterror_item, null);
        errorItemContainer.addView(errorView);
        errorText = (TextView) errorView.findViewById(R.id.tv_connect_errormsg);
    }
    
    @Override
    protected void setUpView() {
        super.setUpView();
        // register context menu
        registerForContextMenu(conversationListView);
        conversationListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EMConversation conversation = conversationListView.getItem(position);
                String username = conversation.conversationId();
                if (username.equals(EMClient.getInstance().getCurrentUser()))
                    Toast.makeText(getActivity(), R.string.Cant_chat_with_yourself, Toast.LENGTH_SHORT).show();
                else {
                    // start chat acitivity
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    if(conversation.isGroup()){
                        if(conversation.getType() == EMConversationType.ChatRoom){
                            // it's group chat
                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_CHATROOM);
                        }else{
                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_GROUP);
                        }
                        
                    }
                    // it's single chat
                    intent.putExtra(Constant.EXTRA_USER_ID, username);
                    startActivity(intent);
                }
            }
        });
        //red packet code : 红包回执消息在会话列表最后一条消息的展示
        conversationListView.setConversationListHelper(new EaseConversationList.EaseConversationListHelper() {
            @Override
            public String onSetItemSecondaryText(EMMessage lastMessage) {

                return null;
            }
        });

        conversationListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showMenuDialog(position);
                return true;
            }
        });

        super.setUpView();
        //end of red packet code
    }

    @Override
    protected void onConnectionDisconnected() {
        super.onConnectionDisconnected();
        if (NetUtils.hasNetwork(getActivity())){
         errorText.setText(R.string.can_not_connect_chat_server_connection);
        } else {
          errorText.setText(R.string.the_current_network);
        }
    }
    
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.em_delete_message, menu); 
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        boolean deleteMessage = false;
        if (item.getItemId() == R.id.delete_message) {
            deleteMessage = true;
        } else if (item.getItemId() == R.id.delete_conversation) {
            deleteMessage = false;
        }
    	EMConversation tobeDeleteCons = conversationListView.getItem(((AdapterContextMenuInfo) item.getMenuInfo()).position);
    	if (tobeDeleteCons == null) {
    	    return true;
    	}
        if(tobeDeleteCons.getType() == EMConversationType.GroupChat){
            EaseAtMessageHelper.get().removeAtMeGroup(tobeDeleteCons.conversationId());
        }
        try {
            // delete conversation
            EMClient.getInstance().chatManager().deleteConversation(tobeDeleteCons.conversationId(), deleteMessage);
            InviteMessgeDao inviteMessgeDao = new InviteMessgeDao(getActivity());
            inviteMessgeDao.deleteMessage(tobeDeleteCons.conversationId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        refresh();

        // update unread count
        //((MainActivity) getActivity()).updateUnreadLabel();
        return true;
    }

    private AlertDialog dialog;

    private void showMenuDialog(final int i) {
        final EMConversation tobeDeleteCons = conversationListView.getItem(i);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.chat_long_click_dialog, null);
        Button delete1 = (Button) view.findViewById(R.id.top);
        Button delete2 = (Button) view.findViewById(R.id.center);
        Button top = (Button) view.findViewById(R.id.bottom);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
        //设置对话框位子
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setAttributes(params);
        dialog.setCanceledOnTouchOutside(true);
        if (topMap.containsKey(tobeDeleteCons.getExtField())) {
            top.setText("取消置顶");
        } else {
            top.setText("置顶聊天");
        }

        delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.delConversation(tobeDeleteCons.conversationId(), true);
            }
        });

        delete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.delConversation(tobeDeleteCons.conversationId(), false);
            }
        });

        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (topMap.containsKey(tobeDeleteCons.getExtField())) {
                    topMap.remove(tobeDeleteCons.getExtField());
                    presenter.delTopConversation(tobeDeleteCons.getExtField());
                } else {
                    TopUser user = new TopUser();
                    user.setTime(System.currentTimeMillis());
                    tobeDeleteCons.setExtField(tobeDeleteCons.conversationId());
                    user.setUserName(tobeDeleteCons.getExtField());
                    Map<String, TopUser> map = new HashMap<>();
                    map.put(tobeDeleteCons.getExtField(), user);
                    topMap.putAll(map);
                    presenter.saveTopConversation(user);
                }
            }

        });
        refresh();
        ((MainActivity) getActivity()).updateUnreadLabel();
    }

    @Override
    public void saveTopConversation(long code) {
        if (code >= 1) {
            dialog.dismiss();
            refresh();
        }
    }

    @Override
    public void delTopConversation(int code) {
        if (code >= 1) {
            dialog.dismiss();
            refresh();
        }
    }

    @Override
    public void delConversation(boolean flag) {
        if (flag) {
            dialog.dismiss();
            refresh();
        }
    }

}
