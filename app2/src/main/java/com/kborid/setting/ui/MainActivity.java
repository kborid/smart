package com.kborid.setting.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;

import com.kborid.setting.R;
import com.kborid.setting.databinding.ActivityMainBinding;
import com.kborid.setting.pojo.Address;
import com.kborid.setting.vm.MainViewModel;
import com.kborid.setting.vm.constant.Constant;
import com.thunisoft.common.base.BaseSimpleActivity;
import com.thunisoft.common.tool.UIHandler;
import com.thunisoft.common.util.ToastUtils;

public class MainActivity extends BaseSimpleActivity {

    ActivityMainBinding mBinding;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setAddress(new Address("成都", "四川"));
        mBinding.setHandle(this);
        mBinding.setMainViewModel(getViewModel(MainViewModel.class));
        mBinding.setConstant(getViewModel(Constant.class));
        mBinding.setLifecycleOwner(this);
    }

    public void doClick(String msg) {
        mBinding.getAddress().setTest(new MutableLiveData<>("威海"));
        ToastUtils.showToast(msg + "我被点击了");
    }

    public void onBtnClick() {
        Intent intent1 = new Intent(this, TransActivity.class);
        startActivity(intent1);
        UIHandler.postDelayed(() -> {
            Intent intent2 = new Intent(MainActivity.this, FragmentActivity.class);
            startActivity(intent2);
        }, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding.unbind();
    }
}
