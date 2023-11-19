package kz.just_code.devmuscles.network.gpt

import kz.just_code.devmuscles.repository.gpt.model.ChatResponse
import kz.just_code.devmuscles.repository.gpt.model.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GptApi {
    @POST("chat/completions")
    suspend fun getPrompt(
        @Body requestBody: RequestBody,
        ): Response<ChatResponse>
}
