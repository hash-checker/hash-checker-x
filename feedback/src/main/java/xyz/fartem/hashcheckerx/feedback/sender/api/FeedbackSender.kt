package xyz.fartem.hashcheckerx.feedback.sender.api

import xyz.fartem.hashcheckerx.feedback.model.Feedback

abstract class FeedbackSender {
    abstract fun sendFeedback(feedback: Feedback)
}
