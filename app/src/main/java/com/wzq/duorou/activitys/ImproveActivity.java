package com.wzq.duorou.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wzq.duorou.Constant;
import com.wzq.duorou.MyHelper;
import com.wzq.duorou.R;
import com.wzq.duorou.base.MyBaseActivity;
import com.wzq.duorou.utils.PreferenceManager;
import com.wzq.duorou.widget.CircleImageView;

import java.io.ByteArrayOutputStream;

public class ImproveActivity extends MyBaseActivity implements View.OnClickListener{

    private static final int REQUESTCODE_PICK = 1;
    private static final int REQUESTCODE_CUTTING = 2;
    CircleImageView avatarImageView;
    TextView  genderTextView;
    EditText nickEdit;
    Button btn_save_perfect;
    RelativeLayout ll_gender;
    Dialog genderDialog;
    public static final String TAG = "ImprovePersonActivity";
    String serverId;
    private boolean progressShow;
    private ProgressDialog pd;

    public static Intent newInstance(Activity activity){
        return new Intent(activity,ImproveActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayoutId() {
        return R.layout.activity_improve;
    }

    @Override
    protected void initView() {
        titleName.setText("补全资料");
        avatarImageView = (CircleImageView) findViewById(R.id.avatar_perfect);
        nickEdit = (EditText) findViewById(R.id.edit_nick_perfect);
        genderTextView = (TextView) findViewById(R.id.tv_gender);
        btn_save_perfect = (Button) findViewById(R.id.btn_save_perfect);
        ll_gender = (RelativeLayout) findViewById(R.id.ll_perfect_gender);
        ll_gender.setOnClickListener(this);
        avatarImageView.setOnClickListener(this);
        btn_save_perfect.setOnClickListener(this);
        nickEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.avatar_perfect:
                uploadHeadPhoto();
                break;
            case R.id.ll_perfect_gender:
                showgenderDialog();
                break;
            case R.id.btn_save_perfect:
                String nickname = nickEdit.getText().toString().trim();
                String gender = genderTextView.getText().toString().trim();

                String gender_num = "";
                if (gender.equals("第三方性别")) {
                    showToast("请选择性别");
                    return;
                } else if (gender.equals("男")) {
                    gender_num = "1";
                } else if (gender.equals("女")) {
                    gender_num = "0";
                }
                if (TextUtils.isEmpty(nickname)) {
                    showToast("请填写昵称");
                    return;
                }
                PreferenceManager.getInstance().setValueToPrefrences(Constant.USER_GENDER,gender);
                progressShow = true;
                pd = new ProgressDialog(ImproveActivity.this);
                pd.setCanceledOnTouchOutside(false);
                pd.setMessage(getString(R.string.Is_landing));
                pd.setProgressStyle(R.style.new_circle_progress);
                pd.show();
                updateRemoteNick(nickname);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        showToast("请补全资料！");
        return true;
    }

    private void updateRemoteNick(final String nickName) {
        dialog = ProgressDialog.show(this, getString(R.string.dl_update_nick), getString(R.string.dl_waiting));
        new Thread(new Runnable() {

            @Override
            public void run() {
                boolean updatenick = MyHelper.getInstance().getUserProfileManager().updateCurrentUserNickName(nickName);
                if (ImproveActivity.this.isFinishing()) {
                    return;
                }
                if (!updatenick) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(ImproveActivity.this, getString(R.string.toast_updatenick_fail), Toast.LENGTH_SHORT)
                                    .show();
                            dialog.dismiss();
                            pd.dismiss();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            pd.dismiss();
                            Toast.makeText(ImproveActivity.this, getString(R.string.toast_updatenick_success), Toast.LENGTH_SHORT)
                                    .show();
                           PreferenceManager.getInstance().setValueToPrefrences(Constant.USER_NICK_NAME,nickName);
                            PreferenceManager.getInstance().setIsNeed(1);
                            Intent intent  = new Intent(ImproveActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        }).start();
    }

    //性别选项对话框
    private void showgenderDialog() {
        final ImageView male, female, none;
        genderDialog = new Dialog(ImproveActivity.this, R.style.Dialog);
        genderDialog.setContentView(R.layout.dialog_sex);
        genderDialog.setCanceledOnTouchOutside(true);
        genderDialog.setCancelable(true);

        male = (ImageView) genderDialog.findViewById(R.id.sex_male);
        female = (ImageView) genderDialog.findViewById(R.id.sex_female);
        none = (ImageView) genderDialog.findViewById(R.id.sex_none);

        String sex = genderTextView.getText().toString();
        if (sex.equals("女")) {
            female.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sex_checked));
            male.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sex_unchecked));
            none.setImageResource(R.drawable.sex_unchecked);
        } else if (sex.equals("男")) {
            male.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sex_checked));
            female.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sex_unchecked));
            none.setImageResource(R.drawable.sex_unchecked);
        } else if (sex.equals("未选择") || sex.equals("第三方性别")) {
            male.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sex_unchecked));
            female.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sex_unchecked));
            none.setImageResource(R.drawable.sex_checked);
        }

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                male.setImageDrawable(ContextCompat.getDrawable(ImproveActivity.this, R.drawable.sex_checked));
                female.setImageDrawable(ContextCompat.getDrawable(ImproveActivity.this, R.drawable.sex_unchecked));
                none.setImageResource(R.drawable.sex_unchecked);
                genderTextView.setText("男");
                genderDialog.dismiss();
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                female.setImageDrawable(ContextCompat.getDrawable(ImproveActivity.this, R.drawable.sex_unchecked));
                male.setImageDrawable(ContextCompat.getDrawable(ImproveActivity.this, R.drawable.sex_checked));
                none.setImageResource(R.drawable.sex_unchecked);
                genderTextView.setText("女");
                genderDialog.dismiss();
            }
        });

        none.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                female.setImageDrawable(ContextCompat.getDrawable(ImproveActivity.this, R.drawable.sex_unchecked));
                male.setImageDrawable(ContextCompat.getDrawable(ImproveActivity.this, R.drawable.sex_unchecked));
                none.setImageResource(R.drawable.sex_checked);
                genderTextView.setText("第三方性别");
                genderDialog.dismiss();
            }
        });
        genderDialog.show();
    }

    private void uploadHeadPhoto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dl_title_upload_photo);
        builder.setItems(new String[] { getString(R.string.dl_msg_take_photo), getString(R.string.dl_msg_local_upload) },
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        switch (which) {
                            case 0:
                                Toast.makeText(ImproveActivity.this, getString(R.string.toast_no_support),
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Intent pickIntent = new Intent(Intent.ACTION_PICK,null);
                                pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                startActivityForResult(pickIntent, REQUESTCODE_PICK);
                                break;
                            default:
                                break;
                        }
                    }
                });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUESTCODE_PICK:
                if (data == null || data.getData() == null) {
                    return;
                }
                startPhotoZoom(data.getData());
                break;
            case REQUESTCODE_CUTTING:
                if (data != null) {
                    setPicToView(data);
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, REQUESTCODE_CUTTING);
    }

    /**
     * save the picture data
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(getResources(), photo);
            avatarImageView.setImageDrawable(drawable);
            uploadUserAvatar(Bitmap2Bytes(photo));
        }

    }

    private ProgressDialog dialog;

    private void uploadUserAvatar(final byte[] data) {
        dialog = ProgressDialog.show(this, getString(R.string.dl_update_photo), getString(R.string.dl_waiting));
        new Thread(new Runnable() {

            @Override
            public void run() {
                final String avatarUrl = MyHelper.getInstance().getUserProfileManager().uploadUserAvatar(data);
                PreferenceManager.getInstance().setValueToPrefrences(Constant.USER_AVATAR,"");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        if (avatarUrl != null) {
                            Toast.makeText(ImproveActivity.this, getString(R.string.toast_updatephoto_success),
                                    Toast.LENGTH_SHORT).show();
                            Log.e("TAG",avatarUrl);
                        } else {
                            Toast.makeText(ImproveActivity.this, getString(R.string.toast_updatephoto_fail),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        }).start();

        dialog.show();
    }


    public byte[] Bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

}
