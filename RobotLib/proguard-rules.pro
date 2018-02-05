# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/jerikc/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#############################################
#                 基本指令
#代码混淆压缩比，在0~7之间，默认为5，一般不做修改
-optimizationpasses 5
#混合时不使用大小写混合，混合后的类名为小写
-dontusemixedcaseclassnames
#指定不去忽略非公共库的类
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
#不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度。
-dontpreverify
#这句话能够使我们的项目混淆后产生映射文件 包含有类名->混淆后类名的映射关系
-verbose
#忽略警告
-ignorewarnings
-dontoptimize

#指定混淆是采用的算法，后面的参数是一个过滤器 这个过滤器是谷歌推荐的算法，一般不做更改
-optimizations !code/simplification/cast,!field/*,!class/merging/*

-keepattributes *Annotation*,InnerClasses
-keepattributes Exceptions,InnerClasses
#避免混淆泛型
-keepattributes Signature

#抛出异常时保留代码行号
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

#依赖的jar包
-libraryjars /Library/Java/JavaVirtualMachines/jdk1.8.0_91.jdk/Contents/Home/jre/lib/rt.jar
-libraryjars /Users/zhangjiajie/Work/android-sdk/platforms/android-20/android.jar
-libraryjars /Users/zhangjiajie/Work/android-sdk/extras/android/support/v4/android-support-v4.jar

#############################################


#############################################
#           Android需要保留的公共部分
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Appliction
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService

#保留support下的所有类及其内部类
-keep class android.support.** {*;}
#保留R下面的资源
-keep class **.R$* {*;}
#保留本地native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}
#保留在Activity中的方法参数是view的方法
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}
#保留枚举类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
#保留我们自定义控件（继承自View）不被混淆
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
#保留SDK的接口不被混淆
-keep class com.bit.robotlib.RobotClient {*;}
#保留Parcelable序列化类不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
#保留Serializable序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
#对于带有回调函数的onXXEvent的
-keepclassmembers class * {
    void *(**On*Event);
}
#############################################

#webView处理
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.webView, jav.lang.String);
}

#############################################
#           处理反射类

#############################################

#############################################
#           处理js交互

#############################################

#############################################
#           处理实体类
#-keep public class com.appgame.**.model.** {*;}

#############################################

#############################################
#           处理第三方依赖库
#-keep class  net.tsz.afinal.** { *;}
#-dontwarn net.tsz.afinal.**
#-keep class  com.loopj.android.http.** { *;}
#-dontwarn com.loopj.android.http.**
#-keep class  com.loopj.android.image.** { *;}
#-dontwarn com.loopj.android.image.**
#-keep class  com.avos.avoscloud.feedback.** { *;}
#-dontwarn com.avos.avoscloud.feedback.**
#-keep class  com.avos.** { *;}
#-dontwarn com.avos.**
#-keep class  com.avos.avoscloud.** { *;}
#-dontwarn com.avos.avoscloud.**
#-keep class  com.avos.avoscloud.search.** { *;}
#-dontwarn com.avos.avoscloud.search.**
#-keep class  com.tencent.bugly.imsdk.** { *;}
#-dontwarn com.tencent.bugly.imsdk.**
#-keep class  com.alibaba.fastjson.** { *;}
#-dontwarn com.alibaba.fastjson.**
#-keep class  com.google.gson.** { *;}
#-dontwarn com.google.gson.**
#-keep class  org.apache.http.** { *;}
#-dontwarn org.apache.http.**
#-keep class  com.tencent.** { *;}
#-dontwarn com.tencent.**
#-keep class  org.java_websocket.** { *;}
#-dontwarn org.java_websocket.**
#-keep class  com.baidu.location.** { *;}
#-dontwarn com.baidu.location.**
#-keep class  com.mob.** { *;}
#-dontwarn com.mob.**
#-keep class  com.pili.pldroid.** { *;}
#-dontwarn com.pili.pldroid.**
#-keep class  tv.danmaku.ijk.media.player.** { *;}
#-dontwarn tv.danmaku.ijk.media.player.**
#-keep class  qalsdk.** { *;}
#-dontwarn qalsdk.**
#-keep class  cn.sharesdk.** { *;}
#-dontwarn cn.sharesdk.**
#-keep class  com.sina.** { *;}
#-dontwarn com.sina.**
#-keep class  tencent.tls.** { *;}
#-dontwarn tencent.tls.**
#-keep class  com.qq.** { *;}
#-dontwarn com.qq.**
#-keep class  com.lidroid.** { *;}
#-dontwarn com.lidroid.**

#-keep class  com.bigkoo.convenientbanner.** { *;}
#-dontwarn  com.bigkoo.convenientbanner.**
#-keep class  me.nereo.** { *;}
#-dontwarn  me.nereo.**
#-keep class  com.astuetz.** { *;}
#-dontwarn  com.astuetz.**
#-keep class  in.srain.cube.views.ptr.** { *;}
#-dontwarn  in.srain.cube.views.ptr.**

#-keep class  com.github.sahasbhop.apngview.** { *;}
#-dontwarn    com.github.sahasbhop.apngview.**

-keep class  org.apache.commons.io.** { *;}
-dontwarn    org.apache.commons.io.**

#-keep class  ar.com.hjg.pngj.** { *;}
#-dontwarn    ar.com.hjg.pngj.**

-keep class  android.support.v4.** { *;}
-dontwarn    android.support.v4.**

#-keep class  android.support.v7.** { *;}
#-dontwarn    android.support.v7.**

#-keep class  de.hdodenhof.circleimageview.** { *;}
#-dontwarn    de.hdodenhof.circleimageview.**
#
#-keep class  de.hdodenhof.circleimageview.** { *;}
#-dontwarn    de.hdodenhof.circleimageview.**
#
#-keep class  org.greenrobot.eventbus.** { *;}
#-dontwarn    org.greenrobot.eventbus.**
#
#-keep class  ru.noties.filldrawable.** { *;}
#-dontwarn    ru.noties.filldrawable.**
#
#-keep class  com.github.sahasbhop.** { *;}
#-dontwarn    com.github.sahasbhop.**
#
#-keep class  com.github.sahasbhop.** { *;}
#-dontwarn    com.github.sahasbhop.**
#
#-keep class  in.srain.cube.views.** { *;}
#-dontwarn    in.srain.cube.views.**
#
#-keep class  com.qiniu.android.dns.** { *;}
#-dontwarn    com.qiniu.android.dns.**
#
#-keep class  com.daimajia.easing.** { *;}
#-dontwarn    com.daimajia.easing.**
#
#-keep class  com.daimajia.androidanimations.library.** { *;}
#-dontwarn    com.daimajia.androidanimations.library.**
#
#-keep class  com.nineoldandroids.** { *;}
#-dontwarn    com.nineoldandroids.**
#
#-keep class  android.support.multidex.** { *;}
#-dontwarn    android.support.multidex.**
#
#-keep class  com.android.test.runner.** { *;}
#-dontwarn    com.android.test.runner.**
#
#-keep class  com.squareup.picasso.** { *;}
#-dontwarn    com.squareup.picasso.**
#
#-keep class  io.realm.** { *;}
#-dontwarn    io.realm.**
#
#-keep class  com.getkeepsafe.relinker.** { *;}
#-dontwarn    com.getkeepsafe.relinker.**
#
#-keep class  com.makeramen.roundedimageview.** { *;}
#-dontwarn    com.makeramen.roundedimageview.**
#
#-keep class  android.support.annotation.** { *;}
#-dontwarn    android.support.annotation.**
#
#-keep class  com.nostra13.universalimageloader.** { *;}
#-dontwarn    com.nostra13.universalimageloader.**

#gson
#-keep class com.google.gson.** {*;}
#-keep class com.google.**{*;}
#-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }
#-keep class com.google.gson.examples.android.model.** { *; }

# support-v4
-dontwarn android.support.v4.**
-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep class android.support.v4.** { *; }
-keep public class * extends android.app.Fragment


#support-v7
#-dontwarn android.support.v7.**
#-keep class android.support.v7.internal.** { *; }
#-keep interface android.support.v7.internal.** { *; }
#-keep class android.support.v7.** { *; }


#support design
#-dontwarn android.support.design.**
#-keep class android.support.design.** { *; }
#-keep interface android.support.design.** { *; }
#-keep public class android.support.design.R$* { *; }
#-------------------------------------------------------------------------

#融云
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
# public *;
#}
#-keep class com.google.gson.examples.android.model.** { *; }
#-keep class **$Properties
#-dontwarn org.eclipse.jdt.annotation.**
#-keep class io.agora.rtc.** {*;}
#-keep class io.rong.** {*;}
#-keep class io.agora.rtc.** {*; }
#-keep class * implements io.rong.imlib.model.MessageContent{*;}
#-dontwarn io.rong.push.** 
#-dontnote com.xiaomi.** 
#-dontnote com.huawei.android.pushagent.** 
#-dontnote com.google.android.gms.gcm.** 
#-dontnote io.rong.**
#
##EventBus
#-keepattributes *Annotation*
#-keepclassmembers class ** {
#    @org.greenrobot.eventbus.Subscribe <methods>;
#}
#-keep enum org.greenrobot.eventbus.ThreadMode { *; }
#-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
#    <init>(java.lang.Throwable);
#}
#
##----------------支付宝混淆开始-----------------
#-keep class com.alipay.android.app.IAlixPay{*;}
#-keep class com.alipay.android.app.IAlixPay$Stub{*;}
#-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
#-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
#-keep class com.alipay.sdk.app.PayTask{ public *;}
#-keep class com.alipay.sdk.app.AuthTask{ public *;}
##----------------支付宝混淆结束-----------------

#############################################
