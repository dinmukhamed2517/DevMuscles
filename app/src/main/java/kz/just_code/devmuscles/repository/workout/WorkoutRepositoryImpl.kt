package kz.just_code.devmuscles.repository

import com.google.gson.Gson
import kz.just_code.devmuscles.network.WorkoutApi
import okhttp3.ResponseBody
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class WorkoutRepositoryImpl @Inject constructor(
    val api: WorkoutApi
):WorkoutRepository {
    override suspend fun getWorkoutList(): List<Workout>? {
        val response = api.getExercises()
        if(response.isSuccessful) return response.body()
        else throw Exception(response.message())
    }

    override suspend fun getWorkoutListByTarget(target: String): List<Workout>? {
        val response = api.getExercisesByTarget(target)
        if(response.isSuccessful) return response.body()
        else throw Exception(response.message())
    }


//    private fun getMockData():List<WorkoutDto>{
//        return listOf(
//            WorkoutDto(1, "Workout with dumbels", Type.INTERMEDIATE, 350, 60, "Description" ),
//            WorkoutDto(2, "Chess workout", Type.BEGINNER, 400, 15, "Description"),
//            WorkoutDto(3, "Shoulder workout", Type.INTERMEDIATE, 400, 15, "Description"),
//            WorkoutDto(4, "Leg workout", Type.ADVANCE, 230, 40, "Description"),
//            )
//    }
}

//fun ResponseBody?.getErrorMessage():String? {
//    return try {
//        Gson().fromJson(this?.charStream(), WorkoutApiError)
//    }
//}