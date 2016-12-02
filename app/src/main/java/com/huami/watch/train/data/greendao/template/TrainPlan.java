package com.huami.watch.train.data.greendao.template;

/**
 * Created by jinliang on 16/11/10.
 */

import java.io.Serializable;

/**
 *  训练计划模板 template  其中是大的分类
 */



public class TrainPlan implements Serializable {



    /**
     */
    private int  id ;


    /**
     *  运动的title
     */
    private String title ;


    /**
     * 表示的是新手 5km 10 km 之间的区别
     *
     */
    private int type ;


    /**
     * 排序的状态
     */
    private int  orderId ;

    /**
     * 训练类型的文案
     */
    private String copyWrite ;


    private double totalLength ;// 总的长度

    private int totalDays;// 总的训练天数

    public TrainPlan() {

    }

    public TrainPlan(int id, String title,String copyWrite, int type, int orderId) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.orderId = orderId;
        this.copyWrite = copyWrite;

    }

    public String getCopyWrite() {
        return copyWrite;
    }

    public void setCopyWrite(String copyWrite) {
        this.copyWrite = copyWrite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }



    @Override
    public String toString() {
        return "TrainPlan{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", orderId=" + orderId +
                ", copyWrite='" + copyWrite + '\'' +
                ", totalLength=" + totalLength +
                ", totalDays=" + totalDays +
                '}';
    }

    public double getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(double totalLength) {
        this.totalLength = totalLength;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }
}
