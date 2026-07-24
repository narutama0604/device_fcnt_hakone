/*
 * Copyright (C) 2026 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.settings.doze

import android.app.Service
import android.content.Intent
import android.hardware.display.DisplayManager
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.SystemProperties
import android.view.Display

class AodStatusService : Service() {
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var displayManager: DisplayManager

    private val displayListener = object : DisplayManager.DisplayListener {
        override fun onDisplayAdded(displayId: Int) = Unit

        override fun onDisplayRemoved(displayId: Int) = Unit

        override fun onDisplayChanged(displayId: Int) {
            if (displayId == Display.DEFAULT_DISPLAY) {
                updateAodStatus()
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        displayManager = getSystemService(DisplayManager::class.java)
        displayManager.registerDisplayListener(displayListener, handler)
        updateAodStatus()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        updateAodStatus()
        return START_STICKY
    }

    override fun onDestroy() {
        displayManager.unregisterDisplayListener(displayListener)
        SystemProperties.set(AOD_STATUS_PROPERTY, "off")
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun updateAodStatus() {
        val display = displayManager.getDisplay(Display.DEFAULT_DISPLAY) ?: return

        when (display.state) {
            Display.STATE_DOZE_SUSPEND ->
                SystemProperties.set(AOD_STATUS_PROPERTY, "on")

            Display.STATE_ON,
            Display.STATE_OFF,
            Display.STATE_DOZE ->
                SystemProperties.set(AOD_STATUS_PROPERTY, "off")
        }
    }

    companion object {
        private const val AOD_STATUS_PROPERTY = "sys.aod.status"
    }
}
