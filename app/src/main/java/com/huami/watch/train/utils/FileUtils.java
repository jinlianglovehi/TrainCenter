package com.huami.watch.train.utils;

/**
 * Created by jinliang on 16/11/10.
 */

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 文件读写的工具类
 */
public class FileUtils {






    /**
     * 根据 类别 寻找不同的文件地址
     * @param categoryType
     * @return
     */

    public static final int  LANGUAGE_ZH = 0 ;

    public static final int  LANGUAGE_EN = 1 ;

    public static final String MODEL_TRAIN_PLAN = "train_plan" ;


    /**
     *  返回的是 每天的训练的计划目录
     * @param modelName
     * @param categoryType
     * @param mContext
     * @return
     */
    public static String getFilePathByCategoryType(Context mContext ,String modelName ,int categoryType){

        StringBuilder sb = new StringBuilder();
//        sb.append("/assets/");
        sb.append(modelName+"_");
        sb.append(getLanguageType(mContext)==LANGUAGE_ZH?"zh/":"en/");
        switch (categoryType) {
            case Constant.TRAIN_PLAN_CATEGORY_XINSHOU:
                sb.append("train_plan_category_xinshou.xml");
                break;
            case Constant.TRAIN_PLAN_CATEGORY_5KM:
                sb.append("train_plan_category_5km.xml");
                break;
            case Constant.TRAIN_PLAN_CATEGORY_10KM:
                sb.append("train_plan_category_10km.xml");
                break;
            case Constant.TRAIN_PLAN_CATEGORY_BANMA:
                sb.append("train_plan_category_banma.xml");
                break;
            case Constant.TRAIN_PLAN_CATEGORY_QUANMA:
                sb.append("train_plan_category_quanma.xml");
                break;
            case Constant.TRAIN_PLAN_CATEGORY_PLAN_SUMMMARY:
                sb.append("train_plan_category.xml");
                break;
            case Constant.TRAIN_PLAN_RUNING_WRITE_COPY:
                sb.append("train_plan_runtype_writecopy.xml");
                break;
        }
        return sb.toString();
    }



    /**
     * 获取 语言的类型
     * @param mContext
     * @return
     */
    public static int getLanguageType(Context mContext ){
        String result = mContext.getResources().getConfiguration().locale.getCountry();
        int languageType =  0 ;
        if(result.equals("CN")){
            languageType =  LANGUAGE_ZH;
        }else if(result.equals("UK") | result.equals("US")){
            languageType =  LANGUAGE_EN;
        }
        return languageType;
    }



     /**
     * 复制asset文件到指定目录
     * @param oldPath  asset下的路径
     * @param newPath  SD卡下保存路径
     */
    public static void CopyAssets(Context context, String oldPath, String newPath) {
        try {
            String fileNames[] = context.getAssets().list(oldPath);// 获取assets目录下的所有文件及目录名
            if (fileNames.length > 0) {// 如果是目录
                File file = new File(newPath);
                file.mkdirs();// 如果文件夹不存在，则递归
                for (String fileName : fileNames) {
                    CopyAssets(context, oldPath + "/" + fileName, newPath + "/" + fileName);
                }
            } else {// 如果是文件
                InputStream is = context.getAssets().open(oldPath);
                FileOutputStream fos = new FileOutputStream(new File(newPath));
                byte[] buffer = new byte[1024];
                int byteCount = 0;
                while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
                    // buffer字节
                    fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
                }
                fos.flush();// 刷新缓冲区
                is.close();
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
