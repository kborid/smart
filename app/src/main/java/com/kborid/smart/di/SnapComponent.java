package com.kborid.smart.di;

import com.kborid.library.di.ActivityScope;
import com.kborid.library.di.module.CommonModule;
import com.kborid.smart.ui.snaphelper.SnapHelpActivity;

import dagger.Component;

@ActivityScope
@Component(modules = CommonModule.class)
public interface SnapComponent {
    void inject(SnapHelpActivity activity);
}
