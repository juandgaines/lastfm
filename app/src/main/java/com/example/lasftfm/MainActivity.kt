package com.example.lasftfm

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lasftfm.network.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import timber.log.Timber.DebugTree


class MainActivity : AppCompatActivity() {

    private var job=Job()
    private val couroutineScope= CoroutineScope(job+ Dispatchers.IO)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        couroutineScope.launch {
            try {
                var getTracksDeferred=Network.lastFm.getTracksList("spain",1)
                var listResult=getTracksDeferred
            }
            catch (e:Throwable){
                Log.e("MainActivity","${e.message}")
            }
        }
    }
}
