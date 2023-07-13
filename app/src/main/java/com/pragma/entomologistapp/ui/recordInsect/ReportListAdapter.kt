package com.pragma.entomologistapp.ui.recordInsect


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pragma.entomologistapp.databinding.ItemListInsectReportsBinding
import com.pragma.entomologistapp.domain.model.ReportInsectBySpeciesDomain

class ReportListAdapter( val tapItem: (report: ReportInsectBySpeciesDomain) -> Unit ): ListAdapter<ReportInsectBySpeciesDomain, ReportListAdapter.ReportListAdapterViewHolder>(DiffCallback){

    inner class ReportListAdapterViewHolder(
        private val binding: ItemListInsectReportsBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root){
        fun bindItem(report: ReportInsectBySpeciesDomain){
            with(binding){
                mtvCountInsect.text = report.countInsectBySpecie.toString()
                mtvHeaderInsect.text= report.nameInsect
                Glide.with(context)
                    .load( report.photoInsect )
                    .circleCrop()
                    .into( ivInsect )
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ReportInsectBySpeciesDomain>() {
        override fun areItemsTheSame(
            oldItem: ReportInsectBySpeciesDomain,
            newItem: ReportInsectBySpeciesDomain
        ): Boolean {
            return oldItem.nameInsect == newItem.nameInsect
        }

        override fun areContentsTheSame(
            oldItem: ReportInsectBySpeciesDomain,
            newItem: ReportInsectBySpeciesDomain
        ): Boolean {
            return oldItem.nameInsect == newItem.nameInsect
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportListAdapterViewHolder {
        val itemView: ItemListInsectReportsBinding = ItemListInsectReportsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ReportListAdapterViewHolder(itemView, parent.context)
    }

    override fun onBindViewHolder(holder: ReportListAdapterViewHolder, position: Int) {
        holder.apply {
            bindItem( getItem(position) )
            itemView.setOnClickListener {
                tapItem( getItem(position) )
            }
        }
    }

}