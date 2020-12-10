package com.kborid.library.di.module;

import com.kborid.library.base.BaseApplication;
import com.kborid.library.di.ContextLife;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final BaseApplication application;

    public AppModule(BaseApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    @ContextLife
    BaseApplication provideApplicationContext() {
        return application;
    }
}