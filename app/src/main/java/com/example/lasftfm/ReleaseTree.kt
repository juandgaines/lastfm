package com.example.lasftfm

import android.util.Log
import com.crashlytics.android.Crashlytics
import timber.log.Timber

class ReleaseTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.ERROR || priority == Log.DEBUG) {
            Crashlytics.log(priority, tag, message)
            if (t != null) {
                Crashlytics.logException(t)
            }
        } else return
    }

}
