package com.kborid.smart.fragment.first;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.kborid.smart.R;
import com.kborid.smart.fragment.first.model.FirstModel;
import com.kborid.smart.fragment.first.presenter.FirstPresenter;
import com.kborid.smart.fragment.first.view.IFirstView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentFirst extends Fragment implements IFirstView {

    private static final String TAG = FragmentFirst.class.getSimpleName();

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.emptyView)
    TextView emptyView;

    private List<HashMap<String, String>> mData = new ArrayList<>();
    private BaseAdapter adapter;
    private FirstPresenter firstPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Logger.t(TAG).d("onAttach()");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Logger.t(TAG).d("onCreateView()");
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        firstPresenter = new FirstPresenter(this);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    public static Fragment newInstance() {
        return new FragmentFirst();
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            firstPresenter.request();
        }
    };

    private void initView() {
        Logger.t(TAG).d("initView()");
        adapter = new SimpleAdapter(getActivity(), mData, R.layout.lv_string_item, new String[]{FirstModel.KEY_NAME}, new int[]{R.id.tv_name});
        listView.setAdapter(adapter);
        listView.setEmptyView(emptyView);
        emptyView.setOnClickListener(listener);

        Button footerBtn = new Button(getActivity());
        footerBtn.setOnClickListener(listener);
        listView.addFooterView(footerBtn);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                firstPresenter.remove(position);
            }
        });
    }

    @Override
    public void updateData(List<HashMap<String, String>> data) {
        mData.clear();
        mData.addAll(data);
        adapter.notifyDataSetChanged();
    }
}
