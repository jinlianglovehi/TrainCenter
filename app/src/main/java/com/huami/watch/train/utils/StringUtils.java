package com.huami.watch.train.utils;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.widget.TextView;

import com.huami.watch.train.R;

/**
 * Created by jinliang on 16/11/9.
 */

public class StringUtils {


     private static final String TAG = StringUtils.class.getSimpleName();

    public static boolean isEmpty(String data){

        if(data!=null){
            return  TextUtils.isEmpty(data);
        }
        return false;

    }


    /**
     * 计算每天训练记录的提醒的文案
     * @param runRemindType
     * @param param  可传递
     *               注意 rateStart,rateEnd
     * @return
     */
    public static String getDayTrainRecordCopyWrite(Context  mContext ,int runRemindType,String str, int... param){

        StringBuilder sb = new StringBuilder();
          switch (runRemindType){
              case Constant.SPORT_CATEGORY_RUN_SLOW: // 慢跑
                  sb.append(String.format(str,Utils.getRateNumber(mContext,param[0]),Utils.getRateNumber(mContext,param[1])));
                  break;
              case Constant.SPORT_CATEGORY_RUN_RESTORE: //恢复跑
                  sb.append(String.format(str,Utils.getRateNumber(mContext,param[0]),Utils.getRateNumber(mContext,param[1])));
                  break;
              case Constant.SPORT_CATEGORY_RUN_BASIC:// 基础跑
                  sb.append(String.format(str,Utils.getRateNumber(mContext,param[0]),Utils.getRateNumber(mContext,param[1])));
                  break;
              case Constant.SPORT_CATEGORY_RUN_ADVANCED://进阶跑
                  sb.append(String.format(str,Utils.getRateNumber(mContext,param[0]),Utils.getRateNumber(mContext,param[1])));

                  break;
              case Constant.SPORT_CATEGORY_RUN_FATELEK:// 法特莱克跑
                  sb.append(String.format(str,Utils.getRateNumber(mContext,param[0]),Utils.getRateNumber(mContext,param[1])));
                  break;
              case Constant.SPORT_CATEGORY_RUN_HILLSIDE:// 山坡跑
                  sb.append(String.format(str,Utils.getRateNumber(mContext,param[0]),Utils.getRateNumber(mContext,param[1])));
                  break;
              case Constant.SPORT_CATEGORY_RUN_RHYTHM:// 节奏跑
                  sb.append(String.format(str,Utils.getRateNumber(mContext,param[0]),Utils.getRateNumber(mContext,param[1])));
                  break;
              case Constant.SPORT_CATEGORY_RUN_INTERMITTENT_SHORT:// 跑步 短周期
                  sb.append(String.format(str,Utils.getRateNumber(mContext,param[0]),Utils.getRateNumber(mContext,param[1])));
                  break;
              case Constant.SPORT_CATEGORY_RUN_INTERMITTENT_LONG://跑步 长周期
                  sb.append(String.format(str,Utils.getRateNumber(mContext,param[0]),Utils.getRateNumber(mContext,param[1])));
                  break;
              case Constant.SPORT_CATEGORY_REST:// 休息
                  sb.append(String.format(str, param[0]));
                  break;
              case Constant.SPORT_CATEGORY_SWIMMING:// 游泳
                  sb.append(str);
                  break;
              case Constant.SPORT_CATEGORY_RIDING:// 骑行
                  sb.append(String.format(str,Utils.getRateNumber(mContext,param[0]),Utils.getRateNumber(mContext,param[1])));
                  break;

          }

        return sb.toString();
    }

    /**
     * 获取的 基础极端 第1周1天
     * @param mContext
     * @param stag
     * @param offsetDays
     * @return
     */
    public static String getDayTrainRecordStag(Context mContext , String stag, int offsetDays){

        int weekly = offsetDays /7 + 1 ;
        int days = offsetDays % 7+1 ;
        String dayTrainRecordStag = mContext.getResources().getString(R.string.day_train_record_stag);
        String.format(dayTrainRecordStag,stag,weekly,days);
        LogUtils.print(TAG, "stag:"+stag +",weekly:"+weekly +",days:"+days);
        return String.format(dayTrainRecordStag,stag,weekly,days);

    }

    public static String replaceNewLine(String string ){

        return Html.fromHtml(string.replace("|","<br>")).toString();
    }


}
