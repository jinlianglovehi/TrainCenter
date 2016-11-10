package com.huami.watch.train.utils;

/**
 * Created by jinliang on 16/11/10.
 */

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * 文件读写的工具类
 */
public class FileUtils {


    //读数据
    public String readFile(Context mContext ,String fileName) throws IOException{
        //读写assets目录下的文件
        InputStream is = mContext.getResources().getAssets().open(fileName);
        Reader in = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(in);
        String line = null;
        while (null != (line = bufferedReader.readLine()) ){
            System.out.println("assets file==========" + line);
        }
        bufferedReader.close();
        in.close();
        is.close();
        return null;

    }

}
