package com.wzq.duorou.chat.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.easeui.ui.EaseShowBigImageActivity;
import com.hyphenate.easeui.ui.EaseShowVideoActivity;
import com.wzq.duorou.R;
import com.wzq.duorou.beans.ChatFile;
import com.wzq.duorou.chat.model.ChatFileDao;
import com.wzq.duorou.utils.PushImageUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by weizhenqing on 2017/4/12.
 */

public class ChatFileAdapter extends BaseAdapter {

    private Context context;
    private Map<String, ChatFile> chatFileMap;
    private List<ChatFile> chatFileList;
    private CompoundButton.OnCheckedChangeListener mOnCheckedListener;
    private boolean flag;

    public ChatFileAdapter(Context context, List<ChatFile> chatFileList) {
        this.context = context;
        this.chatFileList = chatFileList;
    }

    //接口回调checkbox选中取消事件
    public void setOnCheckedListener(CompoundButton.OnCheckedChangeListener mOnCheckedListener) {
        this.mOnCheckedListener = mOnCheckedListener;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public int getCount() {
        return chatFileList.size();
    }

    @Override
    public Object getItem(int position) {
        return chatFileList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.chat_file_item, parent, false);
            holder.fileBg = (RelativeLayout) convertView.findViewById(R.id.fileBg);
            holder.fileTypeImg = (ImageView) convertView.findViewById(R.id.fileTypeImg);
            holder.lianjieTx = (TextView) convertView.findViewById(R.id.lianjieTx);
            holder.videoTime = (TextView) convertView.findViewById(R.id.videoTime);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.file_selection_state);
            holder.checkBox.setOnCheckedChangeListener(mOnCheckedListener);
            holder.bg = (ImageView) convertView.findViewById(R.id.bg);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ChatFile chatFile = chatFileList.get(position);
        String path = chatFile.getPath();
        String tPath = chatFile.getThumbPath();
        holder.checkBox.setTag(position);
        //holder.checkBox.setTag(path);
        holder.checkBox.setVisibility(flag ? View.VISIBLE : View.GONE);
        holder.bg.setVisibility(flag ? View.VISIBLE : View.GONE);
        holder.bg.getBackground().setAlpha(30);
        Log.e("flag", holder.checkBox.getVisibility() + "");
        if (!TextUtils.isEmpty(path)) {
            Log.e("/--=1-=", path);

        }
        if (!TextUtils.isEmpty(tPath)) {
            Log.e("/--=-2=", tPath);
        }

        holder.checkBox.setChecked(chatFile.isChecked());

        if (chatFile.getFileType().equals(ChatFileDao.IMG_FILE)) {
            if (!TextUtils.isEmpty(tPath)) {
                PushImageUtils.showImageView(tPath, holder.fileBg);
            } else {
                PushImageUtils.showImageView(path, holder.fileBg);
            }
        } else if (chatFile.getFileType().equals(ChatFileDao.VIDEO_FILE)) {
            PushImageUtils.showVideoThumbView(chatFile.getThumbPath(), holder.fileBg);
            holder.fileTypeImg.setImageResource(R.drawable.file_video);
            holder.videoTime.setText(chatFile.getDuration());
        }
        holder.fileBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chatFile.getFileType().equals(ChatFileDao.IMG_FILE)) {
                    Intent intent = new Intent(context, EaseShowBigImageActivity.class);
                    File file = new File(chatFile.getPath());
                    if (file.exists()) {
                        Uri uri = Uri.fromFile(file);
                        intent.putExtra("uri", uri);
                        context.startActivity(intent);
                    } else {
                        String msgId = chatFile.getMessageId();
                        intent.putExtra("messageId", msgId);
                        intent.putExtra("localUrl", chatFile.getPath());
                        context.startActivity(intent);
                    }
                } else if (chatFile.getFileType().equals(ChatFileDao.VIDEO_FILE)) {
                    Intent intent = new Intent(context, EaseShowVideoActivity.class);
                    intent.putExtra("localpath", chatFile.getPath());
                    intent.putExtra("secret", chatFile.getSecret());
                    intent.putExtra("remotepath", chatFile.getRemotePath());
                    context.startActivity(intent);
                }
            }
        });
        return convertView;
    }


        class ViewHolder {
            RelativeLayout fileBg;//文件背景 如果是图片类型的话 直接显示
            ImageView fileTypeImg; // 文件类型的标识
            TextView lianjieTx; // 链接文件 Tx
            TextView videoTime;// 视频文件 时间
            CheckBox checkBox;
            ImageView bg;
    }
}
