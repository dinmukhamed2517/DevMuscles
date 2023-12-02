package kz.just_code.devmuscles.firebase


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kz.just_code.devmuscles.repository.workout.model.Workout

abstract class FRDBWrapper<T> {
    private val db = FirebaseDatabase.getInstance()

    protected abstract fun getTableName(): String
    protected abstract fun getClassType(): Class<T>

    private val _getDataLiveData = MutableLiveData<T?>()
    val getDataLiveData: LiveData<T?> = _getDataLiveData

    private val _updateLiveData = MutableLiveData<T?>()
    val updateLiveData: LiveData<T?> = _updateLiveData

    init {
        db.getReference(getTableName()).addValueEventListener(updateListener())
    }

    private fun updateListener() = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            _updateLiveData.postValue(snapshot.getValue(getClassType()))
        }

        override fun onCancelled(error: DatabaseError) {
            error.let {
                Log.e("FRDBWrapper", it.message)
            }
        }
    }
    fun saveData(value: T, successSave: ((Boolean) -> Unit)? = null) {
        db.getReference(getTableName()).setValue(value) { error, _ ->
            successSave?.invoke(error == null)
            error?.let {
                Log.e("FRDBWrapper", it.message)
            }
        }
    }
    fun saveWorkoutToList(value:SavedWorkout){
        val workoutId = db.getReference(getTableName()).push().key
        if (workoutId != null) {
            db.getReference(getTableName()).child("favoriteList").child(workoutId).setValue(value)
        }
    }

    fun saveProfilePic(value:String){
        db.getReference(getTableName()).child("pictureUrl").setValue(value)
    }

    fun changeCompleteStatus(value:Boolean, workoutId:String){
        db.getReference(getTableName()).child("favoriteList").child(workoutId).child("completed").setValue(value)
    }


    fun saveBio(value:String){
        db.getReference(getTableName()).child("bio").setValue(value)
    }

    fun saveGoalWeight(value:Int){
        db.getReference(getTableName()).child("goalWeight").setValue(value)

    }

    fun getData() {
        db.getReference(getTableName()).get().addOnSuccessListener {
            _getDataLiveData.postValue(it.getValue(getClassType()))
        }
    }

    fun removeData() {
        db.getReference(getTableName()).removeValue()
    }
}