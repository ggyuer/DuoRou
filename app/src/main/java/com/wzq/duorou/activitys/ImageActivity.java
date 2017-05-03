package com.wzq.duorou.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.wzq.duorou.R;
import com.wzq.duorou.base.MyBaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private MyAdapter adapter;
    //  private LoadingDialog dialog;
    private LinearLayout layout;
    private List<ImageView> points = new ArrayList<>();
    private int current;

    public static Intent newInstance(Activity activity) {
        return new Intent(activity, ImageActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        showAkk();
        initView();
    }

    public List<String> getImgUrl() {
        return getIntent().getStringArrayListExtra("urls");
    }

    public void showAkk() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

    public int getIndex() {
        return getIntent().getIntExtra("index", 0);
    }

    public void initView() {

        // dialog = LoadingDialog.show(this,getString(R.string.loading_message));
        layout = (LinearLayout) findViewById(R.id.bottomPoint);

        for (int i = 0; i < getImgUrl().size(); i++) {
            ImageView point = new ImageView(this);
            point.setImageResource(R.drawable.web_img_point_selector);
            //point.setBackgroundColor(Color.parseColor("#ffb6b4"));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(24, 24);
            layoutParams.leftMargin = 20;
            layoutParams.bottomMargin = 10;
            layout.addView(point, layoutParams);
            points.add(point);
        }
        current = getIndex();
        points.get(getIndex()).setSelected(true);
        //dialog.setCancelable(false);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
        adapter = new MyAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(getIndex());
        adapter.notifyDataSetChanged();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                points.get(current).setSelected(false);
                // set current tab selected
                points.get(position).setSelected(true);
                current = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return getImgUrl().size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PhotoView view = new PhotoView(ImageActivity.this);
            view.enable();
            view.setScaleType(ImageView.ScaleType.FIT_CENTER);

            Glide.with(ImageActivity.this)
                    .load(getImgUrl().get(position))
                    .crossFade()
                    .into(new GlideDrawableImageViewTarget(view) {
                        @Override
                        public void onResourceReady(GlideDrawable resource,
                                                    GlideAnimation<? super GlideDrawable> animation) {
                            super.onResourceReady(resource, animation);
                            //dialog.dismiss();
                        }
                    });

            container.addView(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
