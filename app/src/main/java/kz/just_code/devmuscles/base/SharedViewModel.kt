package kz.just_code.devmuscles.base

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kz.just_code.devmuscles.firebase.Goal
import kz.just_code.devmuscles.firebase.Level
import javax.inject.Inject


@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {
    lateinit var name: String
    lateinit var lastname: String
    var age: Int? = null
    var weight: Int? = null
    var height: Int? = null
    var goalWeight: Int? = null
    var goal: Goal = Goal.GAIN_MUSCLES
    var level: Level = Level.INTERMEDIATE
    lateinit var bio: String

}