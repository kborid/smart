package com.kborid.smart.activity;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.kborid.library.util.LogUtils;
import com.kborid.smart.R;
import com.kborid.smart.test.TestTitleView;
import com.kborid.smart.util.ToastUtils;

import butterknife.BindView;

public class WebViewActivity extends BaseActivity {

    private static final long DURATION_PRESS_TWO = 1000;
    private static final int REQUEST_CHOOSE_FILE = 1000;
    private boolean isFirst = false;
    private long mFirstPressStamp = 0;
    @BindView(R.id.progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.web_view) WebView mWebView;
    private ValueCallback<Uri[]> uploadMessage;
    private TestTitleView testTitleView;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_webview;
    }

    private void initViews() {
        testTitleView = (TestTitleView) LayoutInflater.from(this).inflate(R.layout.layout_test_title, null);
        ((LinearLayout)findViewById(R.id.title)).addView(testTitleView);
        testTitleView.setOnTitleListener(new TestTitleView.OnTitleListener() {
            @Override
            public void onBack() {
                onBackPressed();
            }
        });
    }

    @Override
    public void initParams() {
        super.initParams();
        initViews();
        setUpWebView();
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.loadUrl("http://t66y.com");
    }

    @Override
    protected void dealIntent() {
        super.dealIntent();
        Bundle bundle = getIntent().getExtras();
        String from = bundle.getString("from");
        isFirst = TextUtils.isEmpty(from);
    }

    private void setUpWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDomStorageEnabled(true);
        settings.setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        settings.setAppCachePath(appCachePath);
        settings.setAppCacheEnabled(true);
        settings.setBlockNetworkImage(false);
        settings.supportZoom();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setLoadsImagesAutomatically(true);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (REQUEST_CHOOSE_FILE == requestCode) {
            Uri uri = data.getData();
            Uri[] uris = new Uri[]{uri};
            uploadMessage.onReceiveValue(uris);
            uploadMessage = null;
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            LogUtils.i("onPageStarted()");
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            LogUtils.i("onPageFinished()");
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            LogUtils.i("onReceivedError()");
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            LogUtils.i("shouldOverrideUrlLoading() url = " + url);
            if (url.startsWith("http") || url.startsWith("https")) {
                view.loadUrl(url);
            }
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            LogUtils.i("onReceivedSslError()");
            handler.proceed();
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            try {
                if (uploadMessage != null) {
                    uploadMessage.onReceiveValue(null);
                    uploadMessage = null;
                }

                uploadMessage = filePathCallback;
                Intent resultIntent = Intent.createChooser(fileChooserParams.createIntent(), "chooser");
                startActivityForResult(resultIntent, REQUEST_CHOOSE_FILE);
            } catch (ActivityNotFoundException e) {
                uploadMessage = null;
                return false;
            } catch (Exception e) {
                LogUtils.e("start activity for result exception " + e);
            }
            return true;
        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
            LogUtils.i("onReceivedIcon()");
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            LogUtils.i("onReceivedTitle() title = " + title);
            testTitleView.setTitle(title);
        }

        @Override
        public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
            super.onReceivedTouchIconUrl(view, url, precomposed);
            LogUtils.i("onReceivedTouchIconUrl() url = " + url);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                mProgressBar.setVisibility(View.GONE);
            } else {
                mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.setProgress(newProgress);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.stopLoading();
            mWebView.goBack();
        } else {
            if (isFirst) {
                long curr = System.currentTimeMillis();
                if (curr - mFirstPressStamp <= DURATION_PRESS_TWO) {
                    finish();
                } else {
                    mFirstPressStamp = curr;
                    ToastUtils.showToast("再按一次退出程序");
                }
            } else {
                super.onBackPressed();
            }
        }
    }
}
