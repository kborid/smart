package com.kborid.smart.di;

import com.kborid.library.di.component.AppComponent;
import com.kborid.library.di.module.AppModule;
import com.kborid.smart.PRJApplication;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface CommonAppComponent extends AppComponent {
    void inject(PRJApplication app);
}
