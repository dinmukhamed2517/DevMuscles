package kz.just_code.devmuscles.firebase


data class User(
    var name:String? = null,
    var lastname:String? = null,
    var age:Int? = null,
    var bio:String? = null,
    var height: Int? = null,
    var weight: Int? = null,
    var goalWeight: Int? = null,
    var goal:Goal? = null,
    var level:Level? = null,
    var favoriteList:MutableList<SavedWorkout> = mutableListOf()
){



}

enum class Gender{
    MALE, FEMALE, NOT_SELECTED
}

enum class Goal{
    GET_FITTER, LOSE_WEIGHT, GAIN_MUSCLES
}

enum class Level{
    BEGINNER, INTERMEDIATE, ADVANCE
}
