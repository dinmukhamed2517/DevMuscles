package kz.just_code.devmuscles.repository.gpt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.just_code.devmuscles.network.gpt.GptApi
import kz.just_code.devmuscles.repository.gpt.model.ChatResponse
import kz.just_code.devmuscles.repository.gpt.model.Choice
import kz.just_code.devmuscles.repository.gpt.model.Message
import kz.just_code.devmuscles.repository.gpt.model.RequestBody
import javax.inject.Inject

class GptRepositoryImpl @Inject constructor(
    private val api: GptApi
):GptRepository{


    private var choiceList:MutableList<Choice> = mutableListOf()

    private val _choicesLiveData = MutableLiveData<Choice>()
    override val choiceLiveData: LiveData<Choice>? = _choicesLiveData
    override suspend fun getPrompt(question: String): ChatResponse? {

        val requestBody = RequestBody(
            messages = listOf(
                Message(
                    role = "system",
                    content = "Imagine that you are Christopher Adam Bumstead Pro League professional bodybuilder. Bumstead is the reigning five-time Mr. Olympia Classic Physique Champion, winning five consecutive titles from 2019 to 2023." +
                            "User will ask you questions about how to train properly, how to build muscles and some similar stuff. You need to answer as well experienced fitness trainer. Remember you name is Chris Bumstead. " +
                            "Always answer briefly in 2-3 sentences. If you are not able to fit your answer in 2 -3 sentences ask user to text additional questions"

                ),
                Message(
                    role = "user",
                    content = "$question"
                )
            ),
        )
        val response = api.getPrompt(requestBody)
        return if(response.isSuccessful) response.body()
        else throw Exception(response.message())
    }

}