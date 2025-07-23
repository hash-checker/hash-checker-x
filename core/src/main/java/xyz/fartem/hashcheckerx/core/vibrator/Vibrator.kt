package xyz.fartem.hashcheckerx.core.vibrator

import android.content.Context
import android.os.Build
import android.os.VibrationEffect

class Vibrator(context: Context) {
    private val vibrator: android.os.Vibrator? =
        context.getSystemService(Context.VIBRATOR_SERVICE) as android.os.Vibrator?

    fun oneShot() {
        if (vibrator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        VIBRATION_LENGTH.toLong(),
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                vibrator.vibrate(VIBRATION_LENGTH.toLong())
            }
        }
    }

    companion object {
        private const val VIBRATION_LENGTH = 30
    }
}