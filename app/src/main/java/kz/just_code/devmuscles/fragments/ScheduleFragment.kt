package kz.just_code.devmuscles.fragments

import android.util.Log
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kz.just_code.devmuscles.adapter.ItemWorkoutAdapter
import kz.just_code.devmuscles.adapter.ScheduleAdapter
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.databinding.FragmentScheduleBinding
import kz.just_code.devmuscles.firebase.SavedWorkout
import kz.just_code.devmuscles.firebase.UserDao
import kz.just_code.devmuscles.repository.workout.model.Workout
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject


@AndroidEntryPoint
class ScheduleFragment: BaseFragment<FragmentScheduleBinding>(FragmentScheduleBinding::inflate) {


    @Inject
    lateinit var userDao:UserDao
    var workouts:MutableList<SavedWorkout> = mutableListOf()
    override fun onBindView() {
        var selectedDate = ""
        var workoutId = ""
        super.onBindView()
        val adapter = ScheduleAdapter()
        binding.recyclerView.adapter = adapter

        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            selectedDate = formatDate(dayOfMonth, month+1, year)
        }
        binding.selectBtn.setOnClickListener {
            workouts.clear()
            userDao.getData()
        }
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        adapter.itemClick = { it ->
            userDao.changeCompleteStatus(value = true, workoutId)
            findNavController().navigate(
                ScheduleFragmentDirections.actionScheduleToStartWorkoutFragment(it.workout)
            )
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        userDao.getDataLiveData.observe(this){
            val workoutMap = it?.favoriteList
            workoutMap?.forEach {item->
                if(item.value.savedTime == selectedDate) {
                    workoutId = item.key
                    workouts.add(item.value)
                }
            }
                if(workouts.isEmpty()){
                    binding.animation.playAnimation()
                    binding.animation.isVisible = true
                    binding.recyclerView.isVisible = false

                }
                else{
                    binding.animation.isVisible = false
                    binding.recyclerView.isVisible = true
                }

                adapter.submitList(workouts)
            Log.d("Workouts", "$workouts")
            }
        }

    }
    private fun formatDate(day: Int, month: Int, year: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month-1, day)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

