package com.kborid.setting.provider;

import android.database.MatrixCursor;
import android.os.Bundle;
import android.os.IBinder;

public class BinderCursor extends MatrixCursor {

    public static final String KEY_BINDER = "binder";

    public BinderCursor(ISession session) {
        this(new String[]{KEY_BINDER}, session);
    }

    public BinderCursor(String[] columnNames, ISession session) {
        super(columnNames, 1);

        if (session != null) {
            getExtras().putParcelable(columnNames[0], session);
        }
    }
}
