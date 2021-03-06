# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\AndroidSDK/tools/proguard/proguard-android.txt
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
#环信
-keep class com.hyphenate.** {*;}
-dontwarn  com.hyphenate.**
#百度地图
-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}
-dontwarn com.baidu.**
#混淆Gilde加载https图片
-keep class com.wzq.duorou.net.OkHttpGlideModule

-dontwarn okhttp3.**
-keep class okhttp3.**{*;}
#okio
-dontwarn okio.**
-keep class okio.**{*;}

-keep class android.support.** {*;}

-keepattributes *Annotation*
-keepattributes Signature
-dontwarn com.squareup.**
-keep class com.squareup.** { *; }

-keep class butterknife.**{*;}
-dontwarn butterknife.**

-keep class com.google.**{ *; }
-dontwarn com.google.**

-keep class com.huawei.**{ *; }
-dontwarn com.huawei.**

-keep class org.apache.http.** { *; }
-dontwarn org.apache.http.**
-dontwarn android.net.**

-keep class com.parse.**{*;}
-dontwarn com.parse.**

-keep class net.sourceforge.**{ *; }
-dontwarn net.sourceforge.**