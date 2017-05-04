package com.wzq.duorou.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseImageCache;
import com.hyphenate.easeui.utils.EaseImageUtils;
import com.hyphenate.util.ImageUtils;
import com.wzq.duorou.App;
import com.wzq.duorou.Constant;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/11/24.
 */
public class PushImageUtils {

    private static final String TAG = "PushImageUtils";
    private static PushImageUtils instance;
//    public static final File saveImgFile = new File(Environment.getExternalStorageDirectory());


    private PushImageUtils() {

    }

    private static Context mContext;

    public static PushImageUtils getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            instance = new PushImageUtils();
        }
        return instance;
    }

    // 获取OPtions
    public static BitmapFactory.Options getOptions() {

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inTempStorage = new byte[100 * 1024];

        //设置位图颜色显示优化方式
        //ALPHA_8：每个像素占用1byte内存（8位）
        //ARGB_4444:每个像素占用2byte内存（16位）
        //ARGB_8888:每个像素占用4byte内存（32位）
        //RGB_565:每个像素占用2byte内存（16位）
        //Android默认的颜色模式为ARGB_8888，这个颜色模式色彩最细腻，显示质量最高。但同样的，占用的内存//也最大。也就意味着一个像素点占用4个字节的内存。
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        //设置图片可以被回收，创建Bitmap用于存储Pixel的内存空间在系统内存不足时可以被回收
        opts.inPurgeable = true;
        //设置位图缩放比例
        opts.inSampleSize = 4;
        //设置解码位图的尺寸信息
        opts.inInputShareable = true;

        return opts;
    }


    //根据图片地址获取图片旋转角度
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    public static Bitmap pathToBitmap(String path) {

        // getOptions()：图片参数
        Bitmap bitmap = BitmapFactory.decodeFile(path, PushImageUtils.getOptions());
        Matrix m = new Matrix();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 旋转angle度,readPictureDegree:获取旋转角度
        m.setRotate(PushImageUtils.readPictureDegree(path));
        // 生成新图片
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                m, true);
        return bitmap;
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }



    public static void saveImg(Bitmap bitmap) {
        //  if(Environment.getExternalStorageDirectory() != null){
        File appDir = new File(Environment.getExternalStorageDirectory(), "head");
        if (!appDir.exists()) {
            appDir.mkdir();
        }

        File file = new File(appDir, Constant.Head_IMAGE_NAME);
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
            outputStream.flush();
            outputStream.close();
           // LogUtil.toastMsg(App.getContext(), "suc");
        } catch (IOException e) {
        }
        //  }
    }

    /**
     * 将base64字符串转化为bitmap
     *
     * @param string
     * @return
     */
    public Bitmap stringtoBitmap(String string) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    /**
     * 将图片转化为base64字符串
     *
     * @param bitmap
     * @return
     */
    public String bitmaptoString(Bitmap bitmap) {
        //将Bitmap转换成字符串
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

    public static String compressImage(String filePath, String targetPath, int quality) {
        Bitmap bm = getSmallBitmap(filePath);//获取一定尺寸的图片
        int degree = readPictureDegree(filePath);//获取相片拍摄角度
        if (degree != 0) {//旋转照片角度，防止头像横着显示
            bm = rotateBitmap(bm, degree);
        }
        File outputFile = new File(targetPath);
        try {
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();
                //outputFile.createNewFile();
            } else {
                outputFile.delete();
            }
            FileOutputStream out = new FileOutputStream(outputFile);
            bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
        } catch (Exception e) {
        }
        return outputFile.getPath();
    }

    /**
     * 根据路径获得图片信息并按比例压缩，返回bitmap
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只解析图片边沿，获取宽高
        BitmapFactory.decodeFile(filePath, options);
        // 计算缩放比
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        // 完整解析图片返回bitmap
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }


    /**
     * 旋转照片
     *
     * @param bitmap
     * @param degress
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }


    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 把bitmap转换成String
     *
     * @param filePath
     * @return
     */
    public static String bitmapToString(String filePath) {

        Bitmap bm = getSmallBitmap(filePath);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] b = baos.toByteArray();

        return Base64.encodeToString(b, Base64.DEFAULT);

    }

    public static boolean saveImageToLocal(String path, Bitmap bmp) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        bmp.compress(Bitmap.CompressFormat.JPEG, 90, fout);
        try {
            fout.flush();
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean showImageView(final String thumbernailPath,
                                        final RelativeLayout iv) {
        // first check if the thumbnail image already loaded into cache
        Bitmap bitmap = EaseImageCache.getInstance().get(thumbernailPath);
        if (bitmap != null) {
            // thumbnail image is already loaded, reuse the drawable
            Drawable drawable = new BitmapDrawable(bitmap);
            iv.setBackground(drawable);
            return true;
        } else {
            new AsyncTask<Object, Void, Bitmap>() {

                @Override
                protected Bitmap doInBackground(Object... args) {
                    File file = new File(thumbernailPath);
                    if (file.exists()) {
                        return EaseImageUtils.decodeScaleImage(thumbernailPath, 160, 160);
                    } else if (new File(thumbernailPath).exists()) {
                        return EaseImageUtils.decodeScaleImage(thumbernailPath, 160, 160);
                    }
                    return null;
                }

                protected void onPostExecute(Bitmap image) {
                    if (image != null) {
                        Drawable drawable = new BitmapDrawable(image);
                        iv.setBackground(drawable);
                        EaseImageCache.getInstance().put(thumbernailPath, image);
                    }
                }
            }.execute();

            return true;
        }
    }

    public static void showVideoThumbView(final String localThumb, final RelativeLayout iv) {
        // first check if the thumbnail image already loaded into cache
        Bitmap bitmap = EaseImageCache.getInstance().get(localThumb);
        if (bitmap != null) {
            // thumbnail image is already loaded, reuse the drawable
            Drawable drawable = new BitmapDrawable(bitmap);
            iv.setBackground(drawable);
        } else {
            new AsyncTask<Void, Void, Bitmap>() {

                @Override
                protected Bitmap doInBackground(Void... params) {
                    if (new File(localThumb).exists()) {
                        return ImageUtils.decodeScaleImage(localThumb, 160, 160);
                    } else {
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(Bitmap result) {
                    super.onPostExecute(result);
                    if (result != null) {
                        EaseImageCache.getInstance().put(localThumb, result);
                        Drawable drawable = new BitmapDrawable(result);
                        iv.setBackground(drawable);
                    }
                }
            }.execute();
        }

    }


}
