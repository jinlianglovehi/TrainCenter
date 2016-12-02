package com.huami.watch.train.data.template;


import com.huami.watch.train.data.greendao.template.RunRemind;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 *  跑步提醒的 handler 处理
 */
public class XMLRunRemindHandler extends DefaultHandler {


    private List<RunRemind> runRemindList = null;
    private RunRemind currentRunRemind;
    private String tagName = null;//当前解析的元素标签

    private static final String node = "runremind";

    /**
     * private int id ;

     private String runType ; // 跑步， 休息， 游泳 ，

     private String trainContent;//训练内容太

     private String copyWrite;// 对应文案

     * @return
     */


    private static final String  NODE_ID = "id" ;

    private static final String NODE_RUN_TYPE  ="runType";

    private static final String NODE_TRAIN_CONTENT = "trainContent" ;

    private static final String NODE_COPY_WRITE = "copyWrite" ;

    public List<RunRemind> getRunRemindList() {
        return runRemindList;
    }
    /*
     * 接收文档的开始的通知。
     */
    @Override public void startDocument() throws SAXException {
        runRemindList = new ArrayList<RunRemind>();
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
            if(tagName.equals(NODE_RUN_TYPE)){
                this.currentRunRemind.setRunType(data);
            }else if(tagName.equals(NODE_COPY_WRITE)){
                this.currentRunRemind.setCopyWrite(data);
            }else if(tagName.equals(NODE_TRAIN_CONTENT)){
                this.currentRunRemind.setTrainContent(data);
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
            currentRunRemind = new RunRemind();
            currentRunRemind.setId(Integer.parseInt(atts.getValue("id")));
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
            runRemindList.add(currentRunRemind);
            currentRunRemind = null;
        }
        this.tagName = null;
    }



}
