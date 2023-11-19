package kz.just_code.devmuscles.fragments

import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import kz.just_code.devmuscles.R
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.databinding.FragmentWorkoutDetailsBinding

class WorkoutDetailsFragment:BaseFragment<FragmentWorkoutDetailsBinding>(FragmentWorkoutDetailsBinding::inflate) {
    private val args: WorkoutDetailsFragmentArgs by navArgs()
    override fun onBindView() {
        super.onBindView()
        with(binding){
            val workoutItem = args.workout
            title.text = workoutItem.name?.titlecaseFirstChar()

            type.text = "Workouts for ${workoutItem.target}"

            var imageRes:Int? = null
            when (workoutItem.target) {
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
                    imageRes= R.drawable.the_rest
                }
            }
            imageView.setImageResource(imageRes)
            description.text = workoutItem.instructions?.joinToString(separator = " ")
            title.transitionName = "title_${workoutItem.id}"
            type.transitionName = "type_${workoutItem.id}"
            imageView.transitionName = "image_${workoutItem.id}"
            Glide.with(requireContext())
                .load(workoutItem.gifUrl)
                .into(image)
        }
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)

    }
}

fun String.titlecaseFirstChar() = replaceFirstChar(Char::titlecase)