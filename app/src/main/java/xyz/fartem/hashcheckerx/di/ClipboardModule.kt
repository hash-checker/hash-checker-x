package xyz.fartem.hashcheckerx.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import xyz.fartem.hashcheckerx.R
import xyz.fartem.hashcheckerx.core.clipboard.api.Clipboard
import xyz.fartem.hashcheckerx.core.clipboard.impl.SystemClipboard
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ClipboardModule {
    @Provides
    @Singleton
    fun provideClipboard(@ApplicationContext context: Context): Clipboard {
        return SystemClipboard(context, context.getString(R.string.app_name))
    }
}
