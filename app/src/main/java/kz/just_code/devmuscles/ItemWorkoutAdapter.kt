package kz.just_code.devmuscles

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
                val workoutTitle = item.name?.titlecaseFirstChar()
                if(workoutTitle?.length!! <15){
                    title.text = workoutTitle
                }
                else{
                    title.text = workoutTitle.substring(0,15)
                }
                type.text = "${item.target?.titlecaseFirstChar()} Workout"
                equipment.text = item.equipment?.titlecaseFirstChar()
                type.transitionName = "type_${item.id}"
                title.transitionName = "title_${item.id}"
                var color:Int? = null
                var chipColor:Int? = null
                var imageRes:Int? = null
                when (item.target) {
                    "abs" -> {
                        imageRes = R.drawable.girl1_nobg
                        color = R.color.card_view_1
                        chipColor = R.color.chip_color1
                    }
                    "delts" -> {
                        imageRes = R.drawable.nobg_girl
                        color = R.color.card_view_2
                        chipColor = R.color.chip_color2


                    }
                    "biceps" -> {
                        imageRes = R.drawable.biceps_nobg
                        color = R.color.card_view_3
                        chipColor = R.color.chip_color3


                    }
                    "calves" -> {
                        imageRes = R.drawable.calves_nobg
                        color = R.color.card_view_2
                        chipColor = R.color.chip_color2


                    }
                    "quads" -> {
                        imageRes = R.drawable.legs_nobg
                        color = R.color.card_view_2
                        chipColor = R.color.chip_color2

                    }
                    "triceps" -> {
                        imageRes = R.drawable.triceps_nobg
                        color = R.color.card_view_3
                        chipColor = R.color.chip_color3

                    }
                    "lats" -> {
                        imageRes = R.drawable.lats_nobg
                        color = R.color.card_view_3
                        chipColor = R.color.chip_color3
                    }
                    else -> {
                        imageRes=R.drawable.the_rest_nobg
                        color = R.color.card_view_1
                        chipColor = R.color.chip_color1

                    }
                }
                image.setImageResource(imageRes)
                image.transitionName = "image_${item.id}"
                root.setCardBackgroundColor(ContextCompat.getColor(root.context, color ))
                type.setChipBackgroundColorResource(chipColor)


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

