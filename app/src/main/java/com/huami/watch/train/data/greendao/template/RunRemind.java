package com.huami.watch.train.data.greendao.template;

/**
 * Created by jinliang on 16/11/15.
 */

/**
 * 跑步类型文案模板
 */

public class RunRemind {

    private int id ;

    private String runType ; // 跑步， 休息， 游泳 ，

    private String trainContent;//训练内容太

    private String copyWrite;// 对应文案

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRunType() {
        return runType;
    }

    public void setRunType(String runType) {
        this.runType = runType;
    }

    public String getTrainContent() {
        return trainContent;
    }

    public void setTrainContent(String trainContent) {
        this.trainContent = trainContent;
    }

    public String getCopyWrite() {
        return copyWrite;
    }

    public void setCopyWrite(String copyWrite) {
        this.copyWrite = copyWrite;
    }

    @Override
    public String toString() {

        return new StringBuilder("[")
                .append("id:" + id)
                .append("runType:"+runType)
                .append("trainContent:"+ trainContent)
                .append("copyWrite:"+copyWrite)
                .append("]").toString();

    }
}
