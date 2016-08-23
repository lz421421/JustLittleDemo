package com.lizhi.demo.utils;

import android.content.Context;

public class DensityUtil {

    /**
     * 根据手机的分辨率�?dp 的单�?转成�?px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率�?px(像素) 的单�?转成�?dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     * @date 2013年7月23日
     */
    public static int getWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     * @date 2013年7月23日
     */
    public static int getHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }


    /**
     *          * 将px值转换为sp值，保证文字大小不变
     *          * 
     *          * @param pxValue
     *          * @param fontScale
     *          *            （DisplayMetrics类中属性scaledDensity）
     *          * @return
     *          
     */

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    /**
     *          * 将sp值转换为px值，保证文字大小不变
     *          * 
     *          * @param spValue
     *          * @param fontScale
     *          *            （DisplayMetrics类中属性scaledDensity）
     *          * @return
     *          
     */

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}