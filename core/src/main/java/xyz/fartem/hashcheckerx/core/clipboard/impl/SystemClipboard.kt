package xyz.fartem.hashcheckerx.core.clipboard.impl

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import xyz.fartem.hashcheckerx.core.clipboard.api.Clipboard
import javax.inject.Inject

class SystemClipboard @Inject constructor(
    context: Context,
    private val appName: String,
) : Clipboard() {

    private val clipboard: ClipboardManager? = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?

    override fun copy(text: String) {
        clipboard?.setPrimaryClip(ClipData.newPlainText(appName, text))
    }
}
