package kz.just_code.devmuscles.repository.gpt

import androidx.lifecycle.LiveData
import kz.just_code.devmuscles.repository.gpt.model.ChatResponse
import kz.just_code.devmuscles.repository.gpt.model.Choice

interface GptRepository {

    suspend fun getPrompt(question: String): ChatResponse?

    val choiceLiveData: LiveData<Choice>?
}