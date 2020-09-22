package com.xjx.helper.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
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
}
