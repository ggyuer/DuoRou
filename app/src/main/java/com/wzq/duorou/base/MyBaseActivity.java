package com.wzq.duorou.base;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.easeui.ui.EaseBaseActivity;
import com.wzq.duorou.R;
import com.wzq.duorou.theme.Theme;
import com.wzq.duorou.utils.PreUtils;


/**
 * 除了MainActivity外的所有用到复用reuse_top_view_layout 的 activity继承
 */

public abstract class MyBaseActivity extends EaseBaseActivity {


    protected Button btnRight;
    protected TextView titleName;
    protected TextView save;
    protected ImageView rightImg;
    protected ImageView leftImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onPreCreate();

        setContentView(initLayoutId());
        initTop();
        initView();
    }

    public void initTop() {
        titleName = (TextView) findViewById(R.id.title_name);
        save = (TextView) findViewById(R.id.save);
        leftImg = (ImageView) findViewById(R.id.title_left);
        rightImg = (ImageView) findViewById(R.id.title_right);
        btnRight = (Button) findViewById(R.id.btnRight);
        leftImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public void showToast(String data) {
        Toast.makeText(this, data, Toast.LENGTH_LONG).show();
    }

    private void onPreCreate() {
        Theme theme = PreUtils.getCurrentTheme(this);
        switch (theme) {
            case Blue:
                setTheme(R.style.BlueTheme);
                break;
            case Red:
                setTheme(R.style.RedTheme);
                break;
            case Brown:
                setTheme(R.style.BrownTheme);
                break;
            case Green:
                setTheme(R.style.GreenTheme);
                break;
            case Purple:
                setTheme(R.style.PurpleTheme);
                break;
            case Teal:
                setTheme(R.style.TealTheme);
                break;
            case Pink:
                setTheme(R.style.PinkTheme);
                break;
            case DeepPurple:
                setTheme(R.style.DeepPurpleTheme);
                break;
            case Orange:
                setTheme(R.style.OrangeTheme);
                break;
            case Indigo:
                setTheme(R.style.IndigoTheme);
                break;
            case LightGreen:
                setTheme(R.style.LightGreenTheme);
                break;
            case Lime:
                setTheme(R.style.LimeTheme);
                break;
            case DeepOrange:
                setTheme(R.style.DeepOrangeTheme);
                break;
            case Cyan:
                setTheme(R.style.CyanTheme);
                break;
            case BlueGrey:
                setTheme(R.style.BlueGreyTheme);
                break;
        }

    }

    protected abstract void initView();

    protected abstract int initLayoutId();
}
