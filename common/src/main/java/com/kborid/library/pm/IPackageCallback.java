package com.kborid.library.pm;

public interface IPackageCallback {
    void installPackage(String pkgName, int code);

    void deletePackage(String pkgName, int code);
}
