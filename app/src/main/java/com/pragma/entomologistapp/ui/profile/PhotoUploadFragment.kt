package com.pragma.entomologistapp.ui.profile

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.pragma.entomologistapp.core.ext.pickMediaLauncher
import com.pragma.entomologistapp.databinding.FragmentPhotoUploadBinding

class PhotoUploadFragment : Fragment() {

    //LAUNCHER FOR PICKMEDIALAUNCHER
    private val pickMediaLauncher = pickMediaLauncher { uri ->
        if (uri != null) {
            Glide
                .with(this)
                .load(uri)
                .circleCrop()
                .into(binding.ivAvatarUser)

            findNavController().previousBackStackEntry?.savedStateHandle?.set("uriNewPhoto", uri.toString() )
            findNavController().popBackStack()
        } else {
            findNavController().previousBackStackEntry?.savedStateHandle?.set("uriNewPhoto", null )
            findNavController().popBackStack()
        }
    }

    private var _binding: FragmentPhotoUploadBinding? = null
    private val binding get() = _binding!!

    //GET ARGUMENTS
    private val args by navArgs<PhotoUploadFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoUploadBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents()

    }

    private fun initComponents() {
        //SE OBTIENE LA URI ENVIADA
        args.uriPhoto?.let { uri ->
            Glide.with(this)
                .load( Uri.parse(uri) )
                .circleCrop()
                .into(binding.ivAvatarUser)
        }

        binding.mbUploadPhoto.setOnClickListener {
            pickMediaLauncher.launch( PickVisualMediaRequest( ActivityResultContracts.PickVisualMedia.ImageOnly ) )
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}