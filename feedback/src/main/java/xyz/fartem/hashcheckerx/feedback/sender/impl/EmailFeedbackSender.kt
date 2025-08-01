package xyz.fartem.hashcheckerx.feedback.sender.impl

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import xyz.fartem.hashcheckerx.feedback.model.Feedback
import xyz.fartem.hashcheckerx.feedback.sender.api.FeedbackSender
import androidx.core.net.toUri

class EmailFeedbackSender(
    private val context: Context,
    private val email: String,
    private val subject: String,
    private val onError: (Exception) -> Unit
) : FeedbackSender() {
    override fun sendFeedback(feedback: Feedback) {
        val to = arrayOf<String?>(email)
        val emailIntent = Intent(Intent.ACTION_SEND)

        emailIntent.setDataAndType("mailto:".toUri(), "text/plain")

        emailIntent.putExtra(Intent.EXTRA_EMAIL, to)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        emailIntent.putExtra(Intent.EXTRA_TEXT, feedback.message)

        try {
            context.startActivity(
                Intent.createChooser(
                    emailIntent,
                    "Send mail..."
                )
            )
        } catch (e: ActivityNotFoundException) {
            onError.invoke(e)
        }
    }
}
