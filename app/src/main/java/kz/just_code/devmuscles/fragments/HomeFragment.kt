package kz.just_code.devmuscles.fragments

import kz.just_code.devmuscles.Type
import kz.just_code.devmuscles.WorkoutDto
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.databinding.FragmentHomeBinding

class HomeFragment:BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun onBindView() {
        super.onBindView()
        
    }

    private fun getWorkouts():List<WorkoutDto>{
        return listOf(
            WorkoutDto(1, "Workout with dumbels", Type.INTERMEDIATE),
            WorkoutDto(2, "Chess workout", Type.BEGINNER),
            WorkoutDto(3, "Shoulder workout", Type.INTERMEDIATE),
            WorkoutDto(4, "Leg workout", Type.ADVANCE),
        )
    }
}