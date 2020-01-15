package com.alan.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    public static final int ONE_SECOND_MILLIS = 1000;
    public static final int ONE_MINUTE_MILLIS = 60 * ONE_SECOND_MILLIS;
    public static final int ONE_HOUR_MILLIS = 60 * ONE_MINUTE_MILLIS;
    public static final int ONE_DAY_MILLIS = 24 * ONE_HOUR_MILLIS;

    /**
     * 是否是今天
     *
     * @param time
     * @return
     */
    public static boolean isToday(long time) {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.MILLISECOND, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.HOUR_OF_DAY, 0);
        long start = now.getTimeInMillis();
        long end = start + ONE_DAY_MILLIS;
        return time >= start && time <= end;
    }

    /**
     * 获取当前时间的秒值
     *
     * @return
     */
    public static int getCurrentSecondsTime() {
        long l = System.currentTimeMillis();
        return (int) (l % 1000 == 0 ? l / 1000 : l / 1000 + 1);
    }


    public static int getCurrentMonth() {
        Calendar now = Calendar.getInstance();
        int i = now.get(Calendar.MONTH);
        return i + 1;
    }

    public static int getCurrentDay() {
        Calendar now = Calendar.getInstance();
        int i = now.get(Calendar.DAY_OF_MONTH);
        return i;
    }

    public static long getSomeDateMillionSenconds(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 0, 0, 0);
        return calendar.getTimeInMillis();
    }

    public static String getTimeStr(long time, String format) {
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public long getTodayStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

}
