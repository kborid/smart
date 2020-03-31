package com.kborid.smart.di;

import com.kborid.library.di.ActivityScope;
import com.kborid.library.di.module.CommonModule;
import com.kborid.smart.ui.activity.MainFragmentActivity;
import com.kborid.smart.ui.activity.NewsDetailActivity;
import com.kborid.smart.ui.fragment.NewsFragment;
import com.kborid.smart.ui.fragment.NewsTabFragment;
import com.kborid.smart.ui.fragment.PhotoTabFragment;
import com.kborid.smart.ui.fragment.VideoTabFragment;
import com.kborid.smart.ui.fragment.VideoFragment;

import dagger.Component;

@ActivityScope
@Component(modules = CommonModule.class)
public interface CommonComponent {
    void inject(MainFragmentActivity activity);

    void inject(NewsTabFragment fragment);

    void inject(NewsFragment fragment);

    void inject(NewsDetailActivity activity);

    void inject(PhotoTabFragment fragment);

    void inject(VideoTabFragment fragment);

    void inject(VideoFragment fragment);
}
