package com.huami.watch.train.utils;

/**
 * Created by jinliang on 16/11/10.
 *
 * 读取文件的处理工具类
 */

public class XMLUtils {


//    /**
//     * 通过 assets 文件获取文件中的借点数据
//     * @param
//     * @return
//     */
//    public static List<DayTrainPlan> readXMLToDayTrainPlans(Context mContext,int categoryId) {
//
//       String filePath =  FileUtils.getFilePathByCategoryType(mContext,FileUtils.MODEL_TRAIN_PLAN,categoryId);
//       String result = getFileContentByFilePath(mContext,filePath);
//       DayTrainPlanRoot dayTrainPlanRoot =  JaxUtils.converyToJavaBean(result, DayTrainPlanRoot.class);
//
//        if(dayTrainPlanRoot!=null){
//            return dayTrainPlanRoot.getDayTrainPlanList();//返回的是xml 中的数据信息
//        }
//        return null;
//    }
//
//
//    /**
//     * 从 train_plan_category.xml 中获取数据信息
//     * @return
//     */
//    public static List<TrainPlan> getTrainPlanFromXml(Context mContext){
//
//        String filePath  =FileUtils.getFilePathByCategoryType(
//                mContext,FileUtils.MODEL_TRAIN_PLAN,TrainPlan.CATEGORY_PLAN_SUMMMARY);
//
//        String result  = getFileContentByFilePath(mContext,filePath);
//
//        TrainPlanRoot trainPlanRoot = JaxUtils.converyToJavaBean(result,TrainPlanRoot.class);
//
//        if(trainPlanRoot!=null){
//            return trainPlanRoot.getTrainPlanList();
//        }
//
//        return null;
//
//    }
//
//
//    /**
//     * 根据文件路径获取的是文件内容
//     * @param mContext
//     * @param filePath
//     * @return
//     */
//    public static String getFileContentByFilePath(Context mContext ,String filePath){
//
//        StringBuilder sb = new StringBuilder();
//        try {
//            InputStreamReader inputReader = new InputStreamReader(
//                    mContext.getResources().getAssets().open(filePath) );
//            BufferedReader bufReader = new BufferedReader(inputReader);
//            String line="";
//            while((line = bufReader.readLine()) != null){
//                sb.append(line);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return sb.toString();
//    }


}


