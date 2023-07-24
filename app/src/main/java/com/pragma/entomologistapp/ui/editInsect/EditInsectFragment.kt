package com.pragma.entomologistapp.ui.editInsect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.pragma.entomologistapp.core.ext.showSimpleMessageSnackBar
import com.pragma.entomologistapp.databinding.FragmentEditInsectBinding
import com.pragma.entomologistapp.domain.model.RecordInsectGeolocationDomain
import com.pragma.entomologistapp.ui.editInsect.EditInsectUserMessage.NOT_DATA_FOUND_FOR_EDIT
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditInsectFragment : Fragment() {

    private var _binding: FragmentEditInsectBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<EditInsectFragmentArgs>()

    private val viewModel: EditInsectViewModel by viewModels();

    private var _recordInsectGeolocationDomain: RecordInsectGeolocationDomain? = null;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditInsectBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
        initObservers()
    }

    private fun initComponents() {
        //LOAD INSECT WITH EXTRA DATA
        viewModel.setInsectWithExtraData( args.RecordAndInsect )
        //LISTENER SAVE BUTTON
        binding.mbSave.setOnClickListener {
            findNavController().navigate(
                EditInsectFragmentDirections.actionEditInsectFragmentToRecordFragment()
            )
        }
        //LISTENER EDIT BUTTON
        binding.mbEdit.setOnClickListener {
            if(_recordInsectGeolocationDomain != null){
                findNavController().navigate(
                    EditInsectFragmentDirections.actionEditInsectFragmentToCountInsectFragment(null, _recordInsectGeolocationDomain)
                )
            }else {
                viewModel.sendMessageEntomologist(NOT_DATA_FOUND_FOR_EDIT)
            }
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect{ uiState ->
                    handleLoadData( uiState.insectWithExtraData )
                    handleMessagesEntomologist( uiState.messageEntomologist )
                }
            }
        }
    }

    private fun handleLoadData( insectWithExtraData: RecordInsectGeolocationDomain? ) {
        insectWithExtraData?.let {
            //LOAD DATA INTO UI
            with(binding){
                mtvCountInsect.text = it.countInsect.toString()
                mtvNameInsect.text = it.nameInsect
                mtvSubHeaderInsect.text = String.format("%s %s", it.cityName, it.dateRecord )
                Glide.with(requireContext())
                    .load( it.photoInsect )
                    .centerCrop()
                    .into( ivAvatarInsect )
                mtvComments.text = it.countComment
                mtvUrlInfo.text =it.moreInfo
            }
            //LOAD DATA FOR SEND TO NEXT SCREEN
            _recordInsectGeolocationDomain = insectWithExtraData
        }
    }

    private fun handleMessagesEntomologist(messageEntomologist: List<EditInsectUserMessage>) {
        messageEntomologist.forEach { messageStateUi: EditInsectUserMessage ->
            when(messageStateUi){
                NOT_DATA_FOUND_FOR_EDIT -> {
                    requireContext().showSimpleMessageSnackBar(
                        binding.root,
                        NOT_DATA_FOUND_FOR_EDIT.message
                    )
                }
            }
            viewModel.deleteMessageEntomologist( messageStateUi )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        _recordInsectGeolocationDomain = null
    }

}