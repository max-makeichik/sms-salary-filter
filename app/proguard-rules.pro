# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\Android\sdk/tools/proguard/proguard-android.txt
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

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-verbose

# Bottom navigation reflection
-keepclassmembers class android.support.design.internal.BottomNavigationMenuView {
    boolean mShiftingMode;
}

-dontwarn android.net.http.SslError

-dontwarn java.nio.file.Files
-dontwarn java.nio.file.Path
-dontwarn java.nio.file.OpenOption
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn com.google.android.gms.**

#jar warnings
-keepattributes EnclosingMethod
#-renamesourcefileattribute SourceFile
-keepattributes SourceFile, LineNumberTable

# OkHttp rules
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

-keepclasseswithmembernames class * {
    native <methods>;
}

-keep class com.google.android.gms.** { *; }
-dontwarn java.lang.invoke**
-dontwarn java.lang.reflect.Method

# Gson
-keep public class com.google.gson
-keep class sun.misc.Unsafe { *; }
-dontwarn sun.misc.Unsafe
-keepclassmembers enum ru.burgerking.** { *; }
# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }
-keep class ru.burgerking.model.** { *; }
# For using GSON @Expose annotation
-keepattributes *Annotation*
# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Crashlytics
-keep class com.crashlytics.** { *; }
-keep class com.crashlytics.android.**

# Retrofit2
-dontwarn okio.**
-dontwarn javax.annotation.**

-keepattributes Signature
-keepattributes Exceptions

# Remove logs
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
}

# Otto bus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @com.squareup.otto.Subscribe public *;
    @com.squareup.otto.Produce public *;
}

#SearchView
-keep class android.support.v7.widget.SearchView { *; }

# Joda time
-dontwarn org.joda.convert.**
-dontwarn org.joda.time.**
-keep class org.joda.time.** { *; }
-keep interface org.joda.time.** { *; }

# keep drawables names
-keepclassmembers class **.R$* {
    public static <fields>;
}
-keep class **.R$*

# yandex metric
-keep class com.yandex.metrica.** { *; }
-dontwarn com.yandex.metrica.**

# Google map
-keep class * extends java.util.ListResourceBundle {
   protected Object[][] getContents();
}

#WebView
-keepattributes JavascriptInterface

# Picasso
-dontwarn com.squareup.okhttp.**
# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-dontwarn com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
-dontwarn com.bumptech.glide.load.resource.bitmap.Downsampler
-dontwarn com.bumptech.glide.load.resource.bitmap.HardwareConfigState
-dontwarn com.bumptech.glide.load.resource.bitmap.VideoDecoder

# Appsee heatmap
-keep class com.appsee.** { *; }
-dontwarn com.appsee.**
-keep class android.support.** { *; }
-keep interface android.support.** { *; }
-keepattributes SourceFile,LineNumberTable