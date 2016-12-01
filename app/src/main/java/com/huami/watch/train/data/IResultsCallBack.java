package com.huami.watch.train.data;

import java.util.Collection;

/**
 * Created by jinliang on 16/11/11.
 */

public interface IResultsCallBack<T> {

    void onSuccess(T t);

    void onFail(T t ,String msg);

}
