package com.huami.watch.train.data.template.model;

/**
 * Created by jinliang on 16/11/10.
 */

import java.io.Serializable;

/**
 *  训练计划模板 template  其中是大的分类
 */
public class TrainPlan implements Serializable {


// ############### 常量的分类 #####################
    //新手
    public static final int  CATEGORY_XINSHOU = 0 ;

    // 5km
    public static final int  CATEGORY_5KM  =1 ;

    // 10km
    public static final int CATEGORY_10KM = 2 ;

    // 半马
    public static final  int CATEGORY_BANMA =3 ;

    // 全马
    public static final  int CATEGORY_QUANMA = 4 ;


//  ############## 常量分类结束 #####################


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

    public TrainPlan(int id, String title, int type, int orderId) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.orderId = orderId;
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
}
