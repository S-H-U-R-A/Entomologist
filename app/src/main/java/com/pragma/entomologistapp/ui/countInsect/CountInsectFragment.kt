package com.pragma.entomologistapp.ui.countInsect

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pragma.entomologistapp.R
import com.pragma.entomologistapp.core.ext.checkMultiplePermissionGranted
import com.pragma.entomologistapp.core.ext.formatTwoDigits
import com.pragma.entomologistapp.core.ext.showSimpleMessageSnackBar
import com.pragma.entomologistapp.databinding.FragmentCountInsectBinding
import com.pragma.entomologistapp.domain.model.InsectDomain
import com.pragma.entomologistapp.ui.countInsect.LocationMessage.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class CountInsectFragment : Fragment() {

    private val requestLocationLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){ isGranted: Boolean ->
        if(isGranted){
            //REGISTRAR EL INSECTO
            enterInsectCount()
        } else {
            viewModel.sendMessageEntomologist(LOCATION_PERMISSION_DENIED)
        }
    }

    private var _binding: FragmentCountInsectBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CountInsectViewModel by viewModels()

    private val args by navArgs<CountInsectFragmentArgs>()

    private var insect: InsectDomain? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        _binding = FragmentCountInsectBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents()
        initObservers()

    }

    private fun initComponents() {
        //SE RECUPERA EL INSECTO
        insect = args.insect
        //SE RECUPERA EL ID DEL ENTOMÓLOGO

        //SE CARGAN LOS DATOS DEL INSECTO
        setDataInsect(insect!!)

        binding.fabCountPlus.setOnClickListener {
            viewModel.plusInsect()
        }
        binding.fabCountMinus.setOnClickListener {
            viewModel.minusInsect()
        }

        binding.mbSaveInsect.setOnClickListener {
            viewModel.getServicesLocationInfoApp{ isLocationUsable ->
                if(!isLocationUsable) {
                    viewModel.sendMessageEntomologist( LOCATION_NO_USABLE )
                }else{
                    //VALIDAR LOS PERMISOS Y HACER EL REGISTRO DE LA INFORMACIÓN
                    checkPermissionsLocationAndRegisterInsect()
                }
            }
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .collect { stateUi ->
                        handleCountInsect( stateUi.countInsect )
                        handleButtonSave( stateUi.isVisibleButtonSave )
                        handleComment( stateUi.isVisibleComment )
                        handleMessagesEntomologist( stateUi.messageEntomologist )
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

    private fun handleMessagesEntomologist(messageEntomologist: List<LocationMessage>) {
        messageEntomologist.forEach { messageStateUi ->
            when( messageStateUi ){
                LOCATION_NO_USABLE -> {
                    requireContext().showSimpleMessageSnackBar(
                        binding.root,
                        LOCATION_NO_USABLE.message
                    )
                }
                LOCATION_PERMISSION_DENIED -> {
                    requireContext().showSimpleMessageSnackBar(
                        binding.root,
                        LOCATION_PERMISSION_DENIED.message
                    )
                }
                LOCATION_EXPLANATION_PERMISSION -> {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle( R.string.app_name )
                        .setMessage( LOCATION_EXPLANATION_PERMISSION.message )
                        .setNegativeButton( getString(R.string.dialog_button_negative) ) { dialog, _ ->
                            dialog.dismiss()
                        }
                        .setPositiveButton(getString(R.string.dialog_button_positive)) { _, _ ->
                            requestLocationLauncher.launch(
                                ACCESS_FINE_LOCATION
                            )
                        }
                        .show()
                }
                GET_LOCATION_ERROR -> {
                    requireContext().showSimpleMessageSnackBar(
                        binding.root,
                        GET_LOCATION_ERROR.message
                    )
                }
            }
            //APENAS SE MUESTRA EL MENSAJE, SE ELIMINA DEL ESTADO
            viewModel.deleteMessageEntomologist( messageStateUi )
        }
    }

    private fun checkPermissionsLocationAndRegisterInsect() {
        when {
            requireContext().checkMultiplePermissionGranted( ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION ) -> {
                enterInsectCount()
            }
            shouldShowRequestPermissionRationale( ACCESS_FINE_LOCATION ) -> {
                viewModel.sendMessageEntomologist( LOCATION_EXPLANATION_PERMISSION )
            }
            else -> {
                requestLocationLauncher.launch(
                    ACCESS_FINE_LOCATION
                )
            }
        }
    }

    private fun enterInsectCount() {
        viewModel.enterInsectCount()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}