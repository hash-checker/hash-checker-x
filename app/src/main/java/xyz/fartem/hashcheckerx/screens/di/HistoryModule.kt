package xyz.fartem.hashcheckerx.screens.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import xyz.fartem.hashcheckerx.history.api.HashOutputDataStorage
import xyz.fartem.hashcheckerx.history.impl.LimitedLocalHashOutputDataStorage
import xyz.fartem.hashcheckerx.history.wrapper.HistoryWrapper
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object HistoryModule {
    @Provides
    @Singleton
    fun provideHistoryWrapper(hashOutputDataStorage: HashOutputDataStorage): HistoryWrapper {
        return HistoryWrapper(hashOutputDataStorage)
    }

    @Provides
    @Singleton
    fun provideHashOutputDataStorage(): HashOutputDataStorage {
        return LimitedLocalHashOutputDataStorage()
    }
}