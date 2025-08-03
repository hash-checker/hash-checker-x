package xyz.fartem.hashcheckerx.feedback.sender.impl

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import xyz.fartem.hashcheckerx.feedback.model.Feedback
import xyz.fartem.hashcheckerx.feedback.sender.api.FeedbackSender

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

        val emailChooserIntent = Intent.createChooser(
            emailIntent,
            "Send mail..."
        )
        emailChooserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        try {
            context.startActivity(emailChooserIntent)
        } catch (e: ActivityNotFoundException) {
            onError.invoke(e)
        }
    }
}
