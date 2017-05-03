package com.wzq.duorou.chat.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wzq.duorou.R;
import com.wzq.duorou.base.MyBaseActivity;
import com.wzq.duorou.chat.view.fragment.ContactListFragment;


public class ContactsListActivity extends MyBaseActivity {

    private ContactListFragment contactListFragment;

    public static Intent newInstance(Activity activity) {
        return new Intent(activity, ContactsListActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int initLayoutId() {
        return R.layout.chat_contacts_list_activity;
    }

    @Override
    protected void initView() {
        contactListFragment = new ContactListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.contact_list_fragment,
                contactListFragment).commit();
//        titleBack.setVisibility(View.VISIBLE);
//        titleName.setText("通讯录");
//        rightImg.setImageResource(R.drawable.contacts_icon_addfriend);
    }

    public void execute(View view){
        switch (view.getId()){
//            case R.id.rightImg:
//                showToast("新朋友！");
//                break;
        }
    }
}
