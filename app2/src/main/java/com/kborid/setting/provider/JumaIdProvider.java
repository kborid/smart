package com.kborid.setting.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.orhanobut.logger.Logger;

public class JumaIdProvider extends ContentProvider {

    private static final String TAG = JumaIdProvider.class.getSimpleName();
    private static final String AUTHORITY = "com.juma.jumaid.provider";

    private static final String PATH_SESSION_GETTER_BINDER = "PATH_SESSION_GETTER_BINDER";
    private static final int CODE_SESSION_GETTER_BINDER = 1;

    private static final String METHOD_GET_LOGIN_INFO = "METHOD_GET_LOGIN_INFO";
    private static final String METHOD_GET_SESSION = "METHOD_GET_SESSION";

    private static final String KEY_SESSION = "KEY_SESSION";
    private static final String KEY_LOGIN_INFO = "KEY_LOGIN_INFO";

    private static UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        mUriMatcher.addURI(AUTHORITY, PATH_SESSION_GETTER_BINDER, CODE_SESSION_GETTER_BINDER);
    }

    private IBinder mSessionGetterBinder = null;

    @Override
    public boolean onCreate() {
        mSessionGetterBinder = new SessionGetterImpl(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = null;
        int flag = mUriMatcher.match(uri);
        if (flag == CODE_SESSION_GETTER_BINDER) {
            Logger.t(TAG).d("query() binder SessionGetter");
            cursor = new BinderCursor(new String[]{BinderCursor.KEY_BINDER}, mSessionGetterBinder);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Nullable
    @Override
    public Bundle call(@NonNull String method, @Nullable String arg, @Nullable Bundle extras) {
        Logger.t(TAG).d("call() [method:" + method + "]");

        if (METHOD_GET_SESSION.equals(method)) {
            Bundle bundle = new Bundle();
            bundle.putString(KEY_SESSION, "");
            return bundle;
        }

        if (METHOD_GET_LOGIN_INFO.equals(method)) {
            Bundle bundle = new Bundle();
            bundle.putString(KEY_LOGIN_INFO, "");
            return bundle;
        }

        return null;
    }
}
