package com.pragma.entomologistapp.ui.recordInsect

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pragma.entomologistapp.databinding.ItemListInsectBinding
import com.pragma.entomologistapp.domain.model.RecordInsectGeolocationDomain

class RecordListAdapter( tapItem: () -> Unit ) : ListAdapter< RecordInsectGeolocationDomain, RecordListAdapter.RecordInsectGeolocationDomainViewHolder >(DiffCallback) {

    inner class RecordInsectGeolocationDomainViewHolder(
        private val binding: ItemListInsectBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root){

        fun bindItem( record: RecordInsectGeolocationDomain ){
            with(binding){
                mtvCountInsect.text = record.countInsect.toString()
                mtvHeaderInsect.text = record.nameInsect
                mtvSubHeaderInsect.text = String.format("%s %s", record.cityName, record.dateRecord )
                Glide.with(context)
                    .load( record.photoInsect )
                    .centerCrop()
                    .into( ivAvatarInsect )
            }
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<RecordInsectGeolocationDomain>() {
        override fun areItemsTheSame(
            oldItem: RecordInsectGeolocationDomain,
            newItem: RecordInsectGeolocationDomain
        ): Boolean {
            return oldItem.idRecord == newItem.idRecord
        }

        override fun areContentsTheSame(
            oldItem: RecordInsectGeolocationDomain,
            newItem: RecordInsectGeolocationDomain
        ): Boolean {
            return oldItem.idRecord == newItem.idRecord
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecordInsectGeolocationDomainViewHolder {
        val itemView = ItemListInsectBinding.inflate( LayoutInflater.from(parent.context), parent, false )
        return  RecordInsectGeolocationDomainViewHolder(itemView, parent.context)
    }

    override fun onBindViewHolder(holder: RecordInsectGeolocationDomainViewHolder, position: Int) {
        holder.bindItem(
            getItem(position)
        )
    }


}