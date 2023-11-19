package kz.just_code.devmuscles.fragments

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kz.just_code.devmuscles.ItemWorkoutAdapter
import kz.just_code.devmuscles.R
import kz.just_code.devmuscles.repository.workout.WorkoutViewModel
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.databinding.FragmentHomeBinding


@AndroidEntryPoint
class HomeFragment:BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {


    private val viewModel: WorkoutViewModel by viewModels()
    override fun onBindView() {
        super.onBindView()
        val adapter = ItemWorkoutAdapter()
        with(binding){
            workoutList.adapter = adapter
            workoutList.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            viewModel.workoutListLiveData.observe(viewLifecycleOwner){
                adapter.submitList(it.orEmpty())
            }
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
}