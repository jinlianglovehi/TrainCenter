package com.huami.watch.train.textxml;

import com.huami.watch.train.base.BaseTest;
import com.huami.watch.train.data.greendao.template.DayTrainPlan;
import com.huami.watch.train.utils.Constant;
import com.huami.watch.train.utils.LogUtils;
import com.huami.watch.train.utils.SAXUtils;

import org.junit.Test;

import java.util.List;

/**
 * Created by jinliang on 16/11/15.
 */

public class TestGetXinShouXmlData extends BaseTest {


     private static final String TAG = TestGetXinShouXmlData.class.getSimpleName();

    /**
     * 测试获取的是全部的训练计划data;
     * @throws Exception
     */
//    @Test
    public void testGetXinShouData() throws  Exception{

       List<DayTrainPlan> dayTrainPlanList = SAXUtils.getDayTrainPlansFromXml(appContext,Constant.TRAIN_PLAN_CATEGORY_XINSHOU);


        for (DayTrainPlan dayTrainPlan : dayTrainPlanList
             ) {
            LogUtils.sysPrint(TAG,dayTrainPlan.toString());
        }

    }

    /**
     * 测试获取某一天的训练计划模板
     */
    @Test
    public void testCurrentDayTrainPlan(){

        DayTrainPlan currentDayTrainPlan =  null;
        for (int i = 0; i < 56 ; i++) {
             currentDayTrainPlan = SAXUtils.getCurrentDayTrainPlan(appContext, Constant.TRAIN_PLAN_CATEGORY_XINSHOU, i);
             LogUtils.sysPrint(TAG,"rubRemindId:" +currentDayTrainPlan.toString());
//            RunRemind  runMind = SAXUtils.getCurrentRunmindFromXml(appContext,currentDayTrainPlan.getRunremindId());
//            LogUtils.sysPrint(TAG,runMind.toString());
        }
    }

}

