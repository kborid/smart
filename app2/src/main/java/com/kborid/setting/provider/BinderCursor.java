package com.kborid.setting.provider;

import android.database.MatrixCursor;
import android.os.Bundle;
import android.os.IBinder;

public class BinderCursor extends MatrixCursor {

    public static final String KEY_BINDER = "binder";

    private Bundle mExtras = new Bundle();

    public BinderCursor(String[] columnNames, IBinder binder) {
        super(columnNames);

        if (binder != null) {
            mExtras.putBinder(KEY_BINDER, binder);
        }
    }

    @Override
    public Bundle getExtras() {
        return mExtras;
    }

}
