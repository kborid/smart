package com.kborid.smart.di;

import com.kborid.library.di.ActivityScope;
import com.kborid.library.di.component.ActivityComponent;
import com.kborid.library.di.component.AppComponent;
import com.kborid.library.di.module.ActivityModule;
import com.kborid.smart.ui.activity.MainFragmentActivity;
import com.kborid.smart.ui.activity.NewsDetailActivity;
import com.kborid.smart.ui.activity.SnapHelpActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface CommActivityComponent extends ActivityComponent {
    void inject(MainFragmentActivity activity);

    void inject(NewsDetailActivity activity);

    void inject(SnapHelpActivity activity);
}
