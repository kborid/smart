# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
# This is a configuration file for ProGuard.
# http://proguard.sourceforge.net/index.html#manual/usage.html

#==================common=====================
#代码混淆压缩比，在0~7之间，默认为5，一般不需要修改
-optimizationpasses 5

#混淆时不使用大小写混合，混淆后的类名为小写
-dontusemixedcaseclassnames

#指定不去忽略非公共的库的类
-dontskipnonpubliclibraryclasses

#指定不去忽略非公共的库的类的成员
-dontskipnonpubliclibraryclassmembers

#不做预校验，preverify是proguard的四个步骤之一
#Android不需要preverify，去掉这一步可加快混淆速度
-dontpreverify

#有了verbose这句话，混淆后就会生成映射文件
#包含有类名->混淆后类名的映射关系
#然后使用printmapping指定映射文件的名称
-verbose
#-printmapping proguardMapping.txt

#指定混淆时采用的算法，后面的参数是一个过滤器
#这个过滤器是google推荐的算法，一般不改变
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#保护代码中的Annotation不被混淆
#这在JSON实体映射时非常重要，比如fastJson
-keepattributes *Annotation*

#避免混淆泛型
#这在JSON实体映射时非常重要，比如fastJson
-keepattributes Signature

#抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable

#保留所有的本地native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

#保留了继承自Activity，Application这些类的子类
#因为这些子类都有可能被外部调用
#比如说，第一行就保证了所有的Activity的子类不要被混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep public class com.google.vending.licensing.ILicensingService

#如果有引用android-support-v4.jar包，可以添加下面这行
#-keep public class com.tuniu.app.ui.fragment.** {*;}

#保留在Activity中的方法参数是view的方法，
#从而我们在layout里面编写onClick就不会被影响
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}

#保持枚举 enum类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#保持自定义控件（继承自View）不被混淆
-keepclassmembers public class * extends android.view.View {
   *** get*();
   void set*(***);
   public <init>(android.content.Context);
   public <init>(android.content.Context, android.util.AttributeSet);
   public <init>(android.content.Context, android.util.AttributeSet, int);
}

#保持序列化的类Parcelable不被混淆
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

#对于R资源下的所有类及方法，都不能被混淆
-keep class **.R$* {
    *;
}

#对于带有回调函数onXXEvent的，不能被混淆
-keepclassmembers class * {
    void *(**On*Event);
}

#==================common end=====================

#gson
-dontwarn com.google.**
-keep class com.google.gson.** {*;}

#protobuf
-dontwarn com.google.**
-keep class com.google.protobuf.** {*;}

#fastjson
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }
-keepattributes Signature

#内部类
-keepattributes InnerClasses
-keep class **.R$* {
    <fields>;
}

# The support library contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version.  We know about them, and they are safe.
-dontwarn android.support.**
-keep class android.support.** {*;}

-keep class org.apache.http.** { *; }
-keep class android.net.http.** { *; }
-dontwarn org.apache.http.**
-dontwarn android.net.http.**

#okhttp, okio混淆start
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.** { *;}
-dontwarn org.conscrypt.**
-keep class org.conscrypt.** { *; }
-dontwarn okio.**
#okhttp混淆end
