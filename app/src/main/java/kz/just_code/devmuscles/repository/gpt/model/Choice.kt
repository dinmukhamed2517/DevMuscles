package kz.just_code.devmuscles.repository.gpt.model

data class Choice(
    val index: Int,
    val message: Message,
)