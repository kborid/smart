package com.kborid.smart.di;

import com.kborid.library.di.FragmentScope;
import com.kborid.library.di.component.AppComponent;
import com.kborid.library.di.component.FragmentComponent;
import com.kborid.library.di.module.FragmentModule;
import com.kborid.smart.ui.fragment.NewsFragment;
import com.kborid.smart.ui.fragment.NewsTabFragment;
import com.kborid.smart.ui.fragment.PhotoTabFragment;
import com.kborid.smart.ui.fragment.VideoFragment;
import com.kborid.smart.ui.fragment.VideoTabFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface CommFragmentComponent extends FragmentComponent {
    void inject(NewsTabFragment fragment);

    void inject(NewsFragment fragment);

    void inject(PhotoTabFragment fragment);

    void inject(VideoTabFragment fragment);

    void inject(VideoFragment fragment);
}
