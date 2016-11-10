package com.huami.watch.train.businessmodolex.traininit;

import com.huami.watch.train.base.FragmentScoped;
import com.huami.watch.train.data.DataSourceComponent;
import com.huami.watch.train.ui.TrainCenterInitActivity;

import dagger.Component;

/**
 * Created by jinliang on 16/11/9.
 */

@FragmentScoped
@Component(dependencies = DataSourceComponent.class, modules = TrainCenterInitPresenterModule.class)
public interface TrainCenterInitComponent {

    void inject(TrainCenterInitActivity activity);

}
