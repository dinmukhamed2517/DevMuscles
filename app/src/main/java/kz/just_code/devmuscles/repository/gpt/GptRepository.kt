package kz.just_code.devmuscles.repository.gpt

import kz.just_code.devmuscles.repository.gpt.model.ChatResponse
import kz.just_code.devmuscles.repository.gpt.model.RequestBody

interface GptRepository {

    suspend fun getPrompt(requestBody: RequestBody):ChatResponse?
}