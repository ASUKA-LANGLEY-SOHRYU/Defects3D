package com.michael.a3dprintingdefects.data.di

import android.content.Context
import com.michael.a3dprintingdefects.data.datasource.api.retrofit.DefectsApiService
import com.michael.a3dprintingdefects.data.repository.DefectsRepositoryImpl
import com.michael.a3dprintingdefects.domain.repository.IDefectsRepository
import com.michael.a3dprintingdefects.data.mapper.DefectMapper
import com.michael.a3dprintingdefects.data.okHTTP.CachingController
import com.michael.a3dprintingdefects.data.okHTTP.UnsafeOkHttpClient
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

const val BASE_URL = "https://46.19.66.5:8080" //API SERVERS

val dataModule = module {
    factory { provideDefectApiService(retrofit = get()) }
    single { provideRetrofitInstance(client = get()) }
    single { provideClient(cachingControl = get(), context = get()) }
    single { CachingController(context = get()) }
    factory { DefectMapper() }
    factory<IDefectsRepository> { DefectsRepositoryImpl(apiService = get(), mapper = get()) }
}

private fun provideClient(cachingControl: CachingController, context: Context): OkHttpClient{
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY

    val cacheSize = (10 * 1024 * 1024).toLong()
    val cache = Cache(context.cacheDir, cacheSize)

    return UnsafeOkHttpClient.getUnsafeOkHttpClientBuilder()
        .addInterceptor(interceptor)
        .addInterceptor(cachingControl.offlineInterceptor)
        .addNetworkInterceptor(cachingControl.onlineInterceptor)
        .cache(cache)
        .build()
}

private fun provideRetrofitInstance(client: OkHttpClient): Retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .client(client)
    .build()

private fun provideDefectApiService(retrofit: Retrofit): DefectsApiService =
    retrofit.create(DefectsApiService::class.java)