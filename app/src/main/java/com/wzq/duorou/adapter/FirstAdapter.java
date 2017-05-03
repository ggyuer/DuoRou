package com.wzq.duorou.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wzq.duorou.R;
import com.wzq.duorou.activitys.WebActivity;
import com.wzq.duorou.beans.Breed;
import com.wzq.duorou.theme.ColorTextView;

import java.util.List;

/**
 * Created by weizhenqing on 2017/3/29.
 */

public class FirstAdapter extends BaseAdapter {

    private List<Breed> breeds;

    private Activity context;
    private LayoutInflater inflater;

    public FirstAdapter(Activity context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    private int layoutId;

    public void bindLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public void bindBreeds(List<Breed> breeds) {
        this.breeds = breeds;
    }

    @Override
    public int getCount() {
        return breeds.size();
    }

    @Override
    public Object getItem(int position) {
        return breeds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Holder holder = null;
        if (view == null) {
            holder = new Holder();
            view = inflater.inflate(layoutId, null);
            holder.img = (ImageView) view.findViewById(R.id.img);
            holder.title = (ColorTextView) view.findViewById(R.id.title);
            holder.time = (TextView) view.findViewById(R.id.time);
            holder.likeCount = (TextView) view.findViewById(R.id.likeCount);
            holder.imgLayout = (LinearLayout) view.findViewById(R.id.imgLayout);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        final Breed breed = breeds.get(position);
        Glide.with(context).load(breed.getImg()).into(holder.img);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), breed.getReId());
        Drawable drawable = new BitmapDrawable(bitmap);
        holder.img.setBackground(drawable);
        holder.imgLayout.setBackground(drawable);
        holder.title.setText(breed.getTitle());
        holder.time.setText(breed.getTime());
        holder.likeCount.setText(breed.getLikeCount() + "");
        holder.imgLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = WebActivity.getInstance(context);
                data.putExtra("url", breed.getHttpPath());
                data.putExtra("title", "养殖记录");
                context.startActivityForResult(data, 1001);
            }
        });
        return view;
    }

    class Holder {
        ImageView img;
        ColorTextView title;
        TextView time;
        TextView likeCount;
        LinearLayout imgLayout;
    }

}
