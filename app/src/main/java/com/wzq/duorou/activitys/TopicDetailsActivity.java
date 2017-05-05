package com.wzq.duorou.activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hyphenate.easeui.ui.GlideCircleTransform;
import com.wzq.duorou.Constant;
import com.wzq.duorou.MyHelper;
import com.wzq.duorou.R;
import com.wzq.duorou.base.MyBaseActivity;
import com.wzq.duorou.beans.TopicUser;
import com.wzq.duorou.utils.DatasUtil;
import com.wzq.duorou.utils.PreferenceManager;

import java.util.List;

public class TopicDetailsActivity extends MyBaseActivity {

    private LinearLayout imageView;
    private ListView listView;
    private TopicAdapter adapter;
    private EditText circleEt;
    private ImageView sendIv;
    private List<TopicUser> topicUsers;

    public static Intent getInstance(Activity activity) {
        return new Intent(activity, TopicDetailsActivity.class);
    }

    public int getFlag() {
        return getIntent().getIntExtra("flag", 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayoutId() {
        return R.layout.activity_topic_details;
    }

    public int getReId() {
        return getIntent().getIntExtra("re", 0);
    }

    @Override
    protected void initView() {
        MyHelper.getInstance().setTopicUsers(DatasUtil.getTopicUsers());
        titleName.setText("多肉话题");
        leftImg.setVisibility(View.VISIBLE);
        circleEt = (EditText) findViewById(R.id.circleEt);
        imageView = (LinearLayout) findViewById(R.id.img);
        sendIv = (ImageView) findViewById(R.id.sendIv);
        listView = (ListView) findViewById(R.id.listView);
        adapter = new TopicAdapter(MyHelper.getInstance().getTopicUsers());
        listView.setAdapter(adapter);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), getReId());
        Drawable drawable = new BitmapDrawable(bitmap);
        imageView.setBackground(drawable);

        sendIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = circleEt.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    showToast("不能为空！");
                    return;
                }
                String nick = PreferenceManager.getInstance().getValueFromPreferences(Constant.USER_NICK_NAME, "");
                String avatar = PreferenceManager.getInstance().getValueFromPreferences(Constant.USER_AVATAR, "");
                TopicUser user = new TopicUser(avatar, nick, content);
                circleEt.setText("");
                showToast("发送成功！");
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                MyHelper.getInstance().saveTopicUser(user);
                adapter.notifyDataSetChanged();
            }
        });
    }


    class TopicAdapter extends BaseAdapter {

        private List<TopicUser> userList;

        public TopicAdapter(List<TopicUser> userList) {
            this.userList = userList;
        }

        @Override
        public int getCount() {
            return userList.size();
        }

        @Override
        public TopicUser getItem(int position) {
            return userList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            Holder holder = null;
            if (view == null) {
                view = LayoutInflater.from(TopicDetailsActivity.this).inflate(R.layout.topic_layout_item_to, null);
                holder = new Holder(view);
                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }
            Glide.with(TopicDetailsActivity.this).load(getItem(position).getAvatar())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.default_avatar)
                    .transform(new GlideCircleTransform(TopicDetailsActivity.this))
                    .into(holder.header);
            holder.content.setText(getItem(position).getContent());
            holder.nameTv.setText(getItem(position).getNick());
            return view;
        }
    }

    class Holder {
        ImageView header;
        TextView nameTv;
        TextView content;

        public Holder(View view) {
            header = (ImageView) view.findViewById(R.id.header);
            nameTv = (TextView) view.findViewById(R.id.nameTv);
            content = (TextView) view.findViewById(R.id.content);
        }
    }


}
