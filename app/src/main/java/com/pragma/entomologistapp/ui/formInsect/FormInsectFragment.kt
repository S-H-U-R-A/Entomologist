package com.pragma.entomologistapp.ui.formInsect

import android.net.Uri
import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.pragma.entomologistapp.core.ext.pickMediaLauncher
import com.pragma.entomologistapp.core.ext.validateFields
import com.pragma.entomologistapp.databinding.FragmentFormInsectBinding
import com.pragma.entomologistapp.domain.model.EntomologistDomain
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class FormInsectFragment : Fragment() {

    //LAUNCHER FOR PICKMEDIALAUNCHER
    private val pickMediaLauncher = pickMediaLauncher { uri ->
        if (uri != null) {
            //SE CARGA EN EL VIEW LA IMAGEN SELECCIONADA
            handleImageInsectSelected( uri )

            //SE GUARDA LA IMAGEN DE INSECTO SELECCIONADA
            imageInsectUriSelected = uri

        }
    }

    private var _binding: FragmentFormInsectBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FormInsectViewModel by viewModels()

    private var imageInsectUriSelected: Uri? = null

    private var listNameInsect: List<String> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormInsectBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents()
        initObservers()
    }

    private fun initComponents() {

        //CARGA DE INFORMACIÓN DEL USUARIO
        viewModel.loadUser()

        binding.ibAvatarInsect.setOnClickListener {
            //SE LANZA EL VISUALIZADOR DE LA GALERIA
            pickMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        //SE CREA UN OBSERVADOR COMUN
        val validateFields: TextWatcher = validateFields {
            binding.mbSaveInsect.isEnabled = handleValidateFields()
            binding.mbSelectedInsect.isEnabled = handleValidateFields()
        }

        //SE ASIGNA EL OBSERVADOR A LOS CAMPOS
        with(binding){
            mactvNameInsect.addTextChangedListener( validateFields )
            tietInfo.addTextChangedListener( validateFields )
        }

        //BOTONES DE GUARDADO
        binding.mbSaveInsect.setOnClickListener {
            handleRegisterInsect()
        }

        binding.mbSelectedInsect.setOnClickListener {
            handleRegisterInsect()
        }

        //SELECCION DE BOTON PARA GUARDAR
        binding.mactvNameInsect.doOnTextChanged { text, _, _, _ ->
            if( listNameInsect.contains( text.toString() ) ){
                viewModel.setVisibilityButtons(saveButton = false, selectedButton = true)
            }else{
                viewModel.setVisibilityButtons(saveButton = true, selectedButton = false)
            }
        }

    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.uiState.collect { state ->
                    handleListSuggestions( state.listInsect )
                    handlePhotoUser(state.photoEntomologist)
                    handleNavigation( state.canNavigate )
                    handleButtons( state.isVisibleButtonSave, state.isVisibleButtonSelected )
                }
            }
        }
    }

    private fun handleRegisterInsect(){
        Log.d("ImageValue", imageInsectUriSelected.toString())
        if(imageInsectUriSelected != null){
            with(binding){
                registerInsect(
                    mactvNameInsect.text.toString(),
                    imageInsectUriSelected!!,
                    tietInfo.text.toString()
                )
            }
        }else{
            Toast.makeText(context, "Por favor seleccione una imagen", Toast.LENGTH_SHORT).show()
        }
    }

    private fun registerInsect(name: String, image: Uri, moreInfo: String) {
        viewModel.saveInsect(
            name,
            image,
            moreInfo
        ){ insectInserted ->
            val action: NavDirections = FormInsectFragmentDirections.actionFormInsectFragmentToCountInsectFragment(insectInserted)
            findNavController().navigate(action)
        }
    }

    private fun handlePhotoUser(photoEntomologist: String) {
        if (photoEntomologist != EntomologistDomain.IMAGE_DEFAULT) {
            Glide.with(requireContext())
                .load(File(photoEntomologist))
                .circleCrop()
                .into(binding.layoutAvatarProfile.ivAvatarUser)
        }
    }

    private fun handleListSuggestions(listInsect: List<String>) {

        //SE GUARDA LA LISTA DE INSECTOS
        listNameInsect = listInsect

        /*val suggestions: List<String> = listInsect.map { insects ->
            insects.name
        }*/
        binding.mactvNameInsect.setAdapter(
            ArrayAdapter(
                requireContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                listInsect
            )
        )

    }

    private fun handleButtons(buttonSave: Boolean, buttonSelected: Boolean) {
        with(binding){
            mbSaveInsect.visibility = if(buttonSave) View.VISIBLE else View.GONE
            mbSelectedInsect.visibility = if(buttonSelected) View.VISIBLE else View.GONE
        }
    }

    private fun handleNavigation(canNavigate: Boolean) {
        if(canNavigate){
            Toast.makeText(context, "Podemos navegar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleImageInsectSelected(uri: Uri) {
        Glide
            .with(this)
            .load(uri)
            .circleCrop()
            .into( binding.ibAvatarInsect )
    }

    private fun handleValidateFields(): Boolean {
        var isValid: Boolean = true

        if( binding.mactvNameInsect.text.toString().trim().isEmpty() ){
            isValid = false
        }

        if( binding.tietInfo.text.toString().trim().isEmpty() ){
            isValid = false
        }

        return isValid
    }

    override fun onResume() {
        super.onResume()
        //SE CARGA DE NUEVO LA IMAGEN SEleCCIONADA PREVIAMENTE
        Glide
            .with(this)
            .load(imageInsectUriSelected)
            .circleCrop()
            .into( binding.ibAvatarInsect )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}