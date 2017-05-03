package com.wzq.duorou.fragments;


import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.wzq.duorou.R;
import com.wzq.duorou.adapter.FirstAdapter;
import com.wzq.duorou.base.BaseFragment;
import com.wzq.duorou.utils.BreedDataUtil;
import com.wzq.duorou.utils.CommonUtils;
import com.wzq.duorou.widget.MultipleStatusView;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class BreedFragment extends BaseFragment {

    @Bind(R.id.multipleStatusView)
    MultipleStatusView mMultipleStatusView;
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    FirstAdapter adapter;

    @Override
    protected void lazyFetchData() {

    }

    @Override
    protected void initViews() {
        adapter = new FirstAdapter(getActivity());
        adapter.bindLayoutId(R.layout.breeding_layout);
        adapter.bindBreeds(BreedDataUtil.initData());
        listView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (refreshLayout.isRefreshing()) {
                    try {
                        Thread.sleep(3000);
                        refreshLayout.setRefreshing(false);
                    } catch (Exception e) {

                    }

                }
            }

        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_breed;
    }


}

