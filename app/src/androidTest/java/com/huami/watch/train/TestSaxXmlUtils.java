package com.huami.watch.train;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.huami.watch.template.model.TrainPlan;
import com.huami.watch.train.data.template.XMLTrainPlanHandler;
import com.huami.watch.train.utils.Constant;
import com.huami.watch.train.utils.FileUtils;
import com.huami.watch.train.utils.SAXUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xml.sax.helpers.AttributesImpl;

import java.io.FileWriter;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 *  sax xml 解析单元测试工具类
 */
@RunWith(AndroidJUnit4.class)
public class TestSaxXmlUtils {


    private Context appContext ;
    @Before
    public void getAppContext(){
         appContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void createTrainPlanXmlFile() {
        List<TrainPlan> trainPlanList =SAXUtils.getTrainPlanFromXml(appContext);
        try {

            List<TrainPlan> trainPlans =SAXUtils.getTrainPlanFromXml(appContext);
            System.out.println("data:"+ trainPlans.size());
            for (TrainPlan trainPlan: trainPlanList
                    ) {
                System.out.println("data:"+ trainPlan.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


//    @Test
    public void writeTrainPlanToXml(){

        List<TrainPlan> trainPlanList = new ArrayList<TrainPlan>();
//
//        TrainPlan xinshou = new TrainPlan(1,"新手上路",1,1);
//        trainPlanList.add(xinshou);
//
//        TrainPlan train_5km = new TrainPlan(2,"5km",2,2);
//
//        trainPlanList.add(train_5km);
//
//
//        TrainPlan train_10km = new TrainPlan(3,"10km",3,3);
//
//        trainPlanList.add(train_10km);
//
//
//        TrainPlan train_banma = new TrainPlan(4,"半马",4,4);
//
//        trainPlanList.add(train_banma);
//
//        TrainPlan train_quanma =new TrainPlan(5,"全马",5,5);
//
//        trainPlanList.add(train_quanma);

        writeTrainPlanToXml(appContext,trainPlanList);



    }

    /**
     *
     * @param mContext
     * @param trainPlans
     */
    public static void  writeTrainPlanToXml(Context mContext,List<TrainPlan> trainPlans){

        //实例化SAX工厂类
        String filePath = FileUtils.getFilePathByCategoryType(
                mContext,FileUtils.MODEL_TRAIN_PLAN, Constant.TRAIN_PLAN_CATEGORY_PLAN_SUMMMARY);

        filePath = "file:///android_asset/" + filePath;
        System.out.println("filePath:"+ filePath);
        writeTrainPlanToXml(trainPlans,filePath);

    }

    /**
     *
     * @param trainPlans
     * @param filePath
     */
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
}
