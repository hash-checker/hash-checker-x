package xyz.fartem.hashcheckerx.feedback.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import xyz.fartem.hashcheckerx.feedback.model.Feedback
import xyz.fartem.hashcheckerx.feedback.sender.api.FeedbackSender

data class FeedbackViewModelState(val feedback: Feedback)

class FeedbackViewModel(
    private val feedbackSender: FeedbackSender,
    feedback: Feedback,
) : ViewModel() {
    private val _state = MutableStateFlow(
        FeedbackViewModelState(
            feedback = feedback,
        ),
    )
    val state = _state.asStateFlow()

    fun updateFeedbackMessage(message: String) {
        _state.update {
            it.copy(
                it.feedback.copy(
                    message = message,
                )
            )
        }
    }

    fun sendFeedback() {
        feedbackSender.sendFeedback(state.value.feedback)
    }
}

class FeedbackViewModelFactory(
    private val feedbackSender: FeedbackSender,
    private val feedback: Feedback,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedbackViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FeedbackViewModel(
                feedbackSender = feedbackSender,
                feedback = feedback,
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
