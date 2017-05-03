package com.wzq.duorou.fragments;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wzq.duorou.R;
import com.wzq.duorou.activitys.WebActivity;
import com.wzq.duorou.base.BaseFragment;
import com.wzq.duorou.beans.News;
import com.wzq.duorou.widget.PullUpLoadMoreListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends BaseFragment {

    private PullUpLoadMoreListView listView;
    private SwipeRefreshLayout refreshLayout;
    private MyAdapter adapter;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initViews() {
        listView = (PullUpLoadMoreListView) getBaseView().findViewById(R.id.listView);
        refreshLayout = (SwipeRefreshLayout) getBaseView().findViewById(R.id.refreshLayout);
        adapter = new MyAdapter(getNews());
        listView.setAdapter(adapter);
        listView.setOnLoadMoreListener(new PullUpLoadMoreListView.OnLoadMoreListener() {
            @Override
            public void loadMore() {
                handler.sendEmptyMessageDelayed(1, 3000);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News item = (News) adapter.getItem(position);
                Intent intent = WebActivity.getInstance(getActivity());
                intent.putExtra("url", item.getHttpPath());
                intent.putExtra("title", "最新资讯");
                getActivity().startActivityForResult(intent, 1002);
            }
        });
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                adapter.setNewsList(getNewsre());
                listView.setLoading(false);
            }
            return false;
        }
    });

    @Override
    protected void lazyFetchData() {

    }

    class MyAdapter extends BaseAdapter {


        List<News> newsList = new ArrayList<>();

        public void setNewsList(List<News> news) {
            newsList.addAll(news);
            notifyDataSetChanged();
        }

        public MyAdapter(List<News> newsList) {
            this.newsList = newsList;
        }


        @Override
        public int getCount() {
            return newsList.size();
        }

        @Override
        public Object getItem(int position) {
            return newsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            Holder holder = null;
            if (view == null) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.new_list_item, null);
                holder = new Holder(view);
                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }

            holder.title.setText(newsList.get(position).getTitle());
            holder.who.setText("分享人: " + newsList.get(position).getWho());
            holder.ping.setText(newsList.get(position).getPing() + "评");
            Glide.with(getActivity()).load(newsList.get(position).getImgUrl()).into(holder.newImg);

            return view;
        }
    }

    class Holder {
        ImageView newImg;
        TextView title;
        TextView who;
        TextView ping;

        public Holder(View view) {
            newImg = (ImageView) view.findViewById(R.id.newImg);
            title = (TextView) view.findViewById(R.id.title);
            who = (TextView) view.findViewById(R.id.who);
            ping = (TextView) view.findViewById(R.id.ping);
        }
    }

    public List<News> getNews() {
        List<News> newses = new ArrayList<>();

        News news0 = new News(0, urls[0], titles[0], "25", "Liam", "file:///android_asset/new0.html");
        News news1 = new News(1, urls[1], titles[1], "22", "属性", "file:///android_asset/new1.html");
        News news2 = new News(2, urls[2], titles[2], "282", "孔爱侃", "file:///android_asset/new2.html");
        News news3 = new News(3, urls[3], titles[3], "121", "季莎大", "file:///android_asset/new3.html");
        News news4 = new News(4, urls[4], titles[4], "56", "买下盟", "file:///android_asset/new4.html");
        News news5 = new News(5, urls[5], titles[5], "56", "柳下惠", "file:///android_asset/new5.html");
        News news6 = new News(6, urls[6], titles[6], "456", "麻辣教师", "file:///android_asset/new6.html");
        News news7 = new News(7, urls[7], titles[7], "45", "西静安寺", "file:///android_asset/new7.html");
        News news8 = new News(8, urls[8], titles[8], "12", "卡卡和时间段", "file:///android_asset/new8.html");
        News news9 = new News(9, urls[9], titles[9], "89", "入会阿紫", "file:///android_asset/new9.html");


        newses.add(news0);
        newses.add(news1);
        newses.add(news2);
        newses.add(news3);
        newses.add(news4);
        newses.add(news5);
        newses.add(news6);
        newses.add(news7);
        newses.add(news8);
        newses.add(news9);

        return newses;
    }

    public String[] urls = {
            "http://onqmk81ra.bkt.clouddn.com/durou/image/new/0000/title!news"
            , "http://onqmk81ra.bkt.clouddn.com/durou/image/new/0001/title!news"
            , "http://onqmk81ra.bkt.clouddn.com/durou/image/new/0002/title!news"
            , "http://onqmk81ra.bkt.clouddn.com/durou/image/new/0003/title!news"
            , "http://onqmk81ra.bkt.clouddn.com/durou/image/new/0004/title!news"
            , "http://onqmk81ra.bkt.clouddn.com/durou/image/new/0005/title!news"
            , "http://onqmk81ra.bkt.clouddn.com/durou/image/new/0006/title!news"
            , "http://onqmk81ra.bkt.clouddn.com/durou/image/new/0007/title!news"
            , "http://onqmk81ra.bkt.clouddn.com/durou/image/new/0008/title!news"
            , "http://onqmk81ra.bkt.clouddn.com/durou/image/new/0009/title!news"
    };

    public String[] titles = {
            "80后IT打造超过200平米的原木露台！"
            , "四川多肉展览盛会，没事的可以去看安！"
            , "第三届广西柳林展览会！"
            , "2016上海多肉随拍，展会多肉特写集合！"
            , "兰溪大学生：当大棚种满多肉是，我就把你娶回家！"
            , "福州文艺小夫妻当花农，自养多肉成艺术品！"
            , "80后乌鲁木齐女孩打造出最美的多肉花园！"
            , "快报：济南激活首次无中文名多肉植物！"
            , "中国花卉报--多肉市场“暴风雨”正在酝酿！"
            , "明星玩多肉：王俊凯把多肉养成狗了！"
    };

    public List<News> getNewsre() {
        ArrayList<News> newses = new ArrayList<>();

        News news0 = new News(0, urls[0], titles[0], "25", "yudas", "file:///android_asset/new0.html");
        News news1 = new News(1, urls[1], titles[1], "22", "asdas", "file:///android_asset/new1.html");
        News news2 = new News(2, urls[2], titles[2], "282", "dsa", "file:///android_asset/new2.html");
        News news3 = new News(3, urls[3], titles[3], "121", "季dasda莎大", "file:///android_asset/new3.html");
        News news4 = new News(4, urls[4], titles[4], "56", "dsad", "file:///android_asset/new4.html");
        News news5 = new News(5, urls[5], titles[5], "56", "柳下惠", "file:///android_asset/new5.html");
        News news6 = new News(6, urls[6], titles[6], "456", "dw", "file:///android_asset/new6.html");
        News news7 = new News(7, urls[7], titles[7], "45", "das", "file:///android_asset/new7.html");
        News news8 = new News(8, urls[8], titles[8], "12", "dsad", "file:///android_asset/new8.html");
        News news9 = new News(9, urls[9], titles[9], "89", "ads", "file:///android_asset/new9.html");


        newses.add(news0);
        newses.add(news1);
        newses.add(news2);
        newses.add(news3);
        newses.add(news4);
        newses.add(news5);
        newses.add(news6);
        newses.add(news7);
        newses.add(news8);
        newses.add(news9);

        return newses;
    }

}
