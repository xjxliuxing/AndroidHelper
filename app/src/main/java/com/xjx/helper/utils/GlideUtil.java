package com.xjx.helper.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.xjx.helper.R;

/**
 * @作者 徐腾飞
 * @创建时间 2019/10/18  21:28
 * @更新者 HongJing
 * @更新时间 2019/10/18  21:28
 * @描述 图片加载工具类
 */
public class GlideUtil {

    private static GlideUtil glideUtil;

    public static GlideUtil getInstance() {
        if (glideUtil == null) {
            glideUtil = new GlideUtil();
        }
        return glideUtil;
    }

    /**
     * 加载普通带站位图的图片
     *
     * @param context
     * @param url
     * @param view
     * @param placeholder
     */
    @SuppressLint("CheckResult")
    public void Load(Activity context, String url, ImageView view, int placeholder) {

        if (context == null) {
            LogUtil.e("GlideUtil的Context对象为空！");
            return;
        }
        if (context.isFinishing()) {
            LogUtil.e("activty 已经关闭了！");
            return;
        }

        if (TextUtils.isEmpty(url)) {
            LogUtil.e("加载图片的路径为空！");
            return;
        }
        if (view == null) {
            LogUtil.e("加载图片的控件为空！");
            return;
        }

        // 配置属性
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.ALL);

        if (placeholder > 0) {
            options.placeholder(placeholder);
            options.error(placeholder);
        }

        Glide
                .with(context)
                .load(url)
                .thumbnail(0.2f)// 20%的缩略图
                .apply(options)
                .into(view);
    }

    /**
     * 加载Context对象的ImageView
     *
     * @param context
     * @param url
     * @param view
     * @param placeholder
     */
    public void Load(Context context, String url, ImageView view, int placeholder) {

        if (context == null) {
            LogUtil.e("GlideUtil的Context对象为空！");
            return;
        }

        if (TextUtils.isEmpty(url)) {
            LogUtil.e("加载图片的路径为空！");
            return;
        }
        if (view == null) {
            LogUtil.e("加载图片的控件为空！");
            return;
        }

        // 配置属性
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.ALL);

        if (placeholder > 0) {
            options.placeholder(placeholder);
            options.error(placeholder);
        }

        Glide
                .with(context)
                .load(url)
                .thumbnail(0.2f)// 20%的缩略图
                .apply(options)
                .into(view);
    }

    public void Load(Activity context, Uri uri, ImageView view, int placeholder) {

        if (context == null) {
            LogUtil.e("GlideUtil的Context对象为空！");
            return;
        }
        if (context.isFinishing()) {
            LogUtil.e("activty 已经关闭了！");
            return;
        }

        if (uri == null) {
            LogUtil.e("加载图片的路径为空！");
            return;
        }
        if (view == null) {
            LogUtil.e("加载图片的控件为空！");
            return;
        }

        // 配置属性
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.ALL);

        if (placeholder > 0) {
            options.placeholder(placeholder);
            options.error(placeholder);
        }

        Glide
                .with(context)
                .load(uri)
                .thumbnail(0.2f)// 20%的缩略图
                .apply(options)
                .into(view);
    }

    /**
     * 加载带站位图的ViewGrounp图片
     *
     * @param context
     * @param url
     * @param viewGroup
     * @param placeholder
     */
    public void LoadViewGroup(Activity context, String url, final ViewGroup viewGroup,
                              int placeholder) {

        if (context == null) {
            LogUtil.e("GlideUtil的Context对象为空！");
            return;
        }
        if (context.isFinishing()) {
            LogUtil.e("activty 已经关闭了！");
            return;
        }

        if (TextUtils.isEmpty(url)) {
            LogUtil.e("加载图片的路径为空！");
            return;
        }
        if (viewGroup == null) {
            LogUtil.e("加载图片的控件为空！");
            return;
        }

        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.ALL);

        if (placeholder > 0) {
            options.centerCrop();
            options.placeholder(placeholder);
        }

        Glide.with(context)
                .load(url)
                .apply(options)
                .thumbnail(0.2f)// 20%的缩略图
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        viewGroup.setBackground(resource);
                    }
                });

    }

    /**
     * @param context
     * @param url
     * @param imageView
     * @param count     gif重复执行的次数
     */
    public void LoadGif(Activity context, String url, final ImageView imageView, final int count) {

        if (context == null) {
            LogUtil.e("GlideUtil的Context对象为空！");
            return;
        }
        if (context.isFinishing()) {
            LogUtil.e("activty 已经关闭了！");
            return;
        }

        if (TextUtils.isEmpty(url)) {
            LogUtil.e("加载图片的路径为空！");
            return;
        }

        if (imageView == null) {
            LogUtil.e("加载图片的控件为空！");
            return;
        }

        Glide.with(context).load(url).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable drawable, Transition<? super Drawable> transition) {
                if (drawable instanceof GifDrawable) {

                    GifDrawable gifDrawable = (GifDrawable) drawable;
                    if (count > 0) {
                        gifDrawable.setLoopCount(count);
                    }
                    imageView.setImageDrawable(drawable);
                    gifDrawable.start();
                }
            }
        });
    }

    /**
     * 获取图片某一帧的图片
     *
     * @param imageView
     * @param url
     * @param context
     */
    public void loadCover(ImageView imageView, String url, Context context) {

        if (imageView == null) {
            return;
        }
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (context == null) {
            return;
        }

        RequestOptions options = new RequestOptions();
        options.frame(4000000)
                .centerCrop();

        // 配置不适用缓存
        options
                // 不使用内存缓存
                .skipMemoryCache(true)
                // 不使用磁盘缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context)
                .setDefaultRequestOptions(
                        options
                )
                .load(url)
                .into(imageView);
    }

    /**
     * ----------------------------------- 3.x-----------------
     *
     * @param url       路径
     * @param imageView imageView
     */

    public void load(String url, ImageView imageView) {
//        Glide
//                .with(mContext)
//                .load(url)
//                .error(R.mipmap.img_error)
//                .placeholder(R.mipmap.iv_loading_round) //加载成功前显示的图片
//                .fallback(R.mipmap.iv_loading_round) //url为空的时候,显示的图片
//                .into(imageView);
    }
}
