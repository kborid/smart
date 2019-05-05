package com.kborid.smart.activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
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
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.kborid.library.util.LogUtils;
import com.kborid.smart.R;
import com.kborid.smart.test.TestTitleView;
import com.kborid.smart.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.smart.jsbridge.SampleRegisterHandler;
import com.smart.jsbridge.WVJBWebViewClient;

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
        mWebView.setWebViewClient(new MyWebViewClient(mWebView));
        mWebView.setWebChromeClient(new MyWebChromeClient());
//        mWebView.loadUrl("http://t66y.com");
        mWebView.loadUrl("file:///android_asset/ExampleApp.html");
    }

    @Override
    protected void dealIntent() {
        super.dealIntent();
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            String from = bundle.getString("from");
            isFirst = TextUtils.isEmpty(from);
        }
    }

    private void setUpWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setBlockNetworkImage(false);
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

    private class MyWebViewClient extends WVJBWebViewClient {
        public MyWebViewClient(WebView webView) {
            super(webView);
            new SampleRegisterHandler(this, WebViewActivity.this).init();
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
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            LogUtils.i("onReceivedSslError()");
            handler.proceed();
        }
    }

    private class MyWebChromeClient extends WebChromeClient {

        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            AlertDialog.Builder builder = new AlertDialog.Builder(WebViewActivity.this);
            builder.setMessage(message);
            builder.setPositiveButton("OK", new AlertDialog.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    result.confirm();
                }
            });
            builder.setCancelable(false);
            builder.create();
            builder.show();
            return true;
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            Logger.t("duan").d("onJsConfirm()");
            return super.onJsConfirm(view, url, message, result);
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            Logger.t("duan").d("onJsPrompt()");
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }

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
