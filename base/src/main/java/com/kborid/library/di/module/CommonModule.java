package com.kborid.library.di.module;

import com.kborid.library.di.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class CommonModule {

    private String name;

    public CommonModule(String name) {
        this.name = name;
    }

    @ActivityScope
    @Provides
    String providerString() {
        return name;
    }
}
