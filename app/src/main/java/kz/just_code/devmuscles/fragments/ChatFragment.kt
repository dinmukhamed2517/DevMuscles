package kz.just_code.devmuscles.fragments


import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kz.just_code.devmuscles.ItemMessageAdapter
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.databinding.FragmentChatBinding
import kz.just_code.devmuscles.repository.gpt.GptViewModel
import kz.just_code.devmuscles.repository.gpt.model.Choice
import kz.just_code.devmuscles.repository.gpt.model.Message
import java.util.UUID


@AndroidEntryPoint
class ChatFragment:BaseFragment<FragmentChatBinding>(FragmentChatBinding::inflate) {

    private val viewModel: GptViewModel by viewModels()
    private val messageList = mutableListOf<Choice>()
    var index:Int = 0
    override fun onBindView() {
        super.onBindView()
        setUpLoader()

        binding.animation.playAnimation()
        val adapter = ItemMessageAdapter()
        binding.messageList.adapter = adapter
        binding.messageList.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL, false)



        binding.til.setEndIconOnClickListener {
            if(binding.editText.text.toString().isNotEmpty()){
                viewModel.getPrompt(binding.editText.text.toString())
                val userMessage = Message("user", "${binding.editText.text.toString()}")
                val choice = Choice(index++, userMessage)
                messageList.add(choice)
                adapter.submitList(listOf(choice).toList())
                binding.editText.text?.clear()

            }
            else{
                Toast.makeText(requireContext(), "Please enter something", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.chatResponseLiveData.observe(viewLifecycleOwner){ response->
            if(response !=null){
                messageList.add(response.choices[0])
                adapter.submitList(messageList.toList())
            }
            else{
                Toast.makeText(requireContext(), "Response is empty", Toast.LENGTH_SHORT).show()
            }

        }

    }
    private fun setUpLoader() {
        viewModel.loadingLiveData.observe(this) {
            binding.loadingView.isVisible = it
        }
    }

}
