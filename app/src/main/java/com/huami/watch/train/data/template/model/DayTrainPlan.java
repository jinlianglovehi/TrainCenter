package com.huami.watch.train.data.template.model;

import java.io.Serializable;

/**
 * Created by jinliang on 16/11/10.
 *
 *  每天的训练计划
 */

public class DayTrainPlan implements Serializable {

      private  int id ; // 每天的记录数

      private String desc ;// 每天的提描述

      public String stageDesc ;// 阶段性描述

      public int distance ;// 需要跑的距离

      public String rate ;// 心率范围

      public int  offsetWeeks ;  // 第几周

      public int  offsetDays;// 第几周的第几天

      public int offsetTotal ;//偏离开始的天数


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStageDesc() {
        return stageDesc;
    }

    public void setStageDesc(String stageDesc) {
        this.stageDesc = stageDesc;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public int getOffsetWeeks() {
        return offsetWeeks;
    }

    public void setOffsetWeeks(int offsetWeeks) {
        this.offsetWeeks = offsetWeeks;
    }

    public int getOffsetDays() {
        return offsetDays;
    }

    public void setOffsetDays(int offsetDays) {
        this.offsetDays = offsetDays;
    }

    public int getOffsetTotal() {
        return offsetTotal;
    }

    public void setOffsetTotal(int offsetTotal) {
        this.offsetTotal = offsetTotal;
    }
}
