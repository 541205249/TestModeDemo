package com.eebbk.testmodetool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日期工具类
 * Created by Simon on 2016/10/4.
 */

public class DateUtils {
    private DateUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断闰年
     *
     * @param year 年份
     * @return {@code true}: 闰年<br>{@code false}: 平年
     */
    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }


    /**
     * 将时间格式成字符串
     *
     * @param date 日期
     * @param formatStr yyyy表示年, MM表示月, dd表示日, HH代表小时, mm代表分钟, ss代表秒
     */
    private static String format (Date date, String formatStr) {
        return format(date, generateDateFormat(formatStr));
    }

    /**
     * 将毫秒数格式化成时间字符串
     * <pre>
     *                          HH:mm    15:44
     *                         h:mm a    3:44 下午
     *                        HH:mm z    15:44 CST
     *                        HH:mm Z    15:44 +0800
     *                     HH:mm zzzz    15:44 中国标准时间
     *                       HH:mm:ss    15:44:40
     *                     yyyy-MM-dd    2016-08-12
     *               yyyy-MM-dd HH:mm    2016-08-12 15:44
     *            yyyy-MM-dd HH:mm:ss    2016-08-12 15:44:40
     *       yyyy-MM-dd HH:mm:ss zzzz    2016-08-12 15:44:40 中国标准时间
     *  EEEE yyyy-MM-dd HH:mm:ss zzzz    星期五 2016-08-12 15:44:40 中国标准时间
     *       yyyy-MM-dd HH:mm:ss.SSSZ    2016-08-12 15:44:40.461+0800
     *     yyyy-MM-dd'ToastUtils'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
     *   yyyy.MM.dd G 'at' HH:mm:ss z    2016.08.12 公元 at 15:44:40 CST
     *                         K:mm a    3:44 下午
     *               EEE, MMM d, ''yy    星期五, 八月 12, '16
     *          hh 'o''clock' a, zzzz    03 o'clock 下午, 中国标准时间
     *   yyyyy.MMMMM.dd GGG hh:mm aaa    02016.八月.12 公元 03:44 下午
     *     EEE, d MMM yyyy HH:mm:ss Z    星期五, 12 八月 2016 15:44:40 +0800
     *                  yyMMddHHmmssZ    160812154440+0800
     *     yyyy-MM-dd'ToastUtils'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
     * EEEE 'DATE('yyyy-MM-dd')' 'TIME('HH:mm:ss')' zzzz    星期五 DATE(2016-08-12) TIME(15:44:40) 中国标准时间
     * </pre>
     *
     * @param milliseconds  时间毫秒值
     * @param format       yyyy表示年, MM表示月, dd表示日, HH代表小时, mm代表分钟, ss代表秒
     */
    public static String format(long milliseconds, String format) {
        Date data = new Date(milliseconds);
        return format(data, format);
    }


    /**
     * 用指定的format 格式化时间
     *
     * @param millis     时间毫秒数
     */
    public static String format(long millis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(millis));
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为用户自定义</p>
     *
     * @param time   Date类型时间
     * @param format 时间格式
     * @return 时间字符串
     */
    private static String format(Date time, SimpleDateFormat format) {
        return format.format(time);
    }

    /**
     * 获取当前时间
     * <p>格式为用户自定义</p>
     *
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String getCurTimeString(SimpleDateFormat format) {
        return format(new Date(), format);
    }

    /***
     * 用指定的格式, 获取当前时间
     *
     * @param formatStr yyyy表示年, MM表示月, dd表示日, HH代表小时, mm代表分钟, ss代表秒
     */
    public static String getCurTimeString(String formatStr) {
        return format(new Date(), generateDateFormat(formatStr));
    }

    private static SimpleDateFormat generateDateFormat(String formatStr) {
        return new SimpleDateFormat(formatStr, Locale.getDefault());
    }
}
