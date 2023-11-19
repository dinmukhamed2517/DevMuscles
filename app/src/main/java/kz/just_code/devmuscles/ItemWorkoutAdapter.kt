package kz.just_code.devmuscles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kz.just_code.devmuscles.base.BaseWorkoutViewHolder
import kz.just_code.devmuscles.databinding.ItemWorkoutBinding
import kz.just_code.devmuscles.fragments.titlecaseFirstChar
import kz.just_code.devmuscles.repository.workout.model.Workout

class ItemWorkoutAdapter:ListAdapter<Workout, BaseWorkoutViewHolder<*>>(WorkoutDiffUtils()) {


    var itemClick:((Workout, Map<View, String>)-> Unit)? = null
    class WorkoutDiffUtils:DiffUtil.ItemCallback<Workout>(){
        override fun areItemsTheSame(oldItem: Workout, newItem: Workout): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Workout, newItem: Workout): Boolean {
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


        override fun bindView(item: Workout) {
            with(binding){
                title.text = item.name?.titlecaseFirstChar()
                type.text = item.target

                type.transitionName = "type_${item.id}"
                title.transitionName = "title_${item.id}"
                var imageRes:Int? = null
                when (item.target) {
                    "abs" -> {
                        imageRes = R.drawable.abs
                    }
                    "delts" -> {
                        imageRes = R.drawable.delts
                    }
                    "biceps" -> {
                        imageRes = R.drawable.biceps
                    }
                    "calves" -> {
                        imageRes = R.drawable.calves
                    }
                    "quads" -> {
                        imageRes = R.drawable.legs
                    }
                    "triceps" -> {
                        imageRes = R.drawable.triceps
                    }
                    else -> {
                        imageRes=R.drawable.the_rest
                    }
                }
                image.setImageResource(imageRes)
                image.transitionName = "image_${item.id}"

            }
            itemView.setOnClickListener {
                itemClick?.invoke(item, mapOf(
                    binding.title to "title_${item.id}",
                    binding.type to "type_${item.id}",
                    binding.image to "image_${item.id}"

                ))
            }
        }
    }
}

