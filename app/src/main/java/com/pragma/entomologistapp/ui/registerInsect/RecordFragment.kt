package com.pragma.entomologistapp.ui.registerInsect

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.pragma.entomologistapp.R
import com.pragma.entomologistapp.databinding.FragmentRecordBinding


class RecordFragment : Fragment() {

    private var _binding: FragmentRecordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRecordBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponent()
    }

    private fun initComponent() {
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_recordFragment_to_formInsectFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}