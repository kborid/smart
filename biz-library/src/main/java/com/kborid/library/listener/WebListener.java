package com.kborid.library.listener;

import android.content.Intent;
import android.net.Uri;
import android.webkit.ValueCallback;

public interface WebListener {
    void onPageStarted(String url);

    void onPageFinishedBefore();

    void onPageFinishedAfter();

    boolean shouldOverrideUrlLoading(String url);

    boolean onReceivedError(int errCode);

    void onProgressChanged(int progress);

    void onReceivedTitle(String url);

    boolean onShowFileChooser(Intent intent, ValueCallback<Uri[]> callback);

    void onDownloadStart(String url);

    void onReceiveValue(Uri[] uris);
}