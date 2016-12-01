package com.huami.watch.template.model;

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

      public double distance ;// 需要跑的距离

      public String rate ;// 心率范围

      public int  offsetWeeks ;  // 第几周

      public int  offsetDays;// 第几周的第几天

      public int offsetTotal ;//偏离开始的天数

    private int runremindId ;//  跑步提醒的Id

    private int rateStart; // 心率的起始位置 或者是休息的距离开始训练的天数
    private int rateEnd ; // 心率的终结位置


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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
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

    public int getRunremindId() {
        return runremindId;
    }

    public void setRunremindId(int runremindId) {
        this.runremindId = runremindId;
    }

    public int getRateStart() {
        return rateStart;
    }

    public void setRateStart(int rateStart) {
        this.rateStart = rateStart;
    }

    public int getRateEnd() {
        return rateEnd;
    }

    public void setRateEnd(int rateEnd) {
        this.rateEnd = rateEnd;
    }

    @Override
    public String toString() {
       return new StringBuilder("[")
               .append("id:"+id)
               .append("runremindId:"+runremindId)
               .append("desc:"+ desc)
               .append("stageDesc:"+stageDesc)
               .append("distance:"+distance)
               .append("rate:"+rateStart +"-"+rateEnd)
               .append("offsetTotal:"+offsetTotal)
               .append("]").toString();
    }
}
