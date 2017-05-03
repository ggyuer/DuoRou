package com.wzq.duorou.fragments;


import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wzq.duorou.R;
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
        public Object getItem(int position) {
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

            Glide.with(getActivity()).load(getWikis().get(position).getUrl()).into(holder.image);
            return view;
        }
    }

    class Holder{
        ImageView image;
        public Holder(View view){
            image = (ImageView) view.findViewById(R.id.image);
        }
    }

    public List<Wiki> getWikis(){

        List<Wiki> wikis = new ArrayList<>();

        Wiki wiki = new Wiki(0,"yuyu",urls[0]);
        Wiki wiki1 = new Wiki(0,"yuyu",urls[0]);
        Wiki wiki2 = new Wiki(0,"yuyu",urls[0]);
        Wiki wiki3 = new Wiki(0,"yuyu",urls[0]);
        Wiki wiki4 = new Wiki(0,"yuyu",urls[0]);
        Wiki wiki5 = new Wiki(0,"yuyu",urls[0]);
        Wiki wiki6 = new Wiki(0,"yuyu",urls[0]);
        Wiki wiki7 = new Wiki(0,"yuyu",urls[0]);
        Wiki wiki8 = new Wiki(0,"yuyu",urls[0]);
        Wiki wiki9 = new Wiki(0,"yuyu",urls[0]);
        Wiki wiki10 = new Wiki(0,"yuyu",urls[0]);

        wikis.add(wiki);
        wikis.add(wiki1);
        wikis.add(wiki2);
        wikis.add(wiki3);
        wikis.add(wiki4);
        wikis.add(wiki5);
        wikis.add(wiki6);
        wikis.add(wiki7);
        wikis.add(wiki8);
        wikis.add(wiki9);
        wikis.add(wiki10);

        return wikis;
    }

    public String[] urls = {
      "http://onqmk81ra.bkt.clouddn.com/ll.jpg!new"
    };
}
