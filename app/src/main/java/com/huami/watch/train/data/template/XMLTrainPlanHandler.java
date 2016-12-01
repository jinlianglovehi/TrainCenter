package com.huami.watch.train.data.template;

import com.huami.watch.template.model.TrainPlan;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinliang on 16/11/10.
 */

public class XMLTrainPlanHandler extends DefaultHandler{

    private List<TrainPlan> trainPlans = null;
    private TrainPlan currentTrainPlan;
    private String tagName = null;//当前解析的元素标签

    private static final String node = "trainplan";

    private static final String NODE_ID = "id" ;
    private static final String NODE_TITLE = "title" ;

    private static final String NODE_TYPE ="type" ;

    private static final String NODE_ORDERID  = "orderId" ;

    private static final String NODE_COPY_WRITE  ="copyWrite" ;


    private static final String NODE_TOTAL_LENGTH = "totalLength";

    private static final String NODE_TOTAL_DAYS = "totalDays" ;


    public List<TrainPlan> getTrainPlans() {
        return trainPlans;
    }
    /*
     * 接收文档的开始的通知。
     */
    @Override public void startDocument() throws SAXException {
        trainPlans = new ArrayList<TrainPlan>();
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
            if(tagName.equals(NODE_TITLE)){
                this.currentTrainPlan.setTitle(data);
            }else if(tagName.equals(NODE_TYPE)){
                this.currentTrainPlan.setType(Integer.parseInt(data));
            }else if(tagName.equals(NODE_ORDERID)){
                this.currentTrainPlan.setOrderId(Integer.parseInt(data));
            }else if(tagName.equals(NODE_COPY_WRITE)){
                this.currentTrainPlan.setCopyWrite(data);
            }else if(tagName.equals(NODE_TOTAL_LENGTH)){
                this.currentTrainPlan.setTotalLength(Double.parseDouble(data));
            }else if(tagName.equals(NODE_TOTAL_DAYS)){
                this.currentTrainPlan.setTotalDays(Integer.parseInt(data));
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
            currentTrainPlan = new TrainPlan();
            currentTrainPlan.setId(Integer.parseInt(atts.getValue("id")));
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
            trainPlans.add(currentTrainPlan);
            currentTrainPlan = null;
        }
        this.tagName = null;
    }
}
