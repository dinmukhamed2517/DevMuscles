package kz.just_code.devmuscles.fragments

import android.R
import android.content.Context
import android.view.inputmethod.EditorInfo
import android.widget.TextView
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
import kz.just_code.devmuscles.repository.gpt.model.RequestBody
import kz.just_code.devmuscles.utilities.BottomNavigationViewListener


@AndroidEntryPoint
class ChatFragment:BaseFragment<FragmentChatBinding>(FragmentChatBinding::inflate) {

    private lateinit var bottomNavigationViewListener: BottomNavigationViewListener
    private val viewModel: GptViewModel by viewModels()
    private val messageList = mutableListOf<Choice>()
    override fun onBindView() {
        super.onBindView()
        setUpLoader()
        bottomNavigationViewListener.showBottomNavigationView(false)

        binding.animation.playAnimation()
        val adapter = ItemMessageAdapter()
        binding.messageList.adapter = adapter
        binding.messageList.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL, false)

        binding.editText.setOnEditorActionListener(TextView.OnEditorActionListener{_, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_SEND){
                if(binding.editText.text.toString().isNotEmpty()){

                    val requestBody = RequestBody(
                        model = "gpt-3.5-turbo",
                        messages = listOf(
                            Message(
                                role = "system",
                                content = "Imagine that you are Christopher Adam Bumstead Pro League professional bodybuilder. Bumstead is the reigning five-time Mr. Olympia Classic Physique Champion, winning five consecutive titles from 2019 to 2023." +
                                        "User will ask you questions about how to train properly, how to build muscles and some similar stuff. You need to answer as well experienced fitness trainer. Remember you name is Chris Bumstead. " +
                                        "Always answer briefly in 2-3 sentences. If you are not able to fit your answer in 2 -3 sentences ask user to text additonal questions"

                            ),
                            Message(
                                role = "user",
                                content = "${binding.editText.text.toString()}"
                            )
                        ),
                        temperature = 1,
                        topP = 1,
                        n = 1,
                        stream = false,
                        maxTokens = 250,
                        presencePenalty = 0,
                        frequencyPenalty = 0
                    )
                    viewModel.getPrompt(requestBody)
                    binding.editText.text?.clear()
                }
                else{
                    Toast.makeText(requireContext(), "Please enter something", Toast.LENGTH_SHORT).show()
                }
                return@OnEditorActionListener true
            }
            false
        })
        viewModel.chatResponseLiveData.observe(viewLifecycleOwner){response->
            if(response !=null){
                messageList.add(response.choices[0])
                adapter.submitList(messageList)
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BottomNavigationViewListener) {
            bottomNavigationViewListener = context
        } else {
            throw RuntimeException("$context, error")
        }
    }

    override fun onDetach() {
        super.onDetach()
        bottomNavigationViewListener.showBottomNavigationView(true)
    }
}

