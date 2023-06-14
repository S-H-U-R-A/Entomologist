package com.pragma.entomologistapp.ui.recordInsect


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.pragma.entomologistapp.R
import com.pragma.entomologistapp.databinding.FragmentRecordBinding
import com.pragma.entomologistapp.domain.model.EntomologistDomain
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class RecordFragment : Fragment() {

    private var _binding: FragmentRecordBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecordInsectViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponent()
        initObservers()

    }

    private fun initComponent() {

        //SE CARGA LA INFO DEL USUARIO
        viewModel.loadUser()

        binding.fabAdd.setOnClickListener {
            val action = RecordFragmentDirections.actionRecordFragmentToFormInsectFragment()
            findNavController().navigate(action)
        }

    }

    private fun initObservers() {

        viewModel.firstTime.observe(viewLifecycleOwner){ firstTime ->
            handleNavigation( firstTime )
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.uiState.collect { state ->
                    handlePhotoUser( state.imageEntomologist )
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        //SE CARGA LA INFO DEL USUARIO
        viewModel.loadUser()
    }

    private fun handleNavigation(firstTime: Boolean) {
        if(firstTime){
            findNavController().navigate(R.id.signUpFragment)
        }
    }

    private fun handlePhotoUser(imageEntomologist: String) {

        if( imageEntomologist != EntomologistDomain.IMAGE_DEFAULT){

            Glide.with(requireContext())
                .load( File( imageEntomologist) )
                .circleCrop()
                .into(binding.layoutAvatarProfile.ivAvatarUser)

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}