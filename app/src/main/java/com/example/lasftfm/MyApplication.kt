package com.example.lasftfm

import android.app.Application
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }else{
            Fabric.with(this,Crashlytics())
            Timber.plant(ReleaseTree())
        }
    }
}