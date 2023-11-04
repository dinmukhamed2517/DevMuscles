package kz.just_code.devmuscles.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kz.just_code.devmuscles.WorkoutDto

abstract class BaseViewHolder<VB:ViewBinding, T>(protected open val binding:VB):
RecyclerView.ViewHolder(binding.root){
    abstract fun bindView(item:T)
}



abstract class BaseWorkoutViewHolder<VB:ViewBinding>(override val binding: VB):
    BaseViewHolder<VB, WorkoutDto>(binding)