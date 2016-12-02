package com.huami.watch.train.data.greendao.template;

import java.util.List;

/**
 * Created by jinliang on 16/11/10.
 *
 *  trainPlan 根节点
 */


public class DayTrainPlanRoot {


    private List<DayTrainPlan> dayTrainPlanList;


    public List<DayTrainPlan> getDayTrainPlanList() {
        return dayTrainPlanList;
    }

    public void setDayTrainPlanList(List<DayTrainPlan> dayTrainPlanList) {
        this.dayTrainPlanList = dayTrainPlanList;
    }
}
