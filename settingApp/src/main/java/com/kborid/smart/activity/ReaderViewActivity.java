package com.kborid.smart.activity;

import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.kborid.smart.R;
import com.kborid.smart.widget.MainTitleLayout;
import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;

import butterknife.BindView;

public class ReaderViewActivity extends SimpleActivity implements TbsReaderView.ReaderCallback {

    @BindView(R.id.content)
    FrameLayout content;

    private TbsReaderView mTbsReaderView;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_webview;
    }

    @Override
    protected boolean needStatusBarImmersive() {
        return true;
    }

    private void initViews() {
        if (null != titleView) {
            titleView.setOnTitleListener(new MainTitleLayout.OnTitleListener() {
                @Override
                public void onBack() {
                    onBackPressed();
                }
            });
            titleView.setBackgroundColor(getResources().getColor(R.color.clock_number_pick_highlight));
        }
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        super.initEventAndData(savedInstanceState);
        initViews();
        initReaderView();
        displayFile(Environment.getExternalStorageDirectory().getPath() + File.separator + "test.doc");
    }

    private void initReaderView() {
        if (null == mTbsReaderView) {
            mTbsReaderView = new TbsReaderView(this, this);
            content.removeAllViews();
            content.addView(mTbsReaderView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mTbsReaderView) {
            mTbsReaderView.onStop();
            mTbsReaderView = null;
        }
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {

    }

    private String tbsReaderTemp = Environment.getExternalStorageDirectory() + "/TbsReaderTemp";

    private void displayFile(String filePath) {
        Log.d("print", "filepath=" + filePath);
        Log.d("print", "tbsReaderTemp=" + tbsReaderTemp);
        //增加下面一句解决没有TbsReaderTemp文件夹存在导致加载文件失败
        String bsReaderTemp = tbsReaderTemp;
        File bsReaderTempFile = new File(bsReaderTemp);
        if (!bsReaderTempFile.exists()) {
            Log.d("print", "准备创建/TbsReaderTemp！！");
            boolean mkdir = bsReaderTempFile.mkdir();
            if (!mkdir) {
                Log.d("print", "创建/TbsReaderTemp失败！！！！！");
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString("filePath", filePath);
        bundle.putString("tempPath", tbsReaderTemp);
        boolean result = mTbsReaderView.preOpen(getFileType("test.doc"), false);
        Log.d("print", "查看文档---" + result);
        if (result) {
            mTbsReaderView.openFile(bundle);
        }
    }

    private String getFileType(String paramString) {
        String str = "";

        if (TextUtils.isEmpty(paramString)) {
            Log.d("print", "paramString---->null");
            return str;
        }
        Log.d("print", "paramString:" + paramString);
        int i = paramString.lastIndexOf('.');
        if (i <= -1) {
            Log.d("print", "i <= -1");
            return str;
        }

        str = paramString.substring(i + 1);
        Log.d("print", "paramString.substring(i + 1)------>" + str);
        return str;
    }
}
