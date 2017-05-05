package com.wzq.duorou.fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wzq.duorou.R;
import com.wzq.duorou.activitys.TopicDetailsActivity;
import com.wzq.duorou.base.BaseFragment;
import com.wzq.duorou.beans.Topic;
import com.wzq.duorou.theme.ColorTextView;
import com.wzq.duorou.widget.PullUpLoadMoreListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopicFragment extends BaseFragment {

    private SwipeRefreshLayout refreshLayout;
    private PullUpLoadMoreListView listView;
    private MyAdapter adapter;

    public TopicFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_topic;
    }

    @Override
    protected void initViews() {
        refreshLayout = (SwipeRefreshLayout) getBaseView().findViewById(R.id.refreshLayout);
        listView = (PullUpLoadMoreListView) getBaseView().findViewById(R.id.listView);
        adapter = new MyAdapter(getTopic());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Topic item = (Topic) adapter.getItem(position);
                Intent intent = TopicDetailsActivity.getInstance(getActivity());
                intent.putExtra("topicId",item.getId());
                intent.putExtra("re",item.getReId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void lazyFetchData() {

    }

    class MyAdapter extends BaseAdapter {


        List<Topic> topics = new ArrayList<>();

        public void setNewsList(List<Topic> news) {
            topics.addAll(news);
            notifyDataSetChanged();
        }

        public MyAdapter(List<Topic> topics) {
            this.topics = topics;
        }


        @Override
        public int getCount() {
            return topics.size();
        }

        @Override
        public Object getItem(int position) {
            return topics.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            Holder holder = null;
            if (view == null) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.topic_item_layout, null);
                holder = new Holder(view);
                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }

            holder.topicName.setText(topics.get(position).getTopicName());
            holder.content.setText(topics.get(position).getContent());
            holder.join.setText(topics.get(position).getJoin() + "人参与评论");


            Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(), topics.get(position).getReId());
            Drawable drawable = new BitmapDrawable(bitmap);
            holder.img.setBackground(drawable);

            return view;
        }
    }

    class Holder {
        ImageView img;
        ColorTextView topicName;
        ColorTextView content;
        ColorTextView join;
        RelativeLayout relativeLayout;

        public Holder(View view) {
            img = (ImageView) view.findViewById(R.id.img);
            topicName = (ColorTextView) view.findViewById(R.id.topicName);
            content = (ColorTextView) view.findViewById(R.id.content);
            join = (ColorTextView) view.findViewById(R.id.join);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.bg);
        }
    }

    public List<Topic> getTopic() {
        List<Topic> topics = new ArrayList<>();

        Topic topic0 = new Topic(0, titiles[0], contents[0], "452", reIds[0]);
        Topic topic1 = new Topic(1, titiles[1], contents[1], "741", reIds[1]);
        Topic topic2 = new Topic(2, titiles[2], contents[2], "123", reIds[2]);

        topics.add(topic0);
        topics.add(topic1);
        topics.add(topic2);

        return topics;
    }

    public String[] urls = {
            "http://onqmk81ra.bkt.clouddn.com/durou/image/topic/0000/bg!topic",
            "http://onqmk81ra.bkt.clouddn.com/durou/image/topic/0003/bg!topic",
            "http://onqmk81ra.bkt.clouddn.com/durou/image/topic/0002/bg!topic"
    };

    public String[] titiles = {
            "#2017,你的愿望是什么？#",
            "#心中的完美多肉#",
            "#来一场多肉婚礼是不是更浪漫#"
    };

    public String[] contents = {
            "17年写下的愿望，期待美梦成真的那一刻！",
            "多肉的情缘，与我难分难解！",
            "浪漫的是人，还是多肉，还是爱情？这是个问题！"
    };

    public int[] reIds = {
            R.drawable.topic0000,
            R.drawable.topic0001,
            R.drawable.topic0002
    };

}
