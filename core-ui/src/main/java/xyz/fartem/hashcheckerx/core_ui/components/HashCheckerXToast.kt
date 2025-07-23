package xyz.fartem.hashcheckerx.core_ui.components

import android.content.Context
import android.widget.Toast

fun showHashCheckerXToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}
