package com.lizhi.demo.utils;


import android.graphics.Color;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;


import java.text.DecimalFormat;
import java.util.List;

public class StringUtil {

    static DecimalFormat df = new DecimalFormat("######0.00");

    /**
     * 判断字符串是否为null或�?空字符串
     *
     * @param str
     * @return
     */
    public static boolean isNullOrEmpty(String str) {
        boolean result = false;
        if (null == str || "".equals(str.trim())) {
            result = true;
        }
        return result;
    }

    /**
     * 如果i小于10，添�?后生成string
     *
     * @param i
     * @return
     */
    public static String addZreoIfLessThanTen(int i) {

        String string = "";
        int ballNum = i + 1;
        if (ballNum < 10) {
            string = "0" + ballNum;
        } else {
            string = ballNum + "";
        }
        return string;
    }

    /**
     * @param string
     * @return
     */
    public static boolean isNotNull(String string) {
        return null != string && !"".equals(string.trim());
    }

    /**
     * 去掉�?��字符串中的所有的单个空格" "
     *
     * @param string
     */
    public static String replaceSpaceCharacter(String string) {
        if (null == string || "".equals(string)) {
            return "";
        }
        return string.replace(" ", "");
    }

    /**
     * 获取小数位为6位的经纬�?
     *
     * @param string
     * @return
     */
    public static String getStringLongitudeOrLatitude(String string) {
        if (StringUtil.isNullOrEmpty(string)) {
            return "";
        }
        if (string.contains(".")) {
            String[] splitArray = string.split("\\.");
            if (splitArray[1].length() > 6) {
                String substring = splitArray[1].substring(0, 6);
                return splitArray[0] + "." + substring;
            } else {
                return string;
            }
        } else {
            return string;
        }
    }

    public static String getNotNullString(String text) {
        if (text == null || "null".equals(text)) {
            return "";
        } else {
            return text;
        }
    }


    /**
     * 将list集合转为以逗号分隔的字符串
     *
     * @param list
     * @return
     */
    public static String listToString(List<String> list) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            builder.append(list.get(i) + ",");
        }
        String ids = new String(builder);
        String ids2 = "";
        if (ids.length() > 0) {
            ids2 = ids.substring(0, ids.lastIndexOf(","));
        }
        return ids2;
    }

    /**
     * 保留小数点后两位
     *
     * @param dou
     * @return
     */
    public static String getDoubleTwo(double dou) {
        return df.format(dou);
    }

    /**
     * 检测String是否全是中文
     */
    public static boolean isChineseString(String str) {
        boolean res = true;
        char[] cTemp = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {
            if (!isChinese(cTemp[i])) {
                res = false;
                break;
            }
        }
        return res;
    }

    /**
     * 判断字符是否是汉字
     */
    public static boolean isChinese(Character character) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(character);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }


    /**
     * 把服务器返回的时间 转化成  Y年M月D日 H M S
     *
     * @param serverTime   服务器返回时间
     * @param isHoueMinute 是否需要 小时 分钟 和秒
     * @return
     */
    public static String getDataFromServer(String serverTime, boolean isHoueMinute) {


        if (StringUtil.isNullOrEmpty(serverTime)) {
            return "";
        } else if (serverTime.contains("T")) {
            String datas[] = serverTime.split("T");
            serverTime = datas[0];
            String arr[] = serverTime.split("-");
            String year = arr[0];
            String month = arr[1];
            String day = arr[2];
            if (isHoueMinute) {
                String hms = datas[1];
                hms = hms.substring(0, 8);
                return year + "-" + month + "-" + day + " " + hms;
            } else {
                return year + "年" + month + "月" + day + "日";
            }
        } else {
            return serverTime;
        }
    }


    public static TextView setSpannableText(TextView textView, String chooseText, String content) {
        if (isNullOrEmpty(chooseText)) {
            textView.setText(content);
            return textView;
        }
        int fromLengthF = 0;
        int toLengthF = chooseText.length();
        String s = chooseText + " " + content;
        Spannable span1 = Spannable.Factory.getInstance().newSpannable(s);
        span1.setSpan(new BackgroundColorSpan(Color.parseColor("#e94848")), fromLengthF, toLengthF, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        span1.setSpan(new ForegroundColorSpan(Color.WHITE), fromLengthF, toLengthF, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(span1, TextView.BufferType.SPANNABLE);
        return textView;
    }


    public static TextView setSpannableText_DianJi(TextView textView, String chooseText, String content, ClickableSpan clickableSpan) {
        if (isNullOrEmpty(chooseText)) {
            textView.setText(content);
            return textView;
        }
        int toLengthF = chooseText.length();
        String s = chooseText + "" + content;
        int length = s.length();
        Spannable span1 = Spannable.Factory.getInstance().newSpannable(s);
        //        span1.setSpan(new ForegroundColorSpan(Color.WHITE), fromLengthF, toLengthF, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        span1.setSpan(clickableSpan, toLengthF, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //        span1.setSpan(new TextAppearanceSpan(context, R.style.style0), fromLengthF, toLengthF, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //        span1.setSpan(new TextAppearanceSpan(context, R.style.style1), length - toLengthF, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(span1, TextView.BufferType.SPANNABLE);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        return textView;
    }


    /**
     * 截取指定长度字符串 多余用...表示
     *
     * @return
     */
    public static String jieQu(String s, int length) {
        int sLength = s.length();
        if (sLength >= length) {
            s = s.substring(0, 12) + "...";
            return s;
        } else {
            return s;
        }
    }


/*
    */
/**
     * 当 数目>9999时候 返回 9999+
     *
     * @param count
     * @return
     *//*

    public static String getMaxIntString(int count) {
        if (count > Constants.maxInt) {
            return Constants.maxInt + "+";
        } else {
            return String.valueOf(count);
        }
    }
*/


    /**
     * @param input
     * @return
     */
    public static String ToSBC(String input) {
        return  input;
//        if (input == null){
//            return  "";
//        }
//        char c[] = input.toCharArray();
//        for (int i = 0; i < c.length; i++) {
//            if (c[i] == ' ') {
//                c[i] = '\u3000';
//            } else if (c[i] < '\177') {
//                c[i] = (char) (c[i] + 65248);
//            }
//        }
//        return new String(c);
    }

}
