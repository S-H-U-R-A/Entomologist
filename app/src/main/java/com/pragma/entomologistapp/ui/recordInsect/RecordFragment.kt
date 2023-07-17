package com.pragma.entomologistapp.ui.recordInsect


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.pragma.entomologistapp.R
import com.pragma.entomologistapp.core.ext.showOrHideDialogLoading
import com.pragma.entomologistapp.databinding.FragmentRecordBinding
import com.pragma.entomologistapp.domain.model.EntomologistDomain
import com.pragma.entomologistapp.domain.model.RecordInsectGeolocationDomain
import com.pragma.entomologistapp.domain.model.ReportInsectBySpeciesDomain
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class RecordFragment : Fragment() {

    private var _binding: FragmentRecordBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecordInsectViewModel by viewModels()

    //ADAPTERS
    private lateinit var recordsAdapter: RecordListAdapter
    private lateinit var reportsAdapter: ReportListAdapter


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
        viewModel.loadData()
        //CONFIG RECYCLERVIEW
        recordsAdapter = RecordListAdapter(){recordInsect: RecordInsectGeolocationDomain ->
            Log.d("INSECT_DOMAIN", recordInsect.toString())
            val action = RecordFragmentDirections.actionRecordFragmentToEditInsectFragment(recordInsect)
            findNavController().navigate(action)
        }

        reportsAdapter = ReportListAdapter(){ reportInsect: ReportInsectBySpeciesDomain ->
            val listRecordInsectById = viewModel.getListRecordInsectById(reportInsect.idInsect)

            Log.d("INSECT_DOMAIN", reportInsect.toString())
            Log.d("INSECT_DOMAIN_R", listRecordInsectById.toString())

        }


        binding.rvRecords.adapter = recordsAdapter
        binding.rvReports.adapter = reportsAdapter

        //ADD INSECT
        binding.fabAdd.setOnClickListener {
            val action: NavDirections = RecordFragmentDirections.actionRecordFragmentToFormInsectFragment()
            findNavController().navigate(action)
        }
        //VISIBILITY ELEMENTS IN UI
        binding.mbReports.setOnClickListener { handleVisibilityOfRecordsAndReports(true) }
        binding.mbRecords.setOnClickListener { handleVisibilityOfRecordsAndReports(false) }

    }

    private fun initObservers() {

        viewModel.firstTime.observe(viewLifecycleOwner){ firstTime ->
            handleNavigation( firstTime )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect { stateUi ->
                    handlePhotoUser( stateUi.imageEntomologist )
                    handleListRecords( stateUi.recordList )
                    handleListReports( stateUi.reportList )
                    handleButtonsAndUI( stateUi.isVisibleButtonsAndShouldAdjustUi )
                    handleLoading( stateUi.isLoading )
                }
            }
        }

    }

    private fun handleVisibilityOfRecordsAndReports(isVisibleReport: Boolean){
        with(binding){
            clAddInsect.isVisible = !isVisibleReport
            rvRecords.isVisible = !isVisibleReport
            cvReport.isVisible = isVisibleReport
        }
        handleStyleButtons(isVisibleReport)
    }

    private fun handleStyleButtons(isVisibleReport: Boolean){
        with(binding){
            if(isVisibleReport){

                mbReports.elevation = 3f
                mbReports.setBackgroundColor(  resources.getColor(R.color.color_surface, resources.newTheme() )   )
                mbReports.strokeWidth = 0

                mbRecords.elevation = 0f
                mbRecords.setBackgroundColor(  resources.getColor(R.color.color_transparent, null )   )
                mbRecords.strokeWidth = 2


            }else{
                mbReports.elevation = 0f
                mbReports.setBackgroundColor(  resources.getColor(R.color.color_transparent, null )   )
                mbReports.strokeWidth = 2

                mbRecords.elevation = 3f
                mbRecords.setBackgroundColor(  resources.getColor(R.color.color_surface, resources.newTheme() )   )
                mbRecords.strokeWidth = 0
            }
        }

    }

    private fun handleButtonsAndUI( visibleButtonsAndShouldAdjustUi: Boolean ) {
        if(visibleButtonsAndShouldAdjustUi){
            (binding.clAddInsect.layoutParams as ViewGroup.MarginLayoutParams).topMargin = 32
            binding.mbRecords.isVisible = true
            binding.mbReports.isVisible = true
        }
    }

    private fun handleListRecords(recordList: List<RecordInsectGeolocationDomain>) {
        recordsAdapter.submitList( recordList )
    }

    private fun handleListReports(reportList: List<ReportInsectBySpeciesDomain>) {
        reportsAdapter.submitList(reportList)
    }

    private fun handleNavigation(firstTime: Boolean) {
        if(firstTime) findNavController().navigate(R.id.signUpFragment)
    }

    private fun handlePhotoUser(imageEntomologist: String) {

        if( imageEntomologist != EntomologistDomain.IMAGE_DEFAULT){

            Glide.with(requireContext())
                .load( File( imageEntomologist) )
                .circleCrop()
                .into( binding.layoutAvatarProfile.ivAvatarUser )

        }

    }

    private fun handleLoading(canShowLoading: Boolean) {
        showOrHideDialogLoading(canShowLoading)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}