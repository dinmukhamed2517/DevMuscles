package kz.just_code.devmuscles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kz.just_code.devmuscles.base.BaseWorkoutViewHolder
import kz.just_code.devmuscles.databinding.ItemWorkoutBinding

class ItemWorkoutAdapter:ListAdapter<WorkoutDto, BaseWorkoutViewHolder<*>>(WorkoutDiffUtils()) {


    var itemClick:((WorkoutDto)-> Unit)? = null
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
                if(item.type.ordinal == Type.BEGINNER.ordinal){
                    typeText = "Workouts for Beginner"
                }
                else if(item.type.ordinal == Type.ADVANCE.ordinal){
                    typeText = "Workouts for Advance"
                }
                else if(item.type.ordinal == Type.INTERMEDIATE.ordinal){
                    typeText = "Workouts for Intermediate"
                }
                workoutType.text = typeText
            }
            itemView.setOnClickListener {
                itemClick?.invoke(item)
            }
        }
    }
}



data class WorkoutDto(
    var id:Int,
    var title:String,
    var type:Type
)

enum class Type{
    BEGINNER, INTERMEDIATE, ADVANCE
}