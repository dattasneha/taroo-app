package com.snehadatta.taroo.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.snehadatta.taroo.network.TarotApi
import com.snehadatta.taroo.network.TarotApi.Companion.BASE_URL
import com.snehadatta.taroo.data.TarotRepository
import com.snehadatta.taroo.data.TarotRepositoryImpl
import com.snehadatta.taroo.data.local.Converters
import com.snehadatta.taroo.data.local.TarotDao
import com.snehadatta.taroo.data.local.TarotDatabase
import com.snehadatta.taroo.util.GsonParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {


    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesTarotApi(retrofit: Retrofit): TarotApi {
        return retrofit.create(TarotApi::class.java)
    }

    @Provides
    fun providesTarotRepository(impl: TarotRepositoryImpl): TarotRepository = impl

    @Provides
    fun providesTarotDataBase(app: Application): TarotDatabase {
        return Room.databaseBuilder(
            app, TarotDatabase::class.java, "tarot_db"
        ).addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }

    @Provides
    fun providesTarotDao(db: TarotDatabase): TarotDao {
        return db.tarotDao()
    }

}
