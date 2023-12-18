package kz.just_code.devmuscles.repository.workout

import kz.just_code.devmuscles.network.workout.WorkoutApi
import kz.just_code.devmuscles.repository.workout.model.Workout
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class WorkoutRepositoryImpl @Inject constructor(
    val api: WorkoutApi
) : WorkoutRepository {
    override suspend fun getWorkoutList(): List<Workout>? {
        val response = api.getExercises()
        if (response.isSuccessful) return response.body()
        else throw Exception(response.message())
    }

    override suspend fun getWorkoutListByTarget(target: String): List<Workout>? {
        val response = api.getExercisesByTarget(target)
        if (response.isSuccessful) return response.body()
        else throw Exception(response.message())
    }

}