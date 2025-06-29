package com.flux.store.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import com.flux.store.helper.AppStateManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppStateModule {

    @Provides
    @Singleton
    fun provideFlagStateManager(@ApplicationContext context: Context): AppStateManager {
        return AppStateManager(context)
    }
}