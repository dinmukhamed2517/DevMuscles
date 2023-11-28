package kz.just_code.devmuscles.fragments

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kz.just_code.devmuscles.ItemWorkoutAdapter
import kz.just_code.devmuscles.R
import kz.just_code.devmuscles.repository.workout.WorkoutViewModel
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment:BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    @Inject
    lateinit var firebaseAuth:FirebaseAuth
    private val viewModel: WorkoutViewModel by viewModels()
    override fun onBindView() {
        val user = firebaseAuth.currentUser
        super.onBindView()
        val adapter = ItemWorkoutAdapter()

        viewModel.getWorkouts()
        with(binding){
            workoutList.adapter = adapter
            workoutList.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            if (user != null){
                greating.text = "Hello, ${user.displayName}"
            }
            data.text = "Today, ${getCurrentDate()}"

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
    private fun getCurrentDate(): String {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd MMM")
        return dateFormat.format(currentDate)
    }
    override fun onStart() {
        super.onStart()
        if(firebaseAuth.currentUser == null){
            findNavController().navigate(
                HomeFragmentDirections.actionHomeToLoginFragment()
            )
        }
    }
}