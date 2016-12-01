package com.huami.watch.train.utils;

import android.content.Context;

import com.huami.watch.template.model.DayTrainPlan;
import com.huami.watch.template.model.RunRemind;
import com.huami.watch.template.model.TrainPlan;
import com.huami.watch.train.data.template.XMLDayTrainPlanHandler;
import com.huami.watch.train.data.template.XMLRunRemindHandler;
import com.huami.watch.train.data.template.XMLTrainPlanHandler;

import org.xml.sax.helpers.AttributesImpl;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by jinliang on 16/11/10.
 */

public class SAXUtils {


    /**
     * 通过 xml 文件中的数据获取 解析 trainPlan 集合
     * @param mContext
     * @return
     */
    public static  List<TrainPlan> getTrainPlanFromXml(Context mContext){

        try {
            //实例化SAX工厂类
            String filePath = FileUtils.getFilePathByCategoryType(
                    mContext,FileUtils.MODEL_TRAIN_PLAN,Constant.TRAIN_PLAN_CATEGORY_PLAN_SUMMMARY);
            InputStream inputStream = mContext.getAssets().open(filePath);
            SAXParserFactory factory=SAXParserFactory.newInstance();
            //实例化SAX解析器。
            SAXParser sParser=factory.newSAXParser();
            //实例化工具类MyHandler，设置需要解析的节点
            XMLTrainPlanHandler myHandler=new XMLTrainPlanHandler();

            // 开始解析
            sParser.parse(inputStream, myHandler);

            // 解析完成之后，关闭流
            inputStream.close();
            //返回解析结果。
            return myHandler.getTrainPlans();  //在这里返回解析之后的数据
        } catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取的是当前的训练计划
     * @return
     */
    public static TrainPlan getCurrentTrainPlanFromXml(Context mContext ,int id){
        List<TrainPlan>  trainPlanList =getTrainPlanFromXml(mContext);
        TrainPlan trainPlan = null ;
        for (TrainPlan item: trainPlanList ) {
            if(item.getId()==id){
                trainPlan = item ;
                break;
            }
        }
        return trainPlan ;
    }


    /**
     * 获取的是每天的训练集合
     * @param mContext
     * @param categoryType 分类型数据
     * @return
     */
    public  static  List<DayTrainPlan> getDayTrainPlansFromXml(Context mContext ,int categoryType){

        try {
            //实例化SAX工厂类
            String filePath = FileUtils.getFilePathByCategoryType(
                    mContext,FileUtils.MODEL_TRAIN_PLAN,categoryType);
            InputStream inputStream = mContext.getAssets().open(filePath);
            SAXParserFactory factory=SAXParserFactory.newInstance();
            //实例化SAX解析器。
            SAXParser sParser=factory.newSAXParser();
            //实例化工具类MyHandler，设置需要解析的节点
            XMLDayTrainPlanHandler myHandler=new XMLDayTrainPlanHandler();

            // 开始解析
            sParser.parse(inputStream, myHandler);

            // 解析完成之后，关闭流
            inputStream.close();
            //返回解析结果。
            return myHandler.getDayTrainPlanList();  //在这里返回解析之后的数据
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    /**
     * 获取的跑步提醒的集合
     * @param mContext
     * @return
     */
    public static List<RunRemind> getRunRemindsFromXml(Context mContext){
        try {
            //实例化SAX工厂类
            String filePath = FileUtils.getFilePathByCategoryType(
                    mContext,FileUtils.MODEL_TRAIN_PLAN,Constant.TRAIN_PLAN_RUNING_WRITE_COPY);
            InputStream inputStream = mContext.getAssets().open(filePath);
            SAXParserFactory factory=SAXParserFactory.newInstance();
            //实例化SAX解析器。
            SAXParser sParser=factory.newSAXParser();
            //实例化工具类MyHandler，设置需要解析的节点
            XMLRunRemindHandler myHandler=new XMLRunRemindHandler();

            // 开始解析
            sParser.parse(inputStream, myHandler);

            // 解析完成之后，关闭流
            inputStream.close();
            //返回解析结果。
            return myHandler.getRunRemindList();  //在这里返回解析之后的数据
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 通过 remindId 获取的是当前的提醒的文案内容
     * @param mContext
     * @param runmindId
     * @return
     */
    public static RunRemind getCurrentRunmindFromXml(Context mContext ,int runmindId){
        List<RunRemind> runRemindList =getRunRemindsFromXml(mContext);
        RunRemind  currentRunRemind  = null;
        for (RunRemind item: runRemindList ) {
            if(item.getId()==runmindId){
                currentRunRemind = item;
                break;
            }
        }
        return currentRunRemind;
    }

    /**
     * 将 tainPlan 写入文件中
     * @param mContext
     */
    public static void  writeTrainPlanToXml(Context mContext,List<TrainPlan> trainPlans){
        //实例化SAX工厂类
        String filePath = FileUtils.getFilePathByCategoryType(
                mContext,FileUtils.MODEL_TRAIN_PLAN,Constant.TRAIN_PLAN_CATEGORY_PLAN_SUMMMARY);
        writeTrainPlanToXml(trainPlans,filePath);

    }







    public static void writeTrainPlanToXml(List<TrainPlan> trainPlans ,String filePath) {
            String  node = trainPlans.getClass().getSimpleName().toLowerCase();
            String root = node +"s";
            try {
                FileWriter fw = new FileWriter(
                        filePath);
                //初始化SAXTransformerFactory
                SAXTransformerFactory factory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
                TransformerHandler handler = factory.newTransformerHandler();

                Transformer transformer = handler.getTransformer();     // 设置xml属性
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
                transformer.setOutputProperty(OutputKeys.VERSION, "1.0");

                StreamResult result = new StreamResult(fw);      // 保存创建的xml
                handler.setResult(result);

                handler.startDocument();
                AttributesImpl attr = new AttributesImpl();

                attr.clear();

                handler.startElement("", "", root, attr);

                for (int i = 0; i < trainPlans.size(); i++) {

                    TrainPlan person = trainPlans.get(i);

                    attr.clear();
                    String id = String.valueOf(person.getId());
                    //添加属性id值
                    attr.addAttribute("", "", "id", "", id);
                    handler.startElement("", "", node, attr);
                    try {
                        Object object = Class.forName(node).newInstance();

                        Field[] fields = object.getClass().getDeclaredFields();
                        for (Field f:fields) {
                            //设置权限
                            f.setAccessible(true);
                            // 获取字段的名称
                            String field = f.toString().substring(f.toString().lastIndexOf(".")+1);         //取出属性名称
                            System.out.println("p1."+field+" --> "+f.get(person));

                            attr.clear();
                            handler.startElement("", "",field, attr);
                            String age = String.valueOf(f.get(person));
                            //添加标签值
                            handler.characters(age.toCharArray(), 0, age.length());
                            handler.endElement("", "",field);

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    handler.endElement("", "", node);
                }

                handler.endElement("", "",root);
                handler.endDocument();
            } catch (Exception e) {
                e.printStackTrace();
            }


    }

    public static String getFilePath(Context mContext ,String filePath){
        try {
            InputStream inputStream = mContext.getAssets().open(filePath);
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            int ch;
            while ((ch = inputStream.read()) != -1) {
                bytestream.write(ch);
            }
            byte imgdata[] = bytestream.toByteArray();
            bytestream.close();
            return new String(imgdata);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


    /**
     *  获取的是当期天的训练计划模板
     * @param mContext
     * @param categoryType
     * @param offsetTotal
     * @return
     */
    public static DayTrainPlan getCurrentDayTrainPlan(Context mContext ,int categoryType,int offsetTotal){

        List<DayTrainPlan> dayTrainPlanList = getDayTrainPlansFromXml(mContext,categoryType);

        DayTrainPlan dayTrainPlan =null;

        for (DayTrainPlan item : dayTrainPlanList) {
            if (item.getOffsetTotal()== offsetTotal){
                dayTrainPlan = item;
                break;
            }
        }

        return dayTrainPlan  ;

    }
}
