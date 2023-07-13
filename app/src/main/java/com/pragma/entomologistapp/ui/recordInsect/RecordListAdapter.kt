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

class RecordListAdapter( val tapItem: (record: RecordInsectGeolocationDomain) -> Unit ) : ListAdapter< RecordInsectGeolocationDomain, RecordListAdapter.RecordListViewHolder >(DiffCallback) {

    inner class RecordListViewHolder(
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
    ): RecordListViewHolder {
        val itemView = ItemListInsectBinding.inflate( LayoutInflater.from(parent.context), parent, false )
        return  RecordListViewHolder(itemView, parent.context)
    }

    override fun onBindViewHolder(holder: RecordListViewHolder, position: Int) {
        holder.apply {
            bindItem( getItem(position) )
            itemView.setOnClickListener {
                tapItem( getItem(position))
            }
        }
    }


}