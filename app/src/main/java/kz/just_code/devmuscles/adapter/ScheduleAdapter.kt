package kz.just_code.devmuscles.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kz.just_code.devmuscles.R
import kz.just_code.devmuscles.base.BaseSavedWorkoutViewHolder
import kz.just_code.devmuscles.base.BaseWorkoutViewHolder
import kz.just_code.devmuscles.databinding.ItemWorkoutBinding
import kz.just_code.devmuscles.firebase.SavedWorkout
import kz.just_code.devmuscles.fragments.titlecaseFirstChar

class ScheduleAdapter:ListAdapter<SavedWorkout, BaseSavedWorkoutViewHolder<*>>(WorkoutDiffUtils()) {


    var itemClick:((SavedWorkout)-> Unit)? = null
    class WorkoutDiffUtils:DiffUtil.ItemCallback<SavedWorkout>(){
        override fun areItemsTheSame(oldItem: SavedWorkout, newItem: SavedWorkout): Boolean {
            return oldItem.workout.id == newItem.workout.id
        }

        override fun areContentsTheSame(oldItem: SavedWorkout, newItem: SavedWorkout): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseSavedWorkoutViewHolder<*> {
        return WorkoutViewHolder(
            ItemWorkoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseSavedWorkoutViewHolder<*>, position: Int) {
        holder.bindView(getItem(position))
    }


    inner class WorkoutViewHolder(binding:ItemWorkoutBinding):
        BaseSavedWorkoutViewHolder<ItemWorkoutBinding>(binding){
        override fun bindView(item: SavedWorkout) {
            val workout = item.workout
            with(binding){
                val workoutTitle = workout.name?.titlecaseFirstChar()
                if(workoutTitle?.length!! <15){
                    title.text = workoutTitle
                }
                else{
                    title.text = workoutTitle.substring(0,15)
                }
                type.text = "${workout.target?.titlecaseFirstChar()} Workout"
                equipment.text = workout.equipment?.titlecaseFirstChar()
                type.transitionName = "type_${workout.id}"
                title.transitionName = "title_${workout.id}"
                var color:Int? = null
                var chipColor:Int? = null
                var imageRes:Int? = null
                var padding:Int = 0

                completed.isVisible = item.completed
                when (workout.target) {
                    "abs" -> {
                        imageRes = R.drawable.ob_2
                        color = R.color.card_view_1
                        chipColor = R.color.chip_color1
                        padding = 200
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
                    "glutes"->{
                        imageRes = R.drawable.girl1_nobg
                        color = R.color.card_view_3
                        chipColor = R.color.chip_color3
                    }
                    else -> {
                        imageRes= R.drawable.the_rest_nobg
                        color = R.color.card_view_1
                        chipColor = R.color.chip_color1

                    }
                }
                image.setImageResource(imageRes)
                image.setPadding(0, padding, 0, 0)
                root.setCardBackgroundColor(ContextCompat.getColor(root.context, color ))
                type.setChipBackgroundColorResource(chipColor)
            }
            itemView.setOnClickListener {
                itemClick?.invoke(item)
            }
        }
    }
}


