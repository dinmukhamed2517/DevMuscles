package kz.just_code.devmuscles.repository.gpt

import android.util.Log
import kz.just_code.devmuscles.network.gpt.GptApi
import kz.just_code.devmuscles.repository.gpt.model.ChatResponse
import kz.just_code.devmuscles.repository.gpt.model.RequestBody
import retrofit2.Response
import javax.inject.Inject

class GptRepositoryImpl @Inject constructor(
    private val api: GptApi
):GptRepository{
    override suspend fun getPrompt(requestBody: RequestBody): ChatResponse? {
        val response = api.getPrompt(requestBody)
        return if(response.isSuccessful) response.body()
        else throw Exception(response.message())
    }

}