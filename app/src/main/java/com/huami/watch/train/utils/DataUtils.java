package com.huami.watch.train.utils;

import android.content.Context;

import com.huami.watch.train.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jinliang on 16/11/16.
 */

public class DataUtils {
 private static final String TAG = DataUtils.class.getSimpleName();
    /**
     * 计算从创建这个训练计划 到今天一共偏移几天
     * @return
     */
    public static int getOffsetDaysFromStartData(Date startDate,Date endDate){

        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }

    /**
     * 获取偏移的天数
     * @param startDate
     * @param offset
     * @return
     */
    public static Date getOffsetDateFromStartDate(Date startDate,int offset){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_MONTH, offset);
        return calendar.getTime();
    }

    /**
     *  计算从 startDate 到 endDate 第几周
     *  计算如果<=8 是正确的， 如果大于8 就是错误
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getCurrentWeekNumber(Date startDate,Date endDate){
        int offsetDays = getOffsetDaysFromStartData(startDate,endDate) ;
        int weeekly =  offsetDays /7+ 1 ;
        return weeekly<=8? weeekly:-1;
    }

    /**
     *
     * @param startSeconds 以s 作为计时单位
     * @return
     */
    public static int getOffsetDaysToToday(long startSeconds){
        Date startDate = new Date(startSeconds*1000);
        int offsetDate = getOffsetDaysFromStartData(startDate,new Date());
        return offsetDate;
    }

    /**
     * 处理日期的处理
     * 这个方法是专门提供给 当前的训练详情的记录中时间的计算
     * @param startSeconds
     * @param offsetDays
     * @return
     */
    public static String getDateByOffsetDays(Context mContext , long startSeconds , int offsetDays,int todyOffsetDays){
        Date startDate = new Date(startSeconds*1000);
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);

        fromCalendar.add(Calendar.DAY_OF_MONTH, +offsetDays);

        int week = fromCalendar.get(Calendar.DAY_OF_WEEK);
        int realWeek = week - 1;
        if (realWeek < 0){ realWeek = 0;}
        String[] weekStrs =mContext.getResources().getStringArray(R.array.simple_weekdays);

        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        int startYear =Integer.parseInt(yearFormat.format(startDate));

        int thisYear = Integer.parseInt(yearFormat.format(fromCalendar.getTime()));

        StringBuilder sb = new StringBuilder();
//        boolean isSameDay = isSameDay(startDate,fromCalendar.getTime());
        if(offsetDays==todyOffsetDays){// 是否同一天
            return sb.append(mContext.getString(R.string.today)).append("   "+weekStrs[realWeek]).toString();
        }else if(thisYear>startYear){//是否跨年
            return  sb.append(new SimpleDateFormat("yyyy/MM/dd").format(fromCalendar.getTime())).append("   "+weekStrs[realWeek]).toString();
        }else {
            return sb.append(new SimpleDateFormat("MM/dd").format(fromCalendar.getTime()).toString()).append("   "+weekStrs[realWeek]).toString();
        }

    }

    public static boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }

    public static String getDateFormat(Long time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
       return  simpleDateFormat.format(new Date(time)).toString();

    }

}
