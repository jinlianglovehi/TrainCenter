package com.huami.watch.train.data.template;

import com.huami.watch.train.data.greendao.template.DayTrainPlan;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinliang on 16/11/11.
 *  处理 每天的训练计划
 */
public class XMLDayTrainPlanHandler  extends DefaultHandler {


    private List<DayTrainPlan> dayTrainPlanList = null;
    private DayTrainPlan currentDayTrainPlan;
    private String tagName = null;//当前解析的元素标签

    private static final String node = "daytrainplan";

    private static final String NODE_ID = "id" ;
    private static final String NODE_DESC = "desc" ;

    private static final String NODE_STAGE_DESC ="stageDesc" ;

    private static final String NODE_DISTANCE  = "distance" ;

    private static final String NODE_RATE = "rate";

    private static final String NODE_OFFSETWEEKS = "offsetWeeks" ;

    private static final String NODE_OFFSETDAYS  = "offsetDays" ;

    private static final String NODE_OFFSETTOTAL = "offsetTotal" ;

    private static final String NODE_REMIND_ID ="runremindId";


//    private int rateStart; // 心率的起始位置
//    private int rateEnd ; // 心率的终结位置

    private static final String NODE_RATE_START= "rateStart" ;
    private static final String NODE_RATE_END = "rateEnd" ;

    public List<DayTrainPlan> getDayTrainPlanList() {
        return dayTrainPlanList;
    }
    /*
     * 接收文档的开始的通知。
     */
    @Override public void startDocument() throws SAXException {
        dayTrainPlanList = new ArrayList<DayTrainPlan>();
    }
    /*
     * 接收字符数据的通知。
     */

    /**
     * @param ch
     * @param start
     * @param length
     * @throws SAXException
     */
    @Override public void characters(char[] ch, int start, int length) throws SAXException {
        if(tagName!=null){
            String data = new String(ch, start, length);
            if(tagName.equals(NODE_DESC)){
                this.currentDayTrainPlan.setDesc(data);
            }else if(tagName.equals(NODE_STAGE_DESC)){
                this.currentDayTrainPlan.setStageDesc(data);
            }else if(tagName.equals(NODE_DISTANCE)){
                this.currentDayTrainPlan.setDistance(Double.parseDouble(data));
            }else if(tagName.equals(NODE_RATE)){
                this.currentDayTrainPlan.setRate(data);
            }else if(tagName.equals(NODE_OFFSETWEEKS)){
                this.currentDayTrainPlan.setOffsetWeeks(Integer.parseInt(data));
            }else if(tagName.equals(NODE_OFFSETDAYS)){
                this.currentDayTrainPlan.setOffsetDays(Integer.parseInt(data));
            }else if(tagName.equals(NODE_OFFSETTOTAL)){
                this.currentDayTrainPlan.setOffsetTotal(Integer.parseInt(data));
            }else if(tagName.equals(NODE_REMIND_ID)){
                this.currentDayTrainPlan.setRunremindId(Integer.parseInt(data));
            }else if(tagName.equals(NODE_RATE_START)){
                this.currentDayTrainPlan.setRateStart(Integer.parseInt(data));
            }else if(tagName.equals(NODE_RATE_END)){
                this.currentDayTrainPlan.setRateEnd(Integer.parseInt(data));
            }
        }
    }
    /*
     * 接收元素开始的通知。
     * 参数意义如下：
     *    namespaceURI：元素的命名空间
     *    localName ：元素的本地名称（不带前缀）
     *    qName ：元素的限定名（带前缀）
     *    atts ：元素的属性集合
     */
    @Override public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        if(localName.equals(node)){
            currentDayTrainPlan = new DayTrainPlan();
            currentDayTrainPlan.setId(Integer.parseInt(atts.getValue("id")));
        }
        this.tagName = localName;
    }
    /*
     * 接收文档的结尾的通知。
     * 参数意义如下：
     *    uri ：元素的命名空间
     *    localName ：元素的本地名称（不带前缀）
     *    name ：元素的限定名（带前缀）
     */
    @Override public void endElement(String uri, String localName, String name) throws SAXException {
        if(localName.equals(node)){
            dayTrainPlanList.add(currentDayTrainPlan);
            currentDayTrainPlan = null;
        }
        this.tagName = null;
    }



}
