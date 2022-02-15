package com.example.learningmanager.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.learningmanager.base.ext.collectWith
import com.example.learningmanager.fragments.notesmanager.data.NoteData

// What is typealias exactly, for what exactly we use inflate?
typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel>(private val inflate: Inflate<VB>) :
    Fragment() {

    private var binding: VB? = null
    protected val layout: VB
        get() = binding!!

    abstract val vm: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflate.invoke(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
collectNavigationEvents()
        //alert events
        //etc?
    }

    private fun collectNavigationEvents() {
        vm.navDestination.collectWith(viewLifecycleOwner) {
            findNavController().navigate(it.createDirections())
        }

        vm.shouldNavBack.collectWith(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}
