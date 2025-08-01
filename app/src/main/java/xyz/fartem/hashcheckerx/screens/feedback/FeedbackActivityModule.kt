package xyz.fartem.hashcheckerx.screens.feedback

import android.content.Context
import android.os.Build
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import xyz.fartem.hashcheckerx.BuildConfig
import xyz.fartem.hashcheckerx.feedback.model.Feedback
import xyz.fartem.hashcheckerx.feedback.sender.api.FeedbackSender
import xyz.fartem.hashcheckerx.feedback.sender.impl.EmailFeedbackSender

@Module
@InstallIn(ActivityComponent::class)
object FeedbackActivityModule {
    @Provides
    fun provideFeedbackSender(@ApplicationContext context: Context): FeedbackSender {
        return EmailFeedbackSender(
            context = context,
            email = "",
            subject = "",
            onError = {},
        )
    }

    @Provides
    fun provideFeedback(): Feedback {
        return Feedback(
            appVersionName = BuildConfig.VERSION_NAME,
            appVersionCode = BuildConfig.VERSION_CODE,
            osVersion = Build.VERSION.RELEASE,
            manufacturer = Build.MANUFACTURER,
            model = Build.MODEL,
            message = "",
        )
    }
}
