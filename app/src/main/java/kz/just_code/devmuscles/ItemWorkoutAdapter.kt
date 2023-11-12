package kz.just_code.devmuscles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kz.just_code.devmuscles.base.BaseWorkoutViewHolder
import kz.just_code.devmuscles.databinding.ItemWorkoutBinding
import kz.just_code.devmuscles.model.WorkoutDto

class ItemWorkoutAdapter:ListAdapter<WorkoutDto, BaseWorkoutViewHolder<*>>(WorkoutDiffUtils()) {


    var itemClick:((WorkoutDto, Map<View, String>)-> Unit)? = null
    class WorkoutDiffUtils:DiffUtil.ItemCallback<WorkoutDto>(){
        override fun areItemsTheSame(oldItem: WorkoutDto, newItem: WorkoutDto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WorkoutDto, newItem: WorkoutDto): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseWorkoutViewHolder<*> {
        return WorkoutViewHolder(
            ItemWorkoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseWorkoutViewHolder<*>, position: Int) {
        holder.bindView(getItem(position))
    }


    inner class WorkoutViewHolder(binding:ItemWorkoutBinding):BaseWorkoutViewHolder<ItemWorkoutBinding>(binding){
        override fun bindView(item: WorkoutDto) {
            with(binding){
                title.text = item.title
                var typeText = ""
                when (item.type.ordinal) {
                    Type.BEGINNER.ordinal -> {
                        typeText = "Workouts for Beginner"
                    }
                    Type.ADVANCE.ordinal -> {
                        typeText = "Workouts for Advance"
                    }
                    Type.INTERMEDIATE.ordinal -> {
                        typeText = "Workouts for Intermediate"
                    }
                }
                type.text = typeText
                type.transitionName = "type_${item.id}"
                title.transitionName = "title_${item.id}"
            }
            itemView.setOnClickListener {
                itemClick?.invoke(item, mapOf(
                    binding.title to "title_${item.id}",
                    binding.type to "type_${item.id}",

                ))
            }
        }
    }
}


enum class Type{
    BEGINNER, INTERMEDIATE, ADVANCE
}