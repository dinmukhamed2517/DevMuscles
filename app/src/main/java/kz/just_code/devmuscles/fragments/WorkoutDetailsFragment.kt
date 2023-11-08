package kz.just_code.devmuscles.fragments

import androidx.navigation.fragment.navArgs
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.databinding.FragmentWorkoutDetailsBinding

class WorkoutDetailsFragment:BaseFragment<FragmentWorkoutDetailsBinding>(FragmentWorkoutDetailsBinding::inflate) {
    private val args: WorkoutDetailsFragmentArgs by navArgs()
    override fun onBindView() {
        super.onBindView()
        with(binding){
            title.text = args.title

            type.text = "Workouts for ${args.type}"

            calories.text = "${args.calories} Cal"
            duration.text = "${args.duration} min"
            description.text = args.description
        }

    }
}