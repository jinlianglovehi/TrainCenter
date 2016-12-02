package com.huami.watch.train.data.greendao.template;

import java.util.List;

/**
 * Created by jinliang on 16/11/10.
 *
 *  trainPlan 根节点
 */

public class TrainPlanRoot {


    private List<TrainPlan> trainPlanList ;

    public List<TrainPlan> getTrainPlanList() {
        return trainPlanList;
    }

    public void setTrainPlanList(List<TrainPlan> trainPlanList) {
        this.trainPlanList = trainPlanList;
    }
}
