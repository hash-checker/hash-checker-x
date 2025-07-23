package xyz.fartem.hashcheckerx.core.clipboard

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

class Clipboard(context: Context, private val text: String) {

    private val clipboard: ClipboardManager? = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?

    fun copy() {
        clipboard?.setPrimaryClip(ClipData.newPlainText("Hash Checker", text))
    }

    private fun clear() {
        val clipData = ClipData.newPlainText("Hash Checker", "")

        clipboard?.setPrimaryClip(clipData)
    }
}