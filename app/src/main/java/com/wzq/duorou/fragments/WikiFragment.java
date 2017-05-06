package com.wzq.duorou.fragments;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wzq.duorou.R;
import com.wzq.duorou.activitys.WebActivity;
import com.wzq.duorou.base.BaseFragment;
import com.wzq.duorou.beans.Wiki;
import com.wzq.duorou.widget.PushGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WikiFragment extends BaseFragment {

    private PushGridView gridView;
    private GAdapter adapter;

    public WikiFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wiki;
    }

    @Override
    protected void initViews() {
        gridView = (PushGridView)getBaseView().findViewById(R.id.gridView);
        adapter = new GAdapter();
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = WebActivity.getInstance(getActivity());
                intent.putExtra("title","多肉百科");
                intent.putExtra("url",adapter.getItem(position).getLocal());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void lazyFetchData() {

    }

    class GAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return getWikis().size();
        }

        @Override
        public Wiki getItem(int position) {
            return getWikis().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            Holder holder = null;
            if (view == null){
                view = LayoutInflater.from(getActivity()).inflate(R.layout.home_grid_item,null);
                holder = new Holder(view);
                view.setTag(holder);
            }else {
                holder = (Holder) view.getTag();
            }
            holder.tv_content.setText(getItem(position).getTitle());
            Glide.with(getActivity()).load(getItem(position).getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.image);
            return view;
        }
    }

    class Holder{
        ImageView image;
        TextView tv_content;
        public Holder(View view){
            image = (ImageView) view.findViewById(R.id.image);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
        }
    }

    public List<Wiki> getWikis(){

        List<Wiki> wikis = new ArrayList<>();

        Wiki wiki0 = new Wiki(0,"玉翁(Mammillaria hahniana)",urls[0],"file:///android_asset/wiki0.html");
        Wiki wiki1 = new Wiki(1,"空蝉(Utsusemi)",urls[1],"file:///android_asset/wiki1.html");
        Wiki wiki2 = new Wiki(2,"爱之蔓(RosaryVine)",urls[2],"file:///android_asset/wiki2.html");
        Wiki wiki3 = new Wiki(3,"钱串(Crassula perforata)",urls[3],"file:///android_asset/wiki3.html");
        Wiki wiki4 = new Wiki(4,"千羽鹤(Echeveria peacockii)",urls[4],"file:///android_asset/wiki4.html");
        Wiki wiki5 = new Wiki(5,"山地玫瑰(Greenovia Webb)",urls[5],"file:///android_asset/wiki5.html");
        Wiki wiki6 = new Wiki(6,"星美人(Pachyphytum oviferum)",urls[6],"file:///android_asset/wiki6.html");
        Wiki wiki7 = new Wiki(7,"昙花(Epiphyllum oxypetalum)",urls[7],"file:///android_asset/wiki7.html");

        wikis.add(wiki0);
        wikis.add(wiki1);
        wikis.add(wiki2);
        wikis.add(wiki3);
        wikis.add(wiki4);
        wikis.add(wiki5);
        wikis.add(wiki6);
        wikis.add(wiki7);

        return wikis;
    }

    public String[] urls = {
           "http://onqmk81ra.bkt.clouddn.com/wiki0000.jpg!new",
           "http://onqmk81ra.bkt.clouddn.com/wiki0001.jpg!new",
           "http://onqmk81ra.bkt.clouddn.com/wiki0002.jpg!new",
           "http://onqmk81ra.bkt.clouddn.com/wiki0003.jpg!new",
           "http://onqmk81ra.bkt.clouddn.com/wiki0004.jpg!new",
           "http://onqmk81ra.bkt.clouddn.com/wiki0005.jpg!new",
           "http://onqmk81ra.bkt.clouddn.com/wiki0006.jpg!new",
           "http://onqmk81ra.bkt.clouddn.com/wiki0007.jpg!new"
    };
}
