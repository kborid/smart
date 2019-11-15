package com.kborid.smart.ui.snaphelper;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.kborid.library.base.BaseActivity;
import com.kborid.smart.R;
import com.kborid.smart.di.DaggerSnapComponent;
import com.kborid.smart.ui.snaphelper.adapter.RecycleAdapter;
import com.kborid.smart.ui.snaphelper.presenter.SnapPresenter;
import com.kborid.smart.ui.snaphelper.presenter.contract.SnapContract;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;

public class SnapHelpActivity extends BaseActivity<SnapPresenter> implements SnapContract.View {

    private static final String TAG = SnapHelpActivity.class.getSimpleName();

    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    private RecycleAdapter adapter;

    @Override
    protected void initInject() {
        DaggerSnapComponent.builder()
                .commonModule(getCommonModule("snap"))
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_snaphelper;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        Logger.t(TAG).d("initEventAndData()");
        if (null == adapter) {
            adapter = new RecycleAdapter(this);
        }
        recycleView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recycleView.setAdapter(adapter);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recycleView);

        mPresenter.request();
    }

    @Override
    public void updateData(List<String> list) {
        adapter.updateData(list);
    }
}
