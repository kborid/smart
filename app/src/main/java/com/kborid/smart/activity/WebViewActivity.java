package com.kborid.smart.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.kborid.library.util.LogUtils;
import com.kborid.smart.R;
import com.kborid.smart.util.ToastUtils;
import com.kborid.smart.widget.MainTitleLayout;
import com.orhanobut.logger.Logger;

import butterknife.BindView;

public class WebViewActivity extends SimpleActivity {

    private static final long DURATION_PRESS_TWO = 1000;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.root)
    FrameLayout root;

    private boolean isFirst = false;
    private long mFirstPressStamp = 0;
    private String path = "http://www.baidu.com";

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
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            String from = bundle.getString("from");
            isFirst = TextUtils.isEmpty(from);
            path = bundle.getString("path");
        }

        initViews();
        mWebView.setWebViewClient(new MyWebViewClient(mWebView));
        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.loadUrl(path);
    }

    private class MyWebViewClient extends WebViewClient {
        MyWebViewClient(WebView webView) {
//            super(webView);
//            new SampleRegisterHandler(this, webView.getContext()).init();
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            LogUtils.i("onReceivedError()");
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            LogUtils.i("shouldOverrideUrlLoading() url = " + url);
            Uri uri = Uri.parse(url);
            if ("tlhl".equals(uri.getScheme())) {
                LogUtils.d("scheme=" + uri.getScheme());
                LogUtils.d("host=" + uri.getHost());
                LogUtils.d("path=" + uri.getPath());
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            LogUtils.i("onReceivedSslError()");
            //过滤https证书继续加载
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

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
            LogUtils.i("onReceivedIcon()");
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            LogUtils.i("onReceivedTitle() title = " + title);
            titleView.setTitle(title);
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
