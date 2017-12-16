#-include proguard-rules.pro
#-include proguard-rules-debug.pro
#-ignorewarnings
#-keepattributes SourceFile,LineNumberTable
-dontobfuscate # need proper stacktraces in Debug builds
# ------------------- TEST DEPENDENCIES -------------------
-dontwarn org.hamcrest.**
-dontwarn android.test.**

-dontwarn android.support.test.**
-keep class android.support.test.** { *; }

#-keep class org.hamcrest.** { *; }

-keep class org.junit.** { *; }
-dontwarn org.junit.**

-keep class junit.** { *; }
-dontwarn junit.**

-keep class sun.misc.** { *; }
-dontwarn sun.misc.**

-keep class org.mockito.** { *; }
-dontwarn org.mockito.**

#-keep class javax.naming.** { *; }
#-dontwarn javax.naming.**
#
#-keep class javax.annotation.** { *; }
#-dontwarn javax.annotation.**
#
#-keep class okhttp3.mockwebserver.** { *; }
#-dontwarn okhttp3.mockwebserver.**
#
#-keep class okhttp3.** { *; }
#-dontwarn okhttp3.**
#
-dontwarn com.squareup.javawriter.JavaWriter
