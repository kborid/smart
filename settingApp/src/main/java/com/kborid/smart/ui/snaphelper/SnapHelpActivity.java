package com.kborid.smart.ui.snaphelper;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;

import com.kborid.library.base.BaseSimpleActivity;
import com.kborid.smart.R;
import com.kborid.smart.ui.snaphelper.adapter.RecycleAdapter;
import com.kborid.smart.ui.snaphelper.presenter.FirstPresenter;
import com.kborid.smart.ui.snaphelper.view.IFirstView;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;

public class SnapHelpActivity extends BaseSimpleActivity implements IFirstView {

    private static final String TAG = SnapHelpActivity.class.getSimpleName();

    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    private RecycleAdapter adapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_snaphelper;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        Logger.t(TAG).d("initEventAndData()");
        FirstPresenter firstPresenter = new FirstPresenter(this);
        if (null == adapter) {
            adapter = new RecycleAdapter(this);
        }
        recycleView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recycleView.setAdapter(adapter);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recycleView);

        firstPresenter.request();
    }

    @Override
    public void updateData(List<AppInfo> list) {
        adapter.updateData(list);
    }
}
