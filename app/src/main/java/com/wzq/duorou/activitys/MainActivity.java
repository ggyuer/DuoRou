package com.wzq.duorou.activitys;

import android.animation.Animator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.util.EMLog;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.wzq.duorou.Constant;
import com.wzq.duorou.MyHelper;
import com.wzq.duorou.R;
import com.wzq.duorou.base.BaseActivity;
import com.wzq.duorou.chat.model.InviteMessgeDao;
import com.wzq.duorou.chat.view.activity.ChatActivity;
import com.wzq.duorou.chat.view.activity.UserProfileActivity;
import com.wzq.duorou.chat.view.fragment.ContactListFragment;
import com.wzq.duorou.chat.view.fragment.ConversationListFragment;
import com.wzq.duorou.chat.view.fragment.GroupsFragment;
import com.wzq.duorou.event.SkinChangeEvent;
import com.wzq.duorou.fragments.BreedFragment;
import com.wzq.duorou.fragments.CommonFragment;
import com.wzq.duorou.fragments.HomeFragment;
import com.wzq.duorou.fragments.MySettingFragment;
import com.wzq.duorou.fragments.NewsFragment;
import com.wzq.duorou.fragments.QuestionFragment;
import com.wzq.duorou.fragments.TopicFragment;
import com.wzq.duorou.fragments.WikiFragment;
import com.wzq.duorou.runtimepermissions.PermissionsManager;
import com.wzq.duorou.theme.ColorRelativeLayout;
import com.wzq.duorou.theme.ColorUiUtil;
import com.wzq.duorou.theme.ColorView;
import com.wzq.duorou.theme.Theme;
import com.wzq.duorou.utils.PreUtils;
import com.wzq.duorou.utils.PreferenceManager;
import com.wzq.duorou.utils.SystemUtils;
import com.wzq.duorou.utils.ThemeUtils;
import com.wzq.duorou.widget.ResideLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.xiaopan.android.preference.PreferencesUtils;

public class MainActivity extends BaseActivity implements ColorChooserDialog.ColorCallback {

