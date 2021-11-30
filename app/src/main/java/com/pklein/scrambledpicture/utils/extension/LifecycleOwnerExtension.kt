package com.pklein.scrambledpicture.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val DISMISS_DELAY_MILLIS: Long = 2000

fun LifecycleOwner.uiDelayed(
    delayInMillis: Long = DISMISS_DELAY_MILLIS,
    function: () -> Any?
): Job {
    return lifecycleScope.launch {
        whenResumed {
            delay(delayInMillis)
            function()
        }
    }
}