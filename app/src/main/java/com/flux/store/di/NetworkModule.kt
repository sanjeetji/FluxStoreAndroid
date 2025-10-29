package com.flux.store.di

import com.flux.store.BuildConfig
import com.flux.store.network.ApiConfigProvider
import com.flux.store.network.ApiService
import com.flux.store.network.ChatApiService
import com.flux.store.network.GoogleApiService
import com.flux.store.network.OlaApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // region --- Qualifiers ---
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseApi

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class GoogleApi

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class OlaApi

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ChatApi
    // endregion


    // region --- Common Providers ---
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideHttpClient(
        logging: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(logging)
        .build()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()
    // endregion


    // region --- Retrofit Instances ---
    /** Base API (Dynamic: Dev / Staging / Prod) */
    @Provides
    @Singleton
    @BaseApi
    fun provideBaseRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        configProvider: ApiConfigProvider
    ): Retrofit = Retrofit.Builder()
        .baseUrl(configProvider.activeBaseUrl)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()

    /** Google API */
    @Provides
    @Singleton
    @GoogleApi
    fun provideGoogleRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.GOOGLE_API_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()

    /** Ola API */
    @Provides
    @Singleton
    @OlaApi
    fun provideOlaRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.OLA_API_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()

    /** Chat API */
    @Provides
    @Singleton
    @ChatApi
    fun provideChatRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.CHAT_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()
    // endregion


    // region --- Service Providers ---
    @Provides
    @Singleton
    fun provideApiService(@BaseApi retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)


    @Provides
    @Singleton
    fun provideGoogleApiService(@GoogleApi retrofit: Retrofit): GoogleApiService =
        retrofit.create(GoogleApiService::class.java)

    @Provides
    @Singleton
    fun provideOlaApiService(@OlaApi retrofit: Retrofit): OlaApiService =
        retrofit.create(OlaApiService::class.java)

    @Provides
    @Singleton
    fun provideChatApiService(@ChatApi retrofit: Retrofit): ChatApiService =
        retrofit.create(ChatApiService::class.java)
    // endregion
}