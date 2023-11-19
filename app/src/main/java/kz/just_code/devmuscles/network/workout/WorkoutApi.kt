package kz.just_code.devmuscles.network

import kz.just_code.devmuscles.repository.Workout
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject

interface WorkoutApi {
    @GET("exercises?limit=50")
    suspend fun getExercises(
    ):Response<List<Workout>>

    @GET("exercises/target/{target}")
    suspend fun getExercisesByTarget(
        @Path("target") target:String,
    ):Response<List<Workout>>
}