    @Bind(R.id.avatar)
    ImageView mAvatar;
    @Bind(R.id.desc)
    TextView mDesc;
    @Bind(R.id.main_home)
    TextView mAll;
    @Bind(R.id.main_news)
    TextView mFuli;
    @Bind(R.id.main_topic)
    TextView mAndroid;
    @Bind(R.id.main_wiki)
    TextView mIos;
    @Bind(R.id.main_breed)
    TextView mVideo;
    @Bind(R.id.main_question)
    TextView mFront;
    @Bind(R.id.main_friends)
    TextView mResource;
    @Bind(R.id.main_groups)
    TextView mApp;
    @Bind(R.id.main_setting)
    TextView mMore;
    @Bind(R.id.scrollView)
    ScrollView mScrollView;
    @Bind(R.id.about)
    TextView mAbout;
    @Bind(R.id.theme)
    TextView mTheme;
    @Bind(R.id.menu)
    ColorRelativeLayout mMenu;
    @Bind(R.id.status_bar)
    ColorView mStatusBar;
    @Bind(R.id.icon)
    ImageView mIcon;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.container)
    FrameLayout mContainer;
    @Bind(R.id.resideLayout)
    ResideLayout mResideLayout;
    @Bind(R.id.message)
    ImageView message;


    private FragmentManager fragmentManager;
    private String currentFragmentTag;

    private TextView unreadLabel; // 消息数量
    private TextView unreadAddressLable;    // 加好友通知

    private int index;
    private int currentTabIndex;

    public boolean isConflict = false;  // 在其他设备上登录
    private boolean isCurrentAccountRemoved = false;  // 用户被移除

    private ContactListFragment contactListFragment;
    private ConversationListFragment conversationListFragment;
    private long mExitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private long clickTime = 0; //记录第一次点击的时间

    private void exit() {
        if ((System.currentTimeMillis() - clickTime) > 2000) {
            Toast.makeText(MainActivity.this, "再按一次后退键退出程序", Toast.LENGTH_SHORT).show();
            clickTime = System.currentTimeMillis();
        } else {
            this.finish();
            System.exit(0);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String packageName = getPackageName();
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                Intent intent = new Intent();
                intent.setAction(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                startActivity(intent);
            }
        }
        //make sure activity will not in background if user is logged into another device or removed
        if (savedInstanceState != null && savedInstanceState.getBoolean(Constant.ACCOUNT_REMOVED, false)) {
            MyHelper.getInstance().logout(false, null);
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        } else if (savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false)) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        chatAbout();
        Log.e("TAG", PreferenceManager.getInstance().getValueFromPreferences(Constant.USER_AVATAR, "") + "0");
        fragmentManager = getSupportFragmentManager();
        contactListFragment = new ContactListFragment();
        conversationListFragment = new ConversationListFragment();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mStatusBar.setVisibility(View.VISIBLE);
            mStatusBar.getLayoutParams().height = SystemUtils.getStatusHeight(this);
            mStatusBar.setLayoutParams(mStatusBar.getLayoutParams());
        } else {
            mStatusBar.setVisibility(View.GONE);
        }

        String username = PreferenceManager.getInstance().getValueFromPreferences(Constant.USER_HX_ID, "");
        EaseUserUtils.setUserAvatar(this, username, mAvatar);

        mDesc.setText("如果事情才刚开始你就胆怯，你还有什么理由继续......");

        if (PreferencesUtils.getBoolean(this, "isFirst", true)) {
            mResideLayout.openPane();
            PreferencesUtils.putBoolean(this, "isFirst", false);
        }
        mIcon.setImageResource(R.drawable.main_home45);
        mTitle.setText("同好社区");
        switchFragment("home");

    }


    public void switchFragment(String name) {
        if (currentFragmentTag != null && currentFragmentTag.equals(name))
            return;

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        Fragment currentFragment = fragmentManager.findFragmentByTag(currentFragmentTag);
        if (currentFragment != null) {
            ft.hide(currentFragment);
        }

        Fragment foundFragment = fragmentManager.findFragmentByTag(name);

        if (foundFragment == null) {
            if (name.equals("home")) {
                foundFragment = new HomeFragment();
            } else if (name.equals("news")) {
                foundFragment = new NewsFragment();
            } else if (name.equals("topic")) {
                foundFragment = new TopicFragment();
            } else if (name.equals("wiki")) {
                foundFragment = new WikiFragment();
            } else if (name.equals("breed")) {
                foundFragment = new BreedFragment();
            } else if (name.equals("question")) {
                foundFragment = new QuestionFragment();
            } else if (name.equals("friends")) {
                foundFragment = contactListFragment;
            } else if (name.equals("groups")) {
                foundFragment = new GroupsFragment();
            } else if (name.equals("setting")) {
                foundFragment = new MySettingFragment();
            } else if (name.equals("conversation")) {
                foundFragment = conversationListFragment;
            } else {
                foundFragment = CommonFragment.newInstance(name);
            }
        }

        if (foundFragment == null) {

        } else if (foundFragment.isAdded()) {
            ft.show(foundFragment);
        } else {
            ft.add(R.id.container, foundFragment, name);
        }
        ft.commit();
        currentFragmentTag = name;
    }


    private android.app.AlertDialog.Builder exceptionBuilder;
    private boolean isExceptionDialogShow = false;
    private BroadcastReceiver internalDebugReceiver;
    private BroadcastReceiver broadcastReceiver;
    private LocalBroadcastManager broadcastManager;

    private InviteMessgeDao  inviteMessgeDao;

    public void chatAbout() {
        unreadLabel = (TextView) findViewById(R.id.unread_msg_number);
        unreadAddressLable = (TextView) findViewById(R.id.unread_address_number);
        inviteMessgeDao = new InviteMessgeDao(this);
        showExceptionDialogFromIntent(getIntent());
        registerBroadcastReceiver();
        EMClient.getInstance().contactManager().setContactListener(new MyContactListener());
    }

    @Override
    public void onBackPressed() {
        if (mResideLayout.isOpen()) {
            mResideLayout.closePane();
        } else {
            super.onBackPressed();
        }
    }

    @OnClick({R.id.avatar, R.id.main_home, R.id.main_news, R.id.main_topic,
            R.id.main_wiki, R.id.main_breed, R.id.main_question,
            R.id.main_friends, R.id.about,
            R.id.main_groups, R.id.theme, R.id.icon, R.id.main_setting, R.id.message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.avatar:
                Intent intent = UserProfileActivity.newInstance(this);
                intent.putExtra("title", "我的信息");
                intent.putExtra("username", PreferenceManager.getInstance().getValueFromPreferences(Constant.USER_HX_ID, ""));
                intent.putExtra("setting", true);
                startActivityForResult(intent, 1001);
                break;
            case R.id.main_home:
                mResideLayout.closePane();
                mIcon.setImageResource(R.drawable.main_home45);
                mTitle.setText("同好社区");
                switchFragment("home");
                index = 0;
                break;
            case R.id.main_news:
                mResideLayout.closePane();
                mIcon.setImageResource(R.drawable.main_news45);
                mTitle.setText("最新资讯");
                switchFragment("news");
                index = 1;
                break;
            case R.id.main_topic:
                mResideLayout.closePane();
                mIcon.setImageResource(R.drawable.main_topic45);
                mTitle.setText("多肉话题");
                switchFragment("topic");
                index = 2;
                break;
            case R.id.main_wiki:
                mResideLayout.closePane();
                mIcon.setImageResource(R.drawable.main_wiki45);
                mTitle.setText("多肉百科");
                switchFragment("wiki");
                index = 3;
                break;

            case R.id.main_breed:
                mResideLayout.closePane();
                mIcon.setImageResource(R.drawable.main_breed45);
                mTitle.setText("多肉养殖");
                switchFragment("breed");
                index = 4;
                break;
            case R.id.main_question:
                mResideLayout.closePane();
                mIcon.setImageResource(R.drawable.main_question45);
                mTitle.setText("同好问答");
                switchFragment("question");
                index = 5;
                break;
            case R.id.main_friends:
                mResideLayout.closePane();
                mIcon.setImageResource(R.drawable.main_friends45);
                mTitle.setText("通讯录");
                switchFragment("friends");
                index = 6;
                break;
            case R.id.main_groups:
                mResideLayout.closePane();
                mIcon.setImageResource(R.drawable.main_groups45);
                mTitle.setText("群聊");
                switchFragment("groups");
                index = 7;
                break;
            case R.id.main_setting:
                mResideLayout.closePane();
                mIcon.setImageResource(R.drawable.main_setting45);
                mTitle.setText("设置");
                switchFragment("setting");
                index = 8;
                break;
            case R.id.message:
                showToast("message");
                mTitle.setText("会话");
                switchFragment("conversation");
                index = 9;
                break;
            case R.id.about:
                new MaterialDialog.Builder(this)
                        .title(R.string.about)
                        .icon(new IconicsDrawable(this)
                                .color(ThemeUtils.getThemeColor(this, R.attr.colorPrimary))
                                .icon(MaterialDesignIconic.Icon.gmi_account)
                                .sizeDp(20))
                        .content(R.string.about_me)
                        .positiveText(R.string.close)
                        .show();
                break;
            case R.id.theme:
                new ColorChooserDialog.Builder(this, R.string.theme)
                        .customColors(R.array.colors, null)
                        .doneButton(R.string.done)
                        .cancelButton(R.string.cancel)
                        .allowUserColorInput(false)
                        .allowUserColorInputAlpha(false)
                        .show();
                break;
            case R.id.icon:
                mResideLayout.openPane();
                break;
        }
    }

    @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, @ColorInt int selectedColor) {
        if (selectedColor == ThemeUtils.getThemeColor(this, R.attr.colorPrimary))
            return;
        EventBus.getDefault().post(new SkinChangeEvent());

        if (selectedColor == getResources().getColor(R.color.colorBluePrimary)) {
            setTheme(R.style.BlueTheme);
            PreUtils.setCurrentTheme(this, Theme.Blue);

        } else if (selectedColor == getResources().getColor(R.color.colorRedPrimary)) {
            setTheme(R.style.RedTheme);
            PreUtils.setCurrentTheme(this, Theme.Red);

        } else if (selectedColor == getResources().getColor(R.color.colorBrownPrimary)) {
            setTheme(R.style.BrownTheme);
            PreUtils.setCurrentTheme(this, Theme.Brown);

        } else if (selectedColor == getResources().getColor(R.color.colorGreenPrimary)) {
            setTheme(R.style.GreenTheme);
            PreUtils.setCurrentTheme(this, Theme.Green);

        } else if (selectedColor == getResources().getColor(R.color.colorPurplePrimary)) {
            setTheme(R.style.PurpleTheme);
            PreUtils.setCurrentTheme(this, Theme.Purple);

        } else if (selectedColor == getResources().getColor(R.color.colorTealPrimary)) {
            setTheme(R.style.TealTheme);
            PreUtils.setCurrentTheme(this, Theme.Teal);

        } else if (selectedColor == getResources().getColor(R.color.colorPinkPrimary)) {
            setTheme(R.style.PinkTheme);
            PreUtils.setCurrentTheme(this, Theme.Pink);

        } else if (selectedColor == getResources().getColor(R.color.colorDeepPurplePrimary)) {
            setTheme(R.style.DeepPurpleTheme);
            PreUtils.setCurrentTheme(this, Theme.DeepPurple);

        } else if (selectedColor == getResources().getColor(R.color.colorOrangePrimary)) {
            setTheme(R.style.OrangeTheme);
            PreUtils.setCurrentTheme(this, Theme.Orange);

        } else if (selectedColor == getResources().getColor(R.color.colorIndigoPrimary)) {
            setTheme(R.style.IndigoTheme);
            PreUtils.setCurrentTheme(this, Theme.Indigo);

        } else if (selectedColor == getResources().getColor(R.color.colorLightGreenPrimary)) {
            setTheme(R.style.LightGreenTheme);
            PreUtils.setCurrentTheme(this, Theme.LightGreen);

        } else if (selectedColor == getResources().getColor(R.color.colorDeepOrangePrimary)) {
            setTheme(R.style.DeepOrangeTheme);
            PreUtils.setCurrentTheme(this, Theme.DeepOrange);

        } else if (selectedColor == getResources().getColor(R.color.colorLimePrimary)) {
            setTheme(R.style.LimeTheme);
            PreUtils.setCurrentTheme(this, Theme.Lime);

        } else if (selectedColor == getResources().getColor(R.color.colorBlueGreyPrimary)) {
            setTheme(R.style.BlueGreyTheme);
            PreUtils.setCurrentTheme(this, Theme.BlueGrey);

        } else if (selectedColor == getResources().getColor(R.color.colorCyanPrimary)) {
            setTheme(R.style.CyanTheme);
            PreUtils.setCurrentTheme(this, Theme.Cyan);

        }
        final View rootView = getWindow().getDecorView();
        rootView.setDrawingCacheEnabled(true);
        rootView.buildDrawingCache(true);

        final Bitmap localBitmap = Bitmap.createBitmap(rootView.getDrawingCache());
        rootView.setDrawingCacheEnabled(false);
        if (null != localBitmap && rootView instanceof ViewGroup) {
            final View tmpView = new View(getApplicationContext());
            tmpView.setBackgroundDrawable(new BitmapDrawable(getResources(), localBitmap));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) rootView).addView(tmpView, params);
            tmpView.animate().alpha(0).setDuration(400).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    ColorUiUtil.changeTheme(rootView, getTheme());
                    System.gc();
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    ((ViewGroup) rootView).removeView(tmpView);
                    localBitmap.recycle();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        showExceptionDialogFromIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!isConflict && !isCurrentAccountRemoved) {
            updateUnreadLabel();
            updateUnreadAddressLable();
        }

        // unregister this event listener when this activity enters the
        // background
        MyHelper sdkHelper = MyHelper.getInstance();
        sdkHelper.pushActivity(this);

        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    @Override
    protected void onStop() {
        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
        MyHelper sdkHelper = MyHelper.getInstance();
        sdkHelper.popActivity(this);

        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isConflict", isConflict);
        outState.putBoolean(Constant.ACCOUNT_REMOVED, isCurrentAccountRemoved);
        super.onSaveInstanceState(outState);
    }

    private int getExceptionMessageId(String exceptionType) {
        if (exceptionType.equals(Constant.ACCOUNT_CONFLICT)) {
            return R.string.connect_conflict;
        } else if (exceptionType.equals(Constant.ACCOUNT_REMOVED)) {
            return R.string.main_user_remove;
        } else if (exceptionType.equals(Constant.ACCOUNT_FORBIDDEN)) {
            return R.string.main_user_forbidden;
        }
        return R.string.Network_error;
    }

    private void showExceptionDialog(String exceptionType) {
        isExceptionDialogShow = true;
        MyHelper.getInstance().logout(false, null);
        String st = getResources().getString(R.string.Logoff_notification);
        if (!MainActivity.this.isFinishing()) {
            // clear up global variables
            try {
                if (exceptionBuilder == null)
                    exceptionBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
                exceptionBuilder.setTitle(st);
                exceptionBuilder.setMessage(getExceptionMessageId(exceptionType));
                exceptionBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        exceptionBuilder = null;
                        isExceptionDialogShow = false;
                        finish();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                exceptionBuilder.setCancelable(false);
                exceptionBuilder.create().show();
                isConflict = true;
            } catch (Exception e) {
                EMLog.e("TAG", "---------color conflictBuilder error" + e.getMessage());
            }
        }
    }

    private void showExceptionDialogFromIntent(Intent intent) {
        EMLog.e("TAG", "showExceptionDialogFromIntent");
        if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_CONFLICT, false)) {
            showExceptionDialog(Constant.ACCOUNT_CONFLICT);
        } else if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_REMOVED, false)) {
            showExceptionDialog(Constant.ACCOUNT_REMOVED);
        } else if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_FORBIDDEN, false)) {
            showExceptionDialog(Constant.ACCOUNT_FORBIDDEN);
        }
    }

    private void registerBroadcastReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_CONTACT_CHANAGED);
        intentFilter.addAction(Constant.ACTION_GROUP_CHANAGED);
        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                updateUnreadLabel();
                updateUnreadAddressLable();
                if (currentTabIndex == 9) {
                    // refresh conversation list
                    if (conversationListFragment != null) {
                        conversationListFragment.refresh();
                    }
                } else if (currentTabIndex == 6) {
                    if (contactListFragment != null) {
                        contactListFragment.refresh();
                    }
                }
                String action = intent.getAction();
                if (action.equals(Constant.ACTION_GROUP_CHANAGED)) {
                }
            }
        };
        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    public class MyContactListener implements EMContactListener {
        @Override
        public void onContactAdded(String username) {
        }

        @Override
        public void onContactDeleted(final String username) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (ChatActivity.activityInstance != null && ChatActivity.activityInstance.toChatUsername != null &&
                            username.equals(ChatActivity.activityInstance.toChatUsername)) {
                        String st10 = getResources().getString(R.string.have_you_removed);
                        Toast.makeText(MainActivity.this, ChatActivity.activityInstance.getToChatUsername() + st10, Toast.LENGTH_LONG)
                                .show();
                        ChatActivity.activityInstance.finish();
                    }
                }
            });
            updateUnreadAddressLable();
        }

        @Override
        public void onContactInvited(String username, String reason) {
        }

        @Override
        public void onFriendRequestAccepted(String username) {
        }

        @Override
        public void onFriendRequestDeclined(String username) {
        }
    }

    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            // notify new message
            for (EMMessage message : messages) {
                MyHelper.getInstance().getNotifier().onNewMsg(message);
            }
            refreshUIWithMessage();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //red packet code : 处理红包回执透传消息
            for (EMMessage message : messages) {

            }
            refreshUIWithMessage();
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
        }
    };

    private void refreshUIWithMessage() {
        runOnUiThread(new Runnable() {
            public void run() {
                // refresh unread count
                updateUnreadLabel();
                if (currentTabIndex == 9) {
                    // refresh conversation list
                    if (conversationListFragment != null) {
                        conversationListFragment.refresh();
                    }
                }
            }
        });
    }

    /**
     * update unread message count
     */
    public void updateUnreadLabel() {
        int count = getUnreadMsgCountTotal();
        if (count > 0) {
            unreadLabel.setText(String.valueOf(count));
            unreadLabel.setVisibility(View.VISIBLE);
        } else {
            unreadLabel.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * update the total unread count
     */
    public void updateUnreadAddressLable() {
        runOnUiThread(new Runnable() {
            public void run() {
                int count = getUnreadAddressCountTotal();
                if (count > 0) {
                    unreadAddressLable.setVisibility(View.VISIBLE);
                } else {
                    unreadAddressLable.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    /**
     * get unread event notification count, including application, accepted, etc
     *
     * @return
     */
    public int getUnreadAddressCountTotal() {
        int unreadAddressCountTotal = 0;
        unreadAddressCountTotal = inviteMessgeDao.getUnreadMessagesCount();
        return unreadAddressCountTotal;
    }

    /**
     * get unread message count
     *
     * @return
     */
    public int getUnreadMsgCountTotal() {
        int unreadMsgCountTotal = 0;
        int chatroomUnreadMsgCount = 0;
        unreadMsgCountTotal = EMClient.getInstance().chatManager().getUnreadMessageCount();
        for (EMConversation conversation : EMClient.getInstance().chatManager().getAllConversations().values()) {
            if (conversation.getType() == EMConversation.EMConversationType.ChatRoom)
                chatroomUnreadMsgCount = chatroomUnreadMsgCount + conversation.getUnreadMsgCount();
        }
        return unreadMsgCountTotal - chatroomUnreadMsgCount;
    }

}
