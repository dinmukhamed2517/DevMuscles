package kz.just_code.devmuscles.fragments

import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kz.just_code.devmuscles.ItemWorkoutAdapter
import kz.just_code.devmuscles.R
import kz.just_code.devmuscles.Type
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.databinding.FragmentHomeBinding
import kz.just_code.devmuscles.model.WorkoutDto

class HomeFragment:BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun onBindView() {
        super.onBindView()
        val adapter = ItemWorkoutAdapter()
        with(binding){
            workoutList.adapter = adapter
            workoutList.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter.submitList(getWorkouts())
        }
        binding.seeAllBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_workoutsFragment
            )
        }
        adapter.itemClick = {workoutItem, shared->
            val extras = FragmentNavigatorExtras(*shared.toList().toTypedArray())
            val action = HomeFragmentDirections.actionHomeToWorkoutDetailsFragment(workoutItem)
            findNavController().navigate(action, extras)
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