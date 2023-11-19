package kz.just_code.devmuscles.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object NetworkWorkoutModule {

    private const val baseUrl = "https://exercisedb.p.rapidapi.com/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor{chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("X-RapidAPI-Key", "d371bee275msh661c019daf4c844p1e5065jsn28f406267cfc")
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