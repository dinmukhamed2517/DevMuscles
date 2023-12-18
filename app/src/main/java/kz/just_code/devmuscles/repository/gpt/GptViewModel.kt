package kz.just_code.devmuscles.repository.gpt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kz.just_code.devmuscles.base.BaseViewModel
import kz.just_code.devmuscles.repository.gpt.model.ChatResponse
import javax.inject.Inject


@HiltViewModel
class GptViewModel @Inject constructor(
    private val repository: GptRepositoryImpl
) : BaseViewModel() {
    private var _chatResponseLiveData = MutableLiveData<ChatResponse?>()
    var chatResponseLiveData: LiveData<ChatResponse?> = _chatResponseLiveData
    fun getPrompt(question: String) {
        launch(
            request = {
                repository.getPrompt(question)
            },
            onSuccess = {
                _chatResponseLiveData.postValue(it)
            }
        )
    }
}



