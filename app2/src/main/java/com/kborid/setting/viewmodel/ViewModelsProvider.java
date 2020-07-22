package com.kborid.setting.viewmodel;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

public class ViewModelsProvider {
    public static ViewModelProvider of(ViewModelStoreOwner owner) {
        return new ViewModelProvider(owner);
    }
}
