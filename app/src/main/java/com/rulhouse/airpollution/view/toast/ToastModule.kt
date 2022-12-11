package com.rulhouse.airpollution.view.toast

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ToastModule {

    @Singleton
    @Provides
    fun provideToastImpl(@ApplicationContext appContext: Context): ToastImpl {
        return ToastImpl(appContext)
    }

}