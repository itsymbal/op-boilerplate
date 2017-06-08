-dontobfuscate # need proper stacktraces in Debug builds
# ------------------- TEST DEPENDENCIES -------------------
-dontwarn org.hamcrest.**
-dontwarn android.test.**

-dontwarn android.support.test.**
-keep class android.support.test.** { *; }

-keep class org.hamcrest.** { *; }

-keep class org.junit.** { *; }
-dontwarn org.junit.**

-keep class junit.** { *; }
-dontwarn junit.**

-keep class sun.misc.** { *; }
-dontwarn sun.misc.**

-keep class org.mockito.** { *; }
-dontwarn org.mockito.**

-dontwarn com.squareup.javawriter.JavaWriter
