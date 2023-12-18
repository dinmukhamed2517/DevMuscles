package kz.just_code.devmuscles.fragments

import android.content.Context
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kz.just_code.devmuscles.adapter.ItemWorkoutAdapter
import kz.just_code.devmuscles.repository.workout.WorkoutViewModel
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.databinding.FragmentWorkoutsBinding
import kz.just_code.devmuscles.repository.workout.model.Workout


@AndroidEntryPoint
class WorkoutsFragment : BaseFragment<FragmentWorkoutsBinding>(FragmentWorkoutsBinding::inflate) {


    private val viewModel: WorkoutViewModel by viewModels()


    override fun onBindView() {
        super.onBindView()
        val adapter = ItemWorkoutAdapter()

        setUpError()
        setUpLoader()

        with(binding) {
            workoutList.adapter = adapter
            workoutList.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            editText.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getData()
                }

                false
            })
            nameChip.setOnClickListener {
                viewModel.setSortedWorkouts(
                    viewModel.workoutListLiveData.value?.sortByName() ?: emptyList()
                )
            }
            targetChip.setOnClickListener {

                viewModel.setSortedWorkouts(
                    viewModel.workoutListLiveData.value?.sortByTarget() ?: emptyList()
                )
            }
            equipment.setOnClickListener {
                viewModel.setSortedWorkouts(
                    viewModel.workoutListLiveData.value?.sortByEquipment() ?: emptyList()
                )
            }
            backBtn.setOnClickListener {
                findNavController().popBackStack()
            }


        }
        viewModel.workoutListLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        adapter.itemClick = { workoutItem, shared ->

            val extras = FragmentNavigatorExtras(*shared.toList().toTypedArray())
            findNavController().navigate(
                WorkoutsFragmentDirections
                    .actionWorkoutsFragmentToWorkoutDetailsFragment(workoutItem), extras
            )
        }
    }

    private fun getData() {
        if (binding.editText.text!!.isNotEmpty()) {
            viewModel.getWorkoutsByTarget(binding.editText.text.toString().lowercase())
            binding.root.hideKeyboard()
            binding.workoutList.isVisible = true
            binding.textInputLayout.isErrorEnabled = false

        } else {
            binding.textInputLayout.error = "Enter target muscle"
            binding.textInputLayout.isErrorEnabled = true
            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpError() {
        viewModel.exceptionLiveData.observe(this) {
            binding.textInputLayout.error = "Entered wrong data"
            binding.textInputLayout.isErrorEnabled = true
            binding.workoutList.isVisible = false
        }
    }

    private fun setUpLoader() {
        viewModel.loadingLiveData.observe(this) {
            binding.loading.isVisible = it
        }
    }

}

fun View.hideKeyboard() {
    val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}


fun List<Workout>.sortByName(): List<Workout> {
    return this.sortedBy { it.name.orEmpty() }
}

fun List<Workout>.sortByTarget(): List<Workout> {
    return this.sortedBy { it.target.orEmpty() }
}

fun List<Workout>.sortByEquipment(): List<Workout> {
    return this.sortedBy { it.equipment.orEmpty() }
}