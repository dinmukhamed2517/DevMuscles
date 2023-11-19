package kz.just_code.devmuscles.fragments

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kz.just_code.devmuscles.ItemWorkoutAdapter
import kz.just_code.devmuscles.repository.workout.WorkoutViewModel
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.databinding.FragmentWorkoutsBinding


@AndroidEntryPoint
class WorkoutsFragment:BaseFragment<FragmentWorkoutsBinding>(FragmentWorkoutsBinding::inflate) {


    private val viewModel: WorkoutViewModel by viewModels()
    override fun onBindView() {
        super.onBindView()
        val adapter = ItemWorkoutAdapter()

        setUpError()
        setUpLoader()

        with(binding){
            workoutList.adapter = adapter
            workoutList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


            textInputLayout.setStartIconOnClickListener {
               getData()
            }
        }
        viewModel.workoutListLiveData.observe(viewLifecycleOwner){
            adapter.submitList(it.orEmpty())
        }

        adapter.itemClick = {workoutItem, shared->

            val extras = FragmentNavigatorExtras(*shared.toList().toTypedArray())
            findNavController().navigate(
                WorkoutsFragmentDirections
                    .actionWorkoutsFragmentToWorkoutDetailsFragment(workoutItem), extras
            )
        }
    }
    private fun getData(){
        if(binding.editText.text!!.isNotEmpty()){
            viewModel.getWorkoutsByTarget(binding.editText.text.toString().lowercase())
            binding.root.hideKeyboard()
            binding.workoutList.isVisible = true
            binding.textInputLayout.isErrorEnabled = false

        }
        else{
            binding.textInputLayout.error = "Enter target muscle"
            binding.textInputLayout.isErrorEnabled = true
            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
        }
    }
    private fun setUpError() {
        viewModel.exceptionLiveData.observe(this) {
//            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
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