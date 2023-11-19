package kz.just_code.devmuscles.repository.workout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kz.just_code.devmuscles.base.BaseViewModel
import kz.just_code.devmuscles.repository.workout.model.Workout
import javax.inject.Inject


@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private var repository: WorkoutRepository
):BaseViewModel() {

    private var _workoutListLiveData = MutableLiveData<List<Workout>?>()
    var workoutListLiveData: LiveData<List<Workout>?> = _workoutListLiveData

     fun getWorkouts(){
        launch(
            request = {
                repository.getWorkoutList()
            },
            onSuccess = {
                _workoutListLiveData.postValue(it)
            }
        )
    }

     fun getWorkoutsByTarget(target:String){
        launch(
            request = {
                repository.getWorkoutListByTarget(target)
            },
            onSuccess = {
                _workoutListLiveData.postValue(it)
            }
        )
    }

}
//    private fun getData() {
//        val data = repository.getWorkoutList()
//        _workoutListLiveData.postValue(data)
//    }
