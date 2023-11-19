package kz.just_code.devmuscles.repository.gpt

import kz.just_code.devmuscles.network.GptApi
import javax.inject.Inject

class GptRepository @Inject constructor(
    private val api:GptApi
){
    suspend fun getPrompt(message:RequestBody):GeneratedAnswer {
        return api.getPrompt(message)
    }
}