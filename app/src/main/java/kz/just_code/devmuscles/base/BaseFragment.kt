package kz.just_code.devmuscles.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import kz.just_code.devmuscles.utilities.BottomNavigationViewListener
import java.lang.Exception
import java.lang.RuntimeException

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB: ViewBinding>(private val inflate: Inflate<VB>): Fragment() {
    private var _binding: VB? = null

    private lateinit var bottomNavigationViewListener: BottomNavigationViewListener
    open var showBottomNavigation:Boolean = true

    val binding get() = _binding ?: throw RuntimeException()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            bottomNavigationViewListener.showBottomNavigationView(showBottomNavigation)
            onInit()
            onBindView()
            bindViewModel()
        } catch (e: Exception) {
            Log.e("OnViewCreated", "Exception by view binding: ${e.message}")
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

    open fun onInit() { }
    open fun onBindView() {}
    open fun bindViewModel() {}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}