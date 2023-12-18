package kz.just_code.devmuscles.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kz.just_code.devmuscles.firebase.SavedWorkout
import kz.just_code.devmuscles.repository.gpt.model.Choice
import kz.just_code.devmuscles.repository.workout.model.Workout
import kz.just_code.devmuscles.utilities.AdDao

abstract class BaseViewHolder<VB : ViewBinding, T>(protected open val binding: VB) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun bindView(item: T)
}


abstract class BaseWorkoutViewHolder<VB : ViewBinding>(override val binding: VB) :
    BaseViewHolder<VB, Workout>(binding)

abstract class BaseAdViewHolder<VB : ViewBinding>(override val binding: VB) :
    BaseViewHolder<VB, AdDao>(binding)


abstract class BaseSavedWorkoutViewHolder<VB : ViewBinding>(override val binding: VB) :
    BaseViewHolder<VB, SavedWorkout>(binding)


abstract class BaseMessageViewHolder<VB : ViewBinding>(override val binding: VB) :
    BaseViewHolder<VB, Choice>(binding)