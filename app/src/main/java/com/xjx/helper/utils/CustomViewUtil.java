package com.xjx.helper.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

public class CustomViewUtil {
    
    public static Bitmap getBitmapDefault(Drawable drawable) {
        Bitmap bitmap = null;
        if (drawable == null) {
            return bitmap;
        }
        
        if (drawable instanceof StateListDrawable) {
            Drawable current = drawable.getCurrent();
            if (current instanceof BitmapDrawable) {
                bitmap = ((BitmapDrawable) current).getBitmap();
            }
        } else if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        }
        
        return bitmap;
    }
    
    public static Bitmap getBitmapScale(Drawable drawable, float scale) {
        Bitmap bitmap = null;
        if (drawable == null) {
            return bitmap;
        }
        
        if (drawable instanceof StateListDrawable) {
            Drawable current = drawable.getCurrent();
            if (current instanceof BitmapDrawable) {
                bitmap = ((BitmapDrawable) current).getBitmap();
            }
        } else if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        }
        
        int bmpWidth = bitmap.getWidth();
        int bmpHeight = bitmap.getHeight();
        
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        
        return Bitmap.createBitmap(bitmap, 0, 0, bmpWidth, bmpHeight, matrix, true);
    }
    
    /**
     * @param drawable 原始的drawable
     * @param width    指定的宽
     * @param height   指定的高
     * @return 根据指定的高度，返回一个新的bitmap
     */
    public static Bitmap getBitmapScale(Drawable drawable, float width, float height) {
        Bitmap bitmap = null;
        if (drawable == null) {
            return bitmap;
        }
        
        if (drawable instanceof StateListDrawable) {
            Drawable current = drawable.getCurrent();
            if (current instanceof BitmapDrawable) {
                bitmap = ((BitmapDrawable) current).getBitmap();
            }
        } else if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        }
        
        int bmpWidth = bitmap.getWidth();
        int bmpHeight = bitmap.getHeight();
        
        float scaleWidth = width / bmpWidth;
        float scaleHeight = height / bmpHeight;
        
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        
        return Bitmap.createBitmap(bitmap, 0, 0, bmpWidth, bmpHeight, matrix, true);
    }
    
    public static Bitmap getBitmapScaleWidth(Drawable drawable, float height) {
        Bitmap bitmap = null;
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof StateListDrawable) {
            Drawable current = drawable.getCurrent();
            if (current instanceof BitmapDrawable) {
                bitmap = ((BitmapDrawable) current).getBitmap();
            }
        } else if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        }
        if (bitmap != null) {
            
            int bmpWidth = bitmap.getWidth();
            int bmpHeight = bitmap.getHeight();
            
            float scaleHeight = height / bmpHeight;
            
            Matrix matrix = new Matrix();
            matrix.postScale(scaleHeight, scaleHeight);
            
            return Bitmap.createBitmap(bitmap, 0, 0, bmpWidth, bmpHeight, matrix, true);
        } else {
            return null;
        }
    }
    
    /**
     * @return 求出中线到基线的距离
     */
    public static float getBaseline(Paint p) {
        Paint.FontMetrics fontMetrics = p.getFontMetrics();
        return (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent;
    }
    
    /**
     * @param paint   画笔
     * @param content 内容
     * @return 获取绘制textView的宽高，第一个返回的是宽，第二个返回的是高
     */
    public static int[] getTextSize(Paint paint, String content) {
        int[] ints = new int[2];
        Rect rect = new Rect();
        paint.getTextBounds(content, 0, content.length(), rect);
        ints[0] = rect.width();
        ints[1] = rect.height();
        return ints;
    }
}
