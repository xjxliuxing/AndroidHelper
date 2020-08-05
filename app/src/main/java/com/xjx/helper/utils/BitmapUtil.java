package com.xjx.helper.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.ViewGroup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2019/5/30.
 */

public class BitmapUtil {

    private static Bitmap bitmap;

    /**
     * @param path
     * @return 根据服务器上的图片路径，获取bitmap对象
     */
    public static Bitmap getBitmapForService(final String path) {
        Thread mThread = new Thread() {
            private InputStream inputStream;
            private HttpURLConnection conn;
            private URL url = null;

            @Override
            public void run() {
                try {
                    url = new URL(path);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    inputStream = conn.getInputStream();
                    Bitmap bitmap2 = BitmapFactory.decodeStream(inputStream);

                    if (bitmap2 != null) {
                        bitmap = changeColor(bitmap2);
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        mThread.start();
        try {
            mThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    //bitmap中的透明色用白色替换
    public static Bitmap changeColor(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int[] colorArray = new int[w * h];
        int n = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int color = getMixtureWhite(bitmap.getPixel(j, i));
                colorArray[n++] = color;
            }
        }
        return Bitmap.createBitmap(colorArray, w, h, Bitmap.Config.ARGB_8888);
    }

    //获取和白色混合颜色
    private static int getMixtureWhite(int color) {
        int alpha = Color.alpha(color);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.rgb(getSingleMixtureWhite(red, alpha), getSingleMixtureWhite(green, alpha),
                getSingleMixtureWhite(blue, alpha));
    }

    // 获取单色的混合值
    private static int getSingleMixtureWhite(int color, int alpha) {
        int newColor = color * alpha / 255 + 255 - alpha;
        return newColor > 255 ? 255 : newColor;
    }

    /**
     * 加载本地大图片
     */
    public static void LoadMorePhoto(Context context, int id, ViewGroup viewGroup) {
        // 获取屏幕宽高
        int screenWidth = ScreenUtil.getInstance().getScreenWidth();
        int screenHeight = ScreenUtil.getInstance().getScreenHeight();

        BitmapFactory.Options opts = new BitmapFactory.Options();
        //请求图片属性但不申请内存
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), id, opts);
        // 获取图片宽高
        int imageWidth = opts.outWidth;
        int imageHeight = opts.outHeight;

        // 图片的宽高除以屏幕宽高，算出宽和高的缩放比例，取较大值作为图片的缩放比例
        int scale = 1;
        int scaleX = imageWidth / screenWidth;
        int scaleY = imageHeight / screenHeight;
        if (scaleX >= scaleY && scaleX > 1) {
            scale = scaleX;
        } else if (scaleY > scaleX && scaleY > 1) {
            scale = scaleY;
        }

        // * 按缩放比例加载图片
        //设置缩放比例
        opts.inSampleSize = scale;
        //为图片申请内存
        opts.inJustDecodeBounds = false;
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), id, opts);

        Drawable drawable = new BitmapDrawable(bm);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            viewGroup.setBackground(drawable);
        }

    }

    /**
     * 加载本地的数据，转换成bitmap
     *
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
