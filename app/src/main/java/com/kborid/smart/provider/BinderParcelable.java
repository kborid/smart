package com.kborid.smart.provider;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

public class BinderParcelable implements Parcelable {
    private IBinder binder;

    BinderParcelable(IBinder binder) {
        this.binder = binder;
    }

    private BinderParcelable(Parcel in) {
        this.binder = in.readStrongBinder();
    }

    public static final Creator<BinderParcelable> CREATOR = new Creator<BinderParcelable>() {
        @Override
        public BinderParcelable createFromParcel(Parcel in) {
            return new BinderParcelable(in);
        }

        @Override
        public BinderParcelable[] newArray(int size) {
            return new BinderParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStrongBinder(binder);
    }

    public IBinder getBinder() {
        return binder;
    }

    public void setBinder(IBinder mBinder) {
        this.binder = mBinder;
    }
}
