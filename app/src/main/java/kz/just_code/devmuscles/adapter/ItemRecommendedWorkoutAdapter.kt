package kz.just_code.devmuscles.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import kz.just_code.devmuscles.base.BaseWorkoutViewHolder
import kz.just_code.devmuscles.databinding.ItemRecommendedWorkoutBinding
import kz.just_code.devmuscles.fragments.titlecaseFirstChar
import kz.just_code.devmuscles.repository.workout.model.Workout

class ItemRecommendedWorkoutAdapter :
    ListAdapter<Workout, BaseWorkoutViewHolder<*>>(WorkoutDiffUtils()) {


    var itemClick: ((Workout) -> Unit)? = null

    class WorkoutDiffUtils : DiffUtil.ItemCallback<Workout>() {
        override fun areItemsTheSame(oldItem: Workout, newItem: Workout): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Workout, newItem: Workout): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseWorkoutViewHolder<*> {
        return WorkoutViewHolder(
            ItemRecommendedWorkoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BaseWorkoutViewHolder<*>, position: Int) {
        holder.bindView(getItem(position))
    }


    inner class WorkoutViewHolder(binding: ItemRecommendedWorkoutBinding) :
        BaseWorkoutViewHolder<ItemRecommendedWorkoutBinding>(binding) {
        override fun bindView(item: Workout) {
            with(binding) {
                val workoutTitle = item.name?.titlecaseFirstChar()
                if (workoutTitle?.length!! < 15) {
                    title.text = workoutTitle
                } else {
                    title.text = workoutTitle.substring(0, 15)
                }
                target.text = "${item.target?.titlecaseFirstChar()} Workout"
                equipment.text = item.equipment?.titlecaseFirstChar()
                Glide.with(itemView.context)
                    .load(item.gifUrl)
                    .into(gif)
            }
            itemView.setOnClickListener {
                itemClick?.invoke(item)
            }
        }
    }
}

