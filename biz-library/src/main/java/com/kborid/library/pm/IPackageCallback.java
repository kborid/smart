package com.kborid.library.pm;

public interface IPackageCallback {
    void success(String pkgName);

    void fail(String pkgName, int code);
}
