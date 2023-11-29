package kz.just_code.devmuscles.fragments

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kz.just_code.devmuscles.ItemWorkoutAdapter
import kz.just_code.devmuscles.R
import kz.just_code.devmuscles.repository.workout.WorkoutViewModel
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.databinding.FragmentHomeBinding
import kz.just_code.devmuscles.firebase.UserDao
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment:BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    @Inject
    lateinit var firebaseAuth:FirebaseAuth
    private val viewModel: WorkoutViewModel by viewModels()
    @Inject
    lateinit var userDao:UserDao
    override fun onBindView() {
        userDao.getData()
        super.onBindView()
        val adapter = ItemWorkoutAdapter()

        viewModel.getWorkouts()
        with(binding){
            workoutList.adapter = adapter
            workoutList.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            data.text = "Today, ${getCurrentDate()}"

            viewModel.workoutListLiveData.observe(viewLifecycleOwner){
                adapter.submitList(it.orEmpty())
            }

        }
        userDao.getDataLiveData.observe(this){
            binding.greating.text = "Hello, ${it?.name}"
            Glide.with(requireContext())
                .load(it?.pictureUrl)
                .into(binding.avatar)

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