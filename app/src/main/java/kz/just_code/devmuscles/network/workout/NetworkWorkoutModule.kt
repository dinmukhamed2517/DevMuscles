package kz.just_code.devmuscles.network.workout

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object NetworkWorkoutModule {

    private const val baseUrl = "https://exercisedb.p.rapidapi.com/"
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor{chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("X-RapidAPI-Key", "c92e681a4bmsh271a014ff49b4edp1e3c55jsn678844079787")
                .addHeader("X-RapidAPI-Host","exercisedb.p.rapidapi.com")
                .build()
                chain.proceed(request)
        }.build()
    fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
    @Provides
    @Singleton
    fun getApi(): WorkoutApi {
        return getRetrofit()
            .create(WorkoutApi::class.java)
    }
}