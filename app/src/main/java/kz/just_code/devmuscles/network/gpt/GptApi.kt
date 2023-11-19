package kz.just_code.devmuscles.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GptApi {
    @POST("chat/completions")
    suspend fun createChatCompletion(
        @Body chatRequest: String,
        ): Response<ChatResponse>
}
