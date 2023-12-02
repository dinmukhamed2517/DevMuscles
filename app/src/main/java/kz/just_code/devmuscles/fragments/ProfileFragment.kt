package kz.just_code.devmuscles.fragments

import android.animation.ValueAnimator
import android.graphics.Color
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import dagger.hilt.android.AndroidEntryPoint
import kz.just_code.devmuscles.R
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.databinding.FragmentProfileBinding
import kz.just_code.devmuscles.firebase.Level
import kz.just_code.devmuscles.firebase.UserDao
import kz.just_code.devmuscles.repository.workout.model.Workout
import kz.just_code.devmuscles.utilities.calculateDailyCalories
import java.lang.Math.ceil
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment :BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate){
    @Inject  lateinit var firebaseAuth:FirebaseAuth

    @Inject lateinit var userDao: UserDao


    var workouts:MutableList<Workout> = mutableListOf()
    var completedWorkouts:MutableList<Workout> = mutableListOf()


    var weight:Int? = null
    var height:Int? = null
    var dailyCalories:Double?= null


    override fun onBindView() {

        userDao.getData()
        super.onBindView()
        with(binding){
            signOutBtn.setOnClickListener {
                signOut()
            }
            settingsBtn.setOnClickListener {
                findNavController().navigate(
                    R.id.action_profile_to_editProfileFragment
                )
            }
            backBtn.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        userDao.getDataLiveData.observe(this){
            weight = it?.weight
            height = it?.height
            val age = it?.age
            val goalWeight = it?.goalWeight
            val goal = it?.goal
            dailyCalories = calculateDailyCalories(weight?.toDouble(), goalWeight?.toDouble(), age, height?.toDouble(), goal)
            binding.username.text = it?.name
            binding.weight.text = it?.weight.toString()
            binding.height.text = it?.height.toString()
            if(it?.pictureUrl !=null){
                Glide.with(requireContext())
                    .load(it?.pictureUrl)
                    .into(binding.avatar)
            }
            else{
                binding.avatar.setImageResource(R.drawable.baseline_person_24)
            }
            val workoutMap = it?.favoriteList
            workoutMap?.forEach {item->
                if(item.value.savedTime == getCurrentDate()){
                    if(item.value.completed){
                        completedWorkouts.add(item.value.workout)
                    }
                    workouts.add(item.value.workout)
                    Log.d("Completed workouts", "$completedWorkouts")
                    Log.d("Workouts", "$workouts")
                }
            }

            var level:String? = null
            when(it?.level){
                Level.BEGINNER -> level = "Beginner"
                Level.INTERMEDIATE -> level = "Intermediate"
                Level.ADVANCE -> level = "Advance"
                else -> {
                    level = "Not selected"
                }
            }
            binding.level.text = level
            heightAnimation()
            weightAnimation()
            percentAnimation()
            caloriesAnimation()
        }


    }




    fun getCurrentDate(): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return currentDate.format(formatter)
    }

    private fun signOut(){
        var alertDialog: AlertDialog? = null
        alertDialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Sign out")
            .setMessage("Are you sure you want to sign out?")
            .setPositiveButton("Yes") { _, _ ->
                firebaseAuth.signOut()
                alertDialog?.dismiss()
                findNavController().navigate(
                    R.id.action_profile_to_loginFragment
                )
            }
            .setNegativeButton("Cancel") { _, _ ->
                alertDialog?.dismiss()
            }
            .show()
    }
    private fun weightAnimation(){
        val animator = weight?.toFloat()?.let { ValueAnimator.ofFloat(0f, it) }
        animator?.duration = 500
        animator?.start()
        animator?.addUpdateListener(object:ValueAnimator.AnimatorUpdateListener{
            override fun onAnimationUpdate(animation: ValueAnimator) {
                val floatValue = animation.animatedValue as Float
                val intValue = floatValue.toInt()
                binding.weight.text = intValue.toString()
            }
        })
    }

    private fun heightAnimation(){
        val animator = height?.toFloat()?.let { ValueAnimator.ofFloat(0f, it) }
        animator?.duration = 500
        animator?.start()
        animator?.addUpdateListener(object:ValueAnimator.AnimatorUpdateListener{
            override fun onAnimationUpdate(animation: ValueAnimator) {
                val floatValue = animation.animatedValue as Float
                val intValue = floatValue.toInt()
                binding.height.text = intValue.toString()
            }
        })
    }
    private fun caloriesAnimation(){
        val animator = dailyCalories?.toFloat()?.let { ValueAnimator.ofFloat(0f, it) }
        animator?.duration = 500
        animator?.start()
        animator?.addUpdateListener(object:ValueAnimator.AnimatorUpdateListener{
            override fun onAnimationUpdate(animation: ValueAnimator) {
                val floatValue = animation.animatedValue as Float
                val intValue = floatValue.toInt()
                binding.dailyCalories.text = intValue.toString()
            }
        })
    }

    private fun percentAnimation(){

        if(workouts.isEmpty()){
            binding.progressBar.isVisible = false
            binding.percentageText.isVisible = false
            binding.noWorkoutText.isVisible = true
        }
        else {
            binding.progressBar.isVisible = true
            binding.percentageText.isVisible = true
            binding.noWorkoutText.isVisible = false

            val animator = ValueAnimator.ofFloat(0f, getPersentage())
            animator?.duration = 500
            animator?.start()
            animator?.addUpdateListener(object:ValueAnimator.AnimatorUpdateListener{
                override fun onAnimationUpdate(animation: ValueAnimator) {
                    val floatValue = animation.animatedValue as Float
                    val intValue = floatValue.toInt()
                    binding.percentageText.text = "${intValue}%"
                    binding.progressBar.apply {
                        setProgressWithAnimation(getPersentage(), 1000)
                        progressMax = 100f
                        progressBarColorStart = Color.RED
                        progressBarColorEnd = Color.GREEN
                        progressBarColorDirection = CircularProgressBar.GradientDirection.TOP_TO_BOTTOM
                    }
                }
            })
        }
    }

    private fun getPersentage():Float{
        val division:Float = completedWorkouts.size.toFloat()/ workouts.size
        return (division*100)
    }
}