package com.huami.watch.train.data;

/**
 * Created by jinliang on 16/11/11.
 */

public interface IResultCallBack<T> {


    void onSuccess(T t);


    void onFail(T t ,String msg) ;
}
