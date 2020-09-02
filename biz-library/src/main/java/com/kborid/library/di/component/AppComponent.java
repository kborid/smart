package com.kborid.library.di.component;

import com.kborid.library.base.BaseApplication;
import com.kborid.library.di.ContextLife;
import com.kborid.library.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    @ContextLife("Application")
    BaseApplication getContext();  // 提供App的Context
}
