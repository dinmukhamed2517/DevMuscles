package kz.just_code.devmuscles.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkGptModule {
    private const val baseUrl = "https://api.openai.com/v1/"
    private val token = "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"
    private var client: OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor { chain ->
        val newRequest: Request =
            chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer $token").build()
        chain.proceed(newRequest)
    }.build()


    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun getApi(): GptApi {
        return getRetrofit()
            .create(GptApi::class.java)
    }
}