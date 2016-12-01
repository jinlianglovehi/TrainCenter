package com.huami.watch.train.testutils;

import android.nfc.Tag;
import android.text.format.DateUtils;

import com.huami.watch.train.base.BaseTest;
import com.huami.watch.train.utils.DataUtils;
import com.huami.watch.train.utils.LogUtils;

import org.junit.Test;

import java.util.Date;

/**
 * Created by jinliang on 16/11/16.
 */

public class TestUtils  extends BaseTest{


     private static final String TAG = TestUtils.class.getSimpleName();
//    @Test
    public void test(){

        Date startDate = new Date() ;
        try {
            Thread.sleep(10000l);
            Date endDate  = new Date() ;
            long days = DataUtils.getOffsetDaysFromStartData(startDate,endDate);
            LogUtils.sysPrint(TAG,"days:"+days);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

//    @Test
    public void str(){
        String string = "以慢跑800m，步行200m为周期完成。推荐心率%1$d-%2$dbpm";

       String result =  String.format(string,100,200);

        LogUtils.sysPrint(TAG,result );
    }



//    @Test
    public void weekComputers(){

        LogUtils.print(TAG, "weekComputers");
        for (int i = 0; i < 56; i++) {
           initData(i);
        }

    }
    private void initData(int offsetDays){
       int weeekly =  offsetDays /7+ 1 ;

        int days = offsetDays % 7+1 ;
        LogUtils.sysPrint(TAG,"第"+ weeekly +"周" +"第"+days+"天");
    }


    /**
     * 单元测试偏移周数
     */
//    @Test
    public void testOffsetWeekly(){
        for (int i = 0; i < 56; i++) {
//            initWeeklyData(i);
        }

    }

//    @Test
//    public void getTimeDays(){
//       String result = DataUtils.getDateByOffsetDays(new Date().getTime()/1000,2);
//        LogUtils.sysPrint(TAG,"testTime:"+result);
//    }
//    private void initWeeklyData(int offsetDays){
//
//
//    }
}
