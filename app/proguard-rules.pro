# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in <Android SDK>/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

#prevent ProGuard from choking on Lambdas
-dontwarn java.lang.invoke.*

# OkHttp rules
-dontwarn okio.**
-dontwarn com.squareup.okhttp.**

# Dagger Javax annotations
-dontwarn javax.xml.stream.**

# Retrofit unused on Android classes
-dontwarn retrofit2.Platform$Java8

# Rx references
-dontwarn sun.misc.Unsafe

-keepattributes *Annotation*

# Fabric configuration
# https://docs.fabric.io/android/crashlytics/dex-and-proguard.html
-keepattributes SourceFile,LineNumberTable
#If you are using custom exceptions, add this line so that custom exception types are skipped during obfuscation:
-keep public class * extends java.lang.Exception

#Exclude Saripaar classes from obfuscation and minification.
-keep class com.mobsandgeeks.saripaar.** {*;}
-keep @com.mobsandgeeks.saripaar.annotation.ValidateUsing class * {*;}