package kz.just_code.devmuscles.fragments

import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import kz.just_code.devmuscles.ItemWorkoutAdapter
import kz.just_code.devmuscles.R
import kz.just_code.devmuscles.Type
import kz.just_code.devmuscles.WorkoutDto
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.databinding.FragmentHomeBinding

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
        adapter.itemClick = {
            findNavController().navigate(
                HomeFragmentDirections
                    .actionHomeToWorkoutDetailsFragment(it.id, it.title, it.type, it.calories, it.duration, it.description)
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