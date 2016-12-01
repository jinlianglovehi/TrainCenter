package com.huami.watch.train.model;

/**
 * Created by jinliang on 16/11/18.
 *  用户信息表
 */

public class UserInfo {

    private int  gender ;
    private int  year ;
    private int month ;
    private Float weight ;
    private int height ;

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "gender=" + gender +
                ", year=" + year +
                ", month=" + month +
                ", weight=" + weight +
                ", height=" + height +
                '}';
    }
}
