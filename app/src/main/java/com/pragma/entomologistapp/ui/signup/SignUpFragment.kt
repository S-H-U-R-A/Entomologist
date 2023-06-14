package com.pragma.entomologistapp.ui.signup


import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.pragma.entomologistapp.R
import com.pragma.entomologistapp.databinding.FragmentSignUpBinding
import com.pragma.entomologistapp.util.PermissionMultipleRequestHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    //VIEWBINDING
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    //URI PARA EL SIGUIENTE FRAGMENTO
    private var imageUriSelected: String? = null

    //VIEWMODEL
    private val viewModel: SignUpViewModel by viewModels()

    //LAUNCHER PERMISSION LOCATION
    private val locationPermission =  PermissionMultipleRequestHelper<SignUpFragment>(
        this,
        arrayOf(
            ACCESS_COARSE_LOCATION,
            ACCESS_FINE_LOCATION
        ),
        onDenied = {
            binding.msLocation.isChecked = false
            //Toast.makeText( requireContext(), "Pailas", Toast.LENGTH_SHORT).show()
        },
        onShowRationale = {
            binding.msLocation.isChecked = false
            //Toast.makeText( requireContext(), "Toca insistirle", Toast.LENGTH_SHORT).show()
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        _binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents()
        initObservers()

    }

    private fun initComponents(){

        //GO PAGE UPLOADPHOTO
        binding.ibAvatar.setOnClickListener {
            val action: NavDirections = SignUpFragmentDirections.actionSignUpFragmentToPhotoUploadFragment(imageUriSelected)
            findNavController().navigate( action )
        }

        //REQUEST GEOLOCATION PERMISSION
        binding.msLocation.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                locationPermission.runWithPermission {

                    //Toast.makeText( requireContext(), "Todo melo Ejecutandose", Toast.LENGTH_SHORT).show()

                }
            }
        }

        //EVENT TEXT CHANGE
        binding.tietUser.doOnTextChanged { text, _, _, _ ->
            text?.let { text ->
                if( text.length > 2 ){
                    viewModel.setSaveButton( true )
                }else{
                    viewModel.setSaveButton( false )
                }
            }
        }

        //SAVE ENTOMOLOGIST
        binding.mbSave.setOnClickListener {
            //LÓGICA DE GUARDAR EL USUARIO Y NAVEGAR
            registerEntomologist(
                null,
                binding.tietUser.text.toString().trim(),
                imageUriSelected ?: URL_PHOTO_DEFAULT
            )
        }
        //SKIP
        binding.mbSkip.setOnClickListener {
            //LÓGICA DE GUARDAR EL USUARIO Y NAVEGAR
            registerEntomologist(
                null,
                binding.tietUser.text.toString().trim().ifEmpty { NAME_DEFAULT },
                imageUriSelected ?: URL_PHOTO_DEFAULT
            )
        }

    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.uiState.collect{ stateUi ->
                    handleSaveButton( stateUi.canSave )
                    handleNavigate( stateUi.isFirstTime )
                }
            }
        }
    }

    private fun handleNavigate(firstTime: Boolean) {
        if(!firstTime){
             val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.recordFragment, true)
                .build()
             findNavController().navigate(R.id.recordFragment, null, navOptions)
        }
    }

    private fun handleSaveButton( canSave: Boolean) {
        binding.mbSave.isEnabled = canSave
    }

    private fun registerEntomologist( id:Int?, name: String, urlPhoto: String ){
        viewModel.saveEntomologist(
            id,
            name,
            urlPhoto
        )
    }

    override fun onResume() {
        super.onResume()

        //SE OBTIENE LA URI RETORNADA
        imageUriSelected = findNavController().currentBackStackEntry?.savedStateHandle?.get<String>("uriNewPhoto")

        imageUriSelected?.let {
            Glide.with(this)
                .load( Uri.parse(imageUriSelected) )
                .circleCrop()
                .into(binding.ibAvatar)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val IS_FIRST_TIME: String = "IS_FIRST_TIME"
        const val NAME_DEFAULT: String = "sin nombre"
        const val URL_PHOTO_DEFAULT: String = "Sin foto"
    }

}