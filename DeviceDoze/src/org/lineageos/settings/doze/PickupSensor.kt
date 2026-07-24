/*
 * Copyright (C) 2021-2025 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.settings.doze

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.hardware.TriggerEvent
import android.hardware.TriggerEventListener
import android.os.PowerManager
import android.os.SystemClock
import android.util.Log
import android.view.Display
import java.util.concurrent.Executors

class PickupSensor(
    private val context: Context,
    sensorType: String,
    private val sensorValue: Float,
) : TriggerEventListener() {
    private val powerManager = context.getSystemService(PowerManager::class.java)!!
    private val wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG)

    private val sensorManager = context.getSystemService(SensorManager::class.java)!!
    private val sensor = Utils.getSensor(sensorManager, sensorType)

    private val executorService = Executors.newSingleThreadExecutor()
    @Volatile private var enabled = false
    private var entryTimestamp = 0L

    override fun onTrigger(event: TriggerEvent) {
        if (DEBUG) Log.d(TAG, "Got sensor event: ${event.values[0]}")
        val delta = SystemClock.elapsedRealtime() - entryTimestamp
        if (delta < MIN_PULSE_INTERVAL_MS) {
            rearm()
            return
        }
        entryTimestamp = SystemClock.elapsedRealtime()
        if (event.values[0] == sensorValue) {
            if (Utils.isPickUpSetToWake(context)) {
                wakeLock.acquire(WAKELOCK_TIMEOUT_MS)
                powerManager.wakeUpWithProximityCheck(
                    SystemClock.uptimeMillis(),
                    PowerManager.WAKE_REASON_GESTURE,
                    TAG,
                    Display.DEFAULT_DISPLAY,
                )
            } else {
                Utils.launchDozePulse(context)
            }
        }
        rearm()
    }

    fun enable() {
        if (sensor != null) {
            Log.d(TAG, "Enabling")
            executorService.submit {
                enabled = true
                entryTimestamp = SystemClock.elapsedRealtime()
                sensorManager.requestTriggerSensor(this, sensor)
            }
        }
    }

    fun disable() {
        if (sensor != null) {
            Log.d(TAG, "Disabling")
            executorService.submit {
                enabled = false
                sensorManager.cancelTriggerSensor(this, sensor)
            }
        }
    }

    private fun rearm() {
        if (enabled && sensor != null) {
            sensorManager.requestTriggerSensor(this, sensor)
        }
    }

    companion object {
        private const val TAG = "PickupSensor"
        private const val DEBUG = false

        private const val MIN_PULSE_INTERVAL_MS = 2500L
        private const val WAKELOCK_TIMEOUT_MS = 300L
    }
}
