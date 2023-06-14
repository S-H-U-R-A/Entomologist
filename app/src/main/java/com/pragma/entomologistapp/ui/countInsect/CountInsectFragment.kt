package com.pragma.entomologistapp.ui.countInsect

import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.pragma.entomologistapp.core.ext.formatTwoDigits
import com.pragma.entomologistapp.databinding.FragmentCountInsectBinding
import com.pragma.entomologistapp.domain.model.InsectDomain
import com.pragma.entomologistapp.util.PermissionMultipleRequestHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class CountInsectFragment : Fragment() {

    //LAUNCHER PERMISSION LOCATION
    private val locationPermission = PermissionMultipleRequestHelper<CountInsectFragment>(
        this,
        arrayOf(
            ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ),
        onDenied = {

        },
        onShowRationale = {

        }
    )

    private var _binding: FragmentCountInsectBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CountInsectViewModel by viewModels()

    private val args by navArgs<CountInsectFragmentArgs>()

    private var insect: InsectDomain? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //SE RECUPERA EL ID ENVIADO POR LA NAVEGACIÓN
        _binding = FragmentCountInsectBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents()
        initObservers()

    }

    private fun initComponents() {
        //SE RECUPERA EL ID DEL INSECTO
        insect = args.insect

        //SE CARGAN LOS DATOS DEL INSECTO
        setDataInsect(insect!!)

        binding.fabCountPlus.setOnClickListener {
            viewModel.plusInsect()
        }

        binding.fabCountMinus.setOnClickListener {
            viewModel.minusInsect()
        }

        binding.mbSaveInsect.setOnClickListener {
            validatePermission(
                ACCESS_COARSE_LOCATION,
                ACCESS_FINE_LOCATION
            )
        }

    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { stateUi ->
                    handleCountInsect(stateUi.countInsect)
                    handleButtonSave(stateUi.isVisibleButtonSave)
                    handleComment(stateUi.isVisibleComment)
                }
            }
        }
    }



    private fun setDataInsect(insect: InsectDomain) {

        Glide.with(requireContext())
            .load(File(insect.urlPhoto))
            .circleCrop()
            .into(binding.ivInsect)

        binding.mtvNameInsect.text = insect.name

    }

    private fun handleCountInsect(countInsect: Int) {
        binding.mtvCountInsect.text = countInsect.formatTwoDigits()
    }

    private fun handleButtonSave(visibleButtonSave: Boolean) {
        binding.mbSaveInsect.isVisible = visibleButtonSave
    }

    private fun handleComment(visibleComment: Boolean) {
        binding.tilComment.isVisible = visibleComment
    }

    private fun validatePermission(accessCoarseLocation: String, accessFineLocation: String) {
        if(
            ContextCompat.checkSelfPermission(
                requireContext(),
                ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            &&
            ContextCompat.checkSelfPermission(
                requireContext(),
                ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        ) {
            //PODEMOS USAR LA UBICACIÓN
        }else{
            locationPermission.runWithPermission {
                //PODEMOS USAR LA UBICACIÓN
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}