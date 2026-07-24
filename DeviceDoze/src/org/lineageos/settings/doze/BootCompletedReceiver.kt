/*
 * Copyright (C) 2021 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.settings.doze

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BootCompletedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "Starting")
        context.startServiceAsUser(
            Intent(context, AodStatusService::class.java),
            android.os.UserHandle.CURRENT,
        )
        Utils.checkDozeService(context)
    }

    companion object {
        private const val TAG = "DeviceDoze"
    }
}
