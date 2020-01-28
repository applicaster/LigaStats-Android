package com.applicaster.liga.statsscreenplugin.screens.base

import android.os.CountDownTimer
import android.support.v4.app.Fragment
import android.util.Log
import java.util.concurrent.TimeUnit

abstract class HeartbeatFragment : Fragment() {
    var timer: CountDownTimer? = null
    val HEARTBEAT_TIME: Long = 60000 // 1 minute
    val INTERVAL: Long = 30000 // 30 seconds

    override fun onStart() {
        super.onStart()
        startTimer()
    }

    private fun startTimer() {
        timer = object : CountDownTimer(HEARTBEAT_TIME, INTERVAL) {
            override fun onFinish() {
                heartbeat()
                timer?.start()
            }

            override fun onTick(millisUntilFinished: Long) {
                // do nothing
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }

    abstract fun heartbeat()
}
