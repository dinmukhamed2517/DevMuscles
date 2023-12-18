package kz.just_code.devmuscles.fragments

import android.app.DatePickerDialog
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kz.just_code.devmuscles.adapter.ItemRecommendedWorkoutAdapter
import kz.just_code.devmuscles.R
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.databinding.FragmentWorkoutDetailsBinding
import kz.just_code.devmuscles.firebase.SavedWorkout
import kz.just_code.devmuscles.firebase.UserDao
import kz.just_code.devmuscles.repository.workout.WorkoutViewModel
import kz.just_code.devmuscles.repository.workout.model.Workout
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject


@AndroidEntryPoint
class WorkoutDetailsFragment :
    BaseFragment<FragmentWorkoutDetailsBinding>(FragmentWorkoutDetailsBinding::inflate) {
    private val args: WorkoutDetailsFragmentArgs by navArgs()
    private val viewModel: WorkoutViewModel by activityViewModels<WorkoutViewModel>()

    @Inject
    lateinit var userDao: UserDao

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    private val calendar = Calendar.getInstance()


    override fun onBindView() {
        showBottomSheet()
        val workoutItem = args.workout
        super.onBindView()
        val adapter = ItemRecommendedWorkoutAdapter()
        binding.list.adapter = adapter
        binding.list.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        with(binding) {
            bodyPart.text = workoutItem.bodyPart?.titlecaseFirstChar()
            var imageRes: Int? = null
            when (workoutItem.target) {
                "abs" -> {
                    imageRes = R.drawable.first
                }

                "delts" -> {
                    imageRes = R.drawable.girl

                }

                "biceps" -> {
                    imageRes = R.drawable.biceps

                }

                "calves" -> {
                    imageRes = R.drawable.calves

                }

                "quads" -> {
                    imageRes = R.drawable.legs

                }

                "triceps" -> {
                    imageRes = R.drawable.triceps

                }

                "lats" -> {
                    imageRes = R.drawable.lats
                }

                "glutes" -> {
                    imageRes = R.drawable.girl1
                }

                else -> {
                    imageRes = R.drawable.the_rest

                }
            }
            mainImage.setImageResource(imageRes)

            requireActivity().runOnUiThread {
                try {
                    requireActivity().runOnUiThread {
                        getWorkouts()
                    }
                } catch (e: Exception) {
                    Log.e("WorkoutDetailsFragment", "error on main thread", e)
                }
            }
            addBtn.setOnClickListener {
                setUpWorkout(workoutItem)

            }
            backBtn.setOnClickListener {
                findNavController().navigate(
                    WorkoutDetailsFragmentDirections.actionWorkoutDetailsFragmentToHome()
                )
            }
            mainCard.setOnClickListener {
                showBottomSheet()
            }
            adapter.itemClick = {
                findNavController().navigate(
                    WorkoutDetailsFragmentDirections.itself(it)
                )
            }

        }
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        viewModel.workoutListLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }


    }

    private fun showBottomSheet() {
        val action =
            WorkoutDetailsFragmentDirections.actionWorkoutDetailsFragmentToWorkoutDetailsBottomSheetFragment(
                args.workout
            )
        findNavController().navigate(action)
    }

    private fun getWorkouts() {
        viewModel.getWorkoutsByTarget(args.workout.target.toString().lowercase())
    }

    private fun setUpWorkout(value: Workout) {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { DatePicker, year: Int, month: Int, day: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, day)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val pickedDate = dateFormat.format(selectedDate.time)
                userDao.saveWorkoutToList(SavedWorkout(value, pickedDate, false))
                showCustomDialog("Success", "Workout is scheduled")

            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
        )
        datePickerDialog.show()
    }

}

fun String.titlecaseFirstChar() = replaceFirstChar(Char::titlecase)