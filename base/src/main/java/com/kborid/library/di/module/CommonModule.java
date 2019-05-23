package com.kborid.library.di.module;

import dagger.Module;
import dagger.Provides;

@Module
public class CommonModule {

    private String name;

    public CommonModule(String name) {
        this.name = name;
    }

    @Provides
    String providerString() {
        return name;
    }
}
