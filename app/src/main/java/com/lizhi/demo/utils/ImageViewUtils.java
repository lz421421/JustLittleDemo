package com.lizhi.demo.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by Administrator on 2016/6/23.
 */
public class ImageViewUtils {


    public static  Bitmap scaleImageView(Bitmap bitmap,int width){
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        //缩放
        Matrix matrix = new Matrix();
        float w_scale = width * 1.0f / bitmapWidth;
        matrix.setScale(w_scale, w_scale);
        Bitmap thumb = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, true);
        return thumb;
    }

}
