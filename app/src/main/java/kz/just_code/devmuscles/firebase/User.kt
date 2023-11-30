package kz.just_code.devmuscles.firebase

import kz.just_code.devmuscles.repository.workout.model.Workout


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
    var pictureUrl:String? = null,
    var favoriteList:Map<String, SavedWorkout> = emptyMap()
){



}

enum class Goal{
    GET_FITTER, LOSE_WEIGHT, GAIN_MUSCLES
}

enum class Level{
    BEGINNER, INTERMEDIATE, ADVANCE
}
