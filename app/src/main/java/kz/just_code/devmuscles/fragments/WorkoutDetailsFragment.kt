package kz.just_code.devmuscles.fragments

import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.databinding.FragmentWorkoutDetailsBinding

class WorkoutDetailsFragment:BaseFragment<FragmentWorkoutDetailsBinding>(FragmentWorkoutDetailsBinding::inflate) {
    private val args: WorkoutDetailsFragmentArgs by navArgs()
    override fun onBindView() {
        super.onBindView()
        with(binding){
            val workoutItem = args.workoutItem
            title.text = workoutItem.title

            type.text = "Workouts for ${workoutItem.type}"

            calories.text = "${workoutItem.calories} Cal"
            duration.text = "${workoutItem.duration} min"
            description.text = workoutItem.description
            title.transitionName = "title_${workoutItem.id}"
            type.transitionName = "type_${workoutItem.id}"

        }
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)

    }
}