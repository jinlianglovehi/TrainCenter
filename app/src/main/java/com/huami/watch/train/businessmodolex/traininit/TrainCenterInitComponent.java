package com.huami.watch.train.businessmodolex.traininit;

import com.huami.watch.train.base.FragmentScoped;
import com.huami.watch.train.data.DataSourceComponent;
import com.huami.watch.train.ui.activity.TrainCenterStatusActivity;
import com.huami.watch.train.ui.test.TestActivity;

import dagger.Component;

/**
 * Created by jinliang on 16/11/9.
 */

@FragmentScoped
@Component(dependencies = DataSourceComponent.class, modules = TrainCenterInitPresenterModule.class)
public interface TrainCenterInitComponent {

    void inject(TrainCenterStatusActivity activity);

    void inject(TestActivity activity);


}
