package kz.just_code.devmuscles.fragments

import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kz.just_code.devmuscles.ItemWorkoutAdapter
import kz.just_code.devmuscles.R
import kz.just_code.devmuscles.Type
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.databinding.FragmentWorkoutsBinding
import kz.just_code.devmuscles.model.WorkoutDto

class WorkoutsFragment:BaseFragment<FragmentWorkoutsBinding>(FragmentWorkoutsBinding::inflate) {
    override fun onBindView() {
        super.onBindView()
        val adapter = ItemWorkoutAdapter()

        with(binding){
            workoutList.adapter = adapter
            workoutList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            adapter.submitList(getWorkouts())
        }
        adapter.itemClick = {workoutItem, shared->

            val extras = FragmentNavigatorExtras(*shared.toList().toTypedArray())
            findNavController().navigate(
                WorkoutsFragmentDirections
                    .actionWorkoutsFragmentToWorkoutDetailsFragment(workoutItem), extras
            )
        }
    }
    private fun getWorkouts():List<WorkoutDto>{
        return listOf(
            WorkoutDto(1, "Workout with dumbels", Type.INTERMEDIATE, 350, 60, getString(R.string.description_placeholder) ),
            WorkoutDto(2, "Chess workout", Type.BEGINNER, 400, 15, getString(R.string.description_placeholder)),
            WorkoutDto(3, "Shoulder workout", Type.INTERMEDIATE, 400, 15, getString(R.string.description_placeholder)),
            WorkoutDto(4, "Leg workout", Type.ADVANCE, 230, 40, getString(R.string.description_placeholder)),

            )
    }
}