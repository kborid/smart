package com.kborid.smart.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
    private String mFilePath = "";

    @Override
    protected int getLayoutResId() {
        return R.layout.act_webview;
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

    private void dealIntent() {
        Uri uri = getIntent().getParcelableExtra(Intent.EXTRA_STREAM);
        if (null != uri) {
            String path = getPath(this, uri);
        }
        String path = getIntent().getStringExtra("filePath");
        if (!TextUtils.isEmpty(path)) {
            mFilePath = path;
        }
    }

    private String getPath(Context context, Uri uri) {
        String path = null;
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            return null;
        }
        if (cursor.moveToFirst()) {
            try {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        return path;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        super.initDataAndEvent(savedInstanceState);
        dealIntent();
        initViews();
        initReaderView();
        displayFile(mFilePath);
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
