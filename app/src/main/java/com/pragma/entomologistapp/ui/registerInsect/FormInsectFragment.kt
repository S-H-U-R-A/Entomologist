package com.pragma.entomologistapp.ui.registerInsect

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pragma.entomologistapp.R
import com.pragma.entomologistapp.databinding.FragmentFormInsectBinding

class FormInsectFragment : Fragment() {

    private var _binding: FragmentFormInsectBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFormInsectBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}