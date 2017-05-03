package com.wzq.duorou.fragments;


import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wzq.duorou.R;
import com.wzq.duorou.base.BaseListFragment;
import com.wzq.duorou.beans.GanHuo;
import com.wzq.duorou.recyclerview.base.ViewHolder;

public class FuLiFragment extends BaseListFragment<GanHuo> {

    @Override
    public int getItemLayout() {
        return R.layout.item_fuli;
    }

    @Override
    public void fillValue(ViewHolder holder, GanHuo ganHuo, int position) {
        ImageView mImage = holder.getView(R.id.image);
        Picasso.with(getContext()).load(ganHuo.getUrl()).placeholder(R.mipmap.avatar).into(mImage);
    }

    @Override
    protected String getUrl() {
        return "http://gank.io/api/data/福利/" + String.valueOf(pageSize) + "/" + String.valueOf(page);
    }
}
