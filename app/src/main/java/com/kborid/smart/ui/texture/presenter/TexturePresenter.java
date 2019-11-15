package com.kborid.smart.ui.texture.presenter;

import com.kborid.library.base.RxPresenter;
import com.kborid.smart.ui.texture.presenter.contract.TextureContract;

import javax.inject.Inject;

public class TexturePresenter extends RxPresenter<TextureContract.View> implements TextureContract.Presenter {

    @Inject
    public TexturePresenter(String name) {
        System.out.println(name);
    }
}
