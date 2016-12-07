package com.huami.watch.greendaogenerator;


import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by jinliang on 16/11/9.
 */

public class TrainCenterDB {

    public static void main(String[] args) throws Exception {
        // 正如你所见的，你创建了一个用于添加实体（Entity）的模式（Schema）对象。
        // 两个参数分别代表：数据库版本号与自动生成代码的包路径。
        Schema schema = new Schema(1, "com.huami.watch.train.model");
//      当然，如果你愿意，你也可以分别指定生成的 Bean 与 DAO 类所在的目录，只要如下所示：
//      Schema schema = new Schema(1, "me.itangqi.bean");
        schema.setDefaultJavaPackageDao("com.huami.watch.train.model");

        // 模式（Schema）同时也拥有两个默认的 flags，分别用来标示 entity 是否是 activie 以及是否使用 keep sections。
        // schema2.enableActiveEntitiesByDefault();
        // schema2.enableKeepSectionsByDefault();


        addTrainRecord(schema);

        addDayTrainRecord(schema);
        new DaoGenerator().generateAll(schema, "/Users/jinliang/Downloads/bleProject/TrainCenter/app/src/main/java-gen");
    }

    /**
     * 一个训练项目
     * @param
     */
    private static void addTrainRecord(Schema schema) {
        // 一个实体（类）就关联到数据库中的一张表，此处表名为「Note」（既类名）
        Entity trainRecord = schema.addEntity("TrainRecord");
        // 你也可以重新给表命名
        trainRecord.setTableName("TrainRecord");
        trainRecord.addIdProperty().primaryKey().autoincrement();
        trainRecord.addIntProperty("trainPlanId");
        trainRecord.addIntProperty("trainType");// 训练类型
        trainRecord.addStringProperty("trainTitle");// 训练标题  //废弃
//        trainRecord.addStringProperty("copywriter");// 文案  // 设置的是动态的属性
        trainRecord.addIntProperty("trainStatus");// 训练状态 进行中 或者是训练完成
        trainRecord.addDateProperty("startData");// 开始时间
        trainRecord.addDateProperty("endData");// 结束时间
        trainRecord.addIntProperty("trainDays");// 训练天数
        trainRecord.addIntProperty("totalDays");// 总的训练天数
        trainRecord.addDoubleProperty("trainTotalLength");//已经训练的总里程
        trainRecord.addDoubleProperty("totalLength");// 一项训练计划的总里程。

        trainRecord.addIntProperty("lastTrainOffsetDays");// 最后的训练天数



    }

    /**
     * 每天的训练记录
     * @param schema
     */
    private static void addDayTrainRecord(Schema schema){

        Entity dayTrainRecord = schema.addEntity("DayTrainRecord");

        dayTrainRecord.setTableName("DayTrainRecord");

        dayTrainRecord.addIdProperty().primaryKey().autoincrement();//id ;


        dayTrainRecord.addLongProperty("trainRecordId");// 记录的是训练的id


        dayTrainRecord.addIntProperty("trainType");// 添加训练类型，五公里 10km 之间的分类
        dayTrainRecord.addIntProperty("rateStart");// 心率最小百分比 *100  计算方式还需要自己计算

        dayTrainRecord.addIntProperty("rateEnd");// 心率的最大百分百 * 100 计算方式自己解决

        dayTrainRecord.addIntProperty("offsetDays");// 从开始偏移的天数

        dayTrainRecord.addIntProperty("dayTrainStatus");// 每天的训练状态

        dayTrainRecord.addDoubleProperty("distance");// 每天的训练长度

        dayTrainRecord.addIntProperty("runremindId"); // 每天的训练提醒id

        dayTrainRecord.addIntProperty("sportType");// 方式

        dayTrainRecord.addIntProperty("swimTimeMin");// 游泳min
        dayTrainRecord.addIntProperty("swimTimeMax");// 游泳max




    }

}
