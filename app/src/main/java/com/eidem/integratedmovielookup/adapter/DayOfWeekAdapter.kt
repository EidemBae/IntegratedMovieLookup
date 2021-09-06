package com.eidem.integratedmovielookup.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.eidem.integratedmovielookup.databinding.ItemDayOfWeekLayoutBinding

class DayOfWeekAdapter(private val dataListLiveData: MutableLiveData<ArrayList<ViewHolderData>>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var _selectedItemPosition = 0
    private var itemSelectedListener: OnItemSelectedListener? = null

    private fun setSelectedPosition(position: Int) {
        val dataList = dataListLiveData.value
        dataList?.get(_selectedItemPosition)?.selected = false
        _selectedItemPosition = position
        dataList?.get(_selectedItemPosition)?.selected = true

        dataListLiveData.postValue(dataList)

        itemSelectedListener?.onSelected(_selectedItemPosition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemDayOfWeekLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.root.layoutParams.width = parent.measuredWidth / itemCount

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with (holder as ViewHolder) {
            dataListLiveData.value?.get(position)?.let {
                this.bindData(it, position)
            }
            this.binding.root.setOnClickListener {
                setSelectedPosition(position)
            }
        }
    }

    override fun getItemCount(): Int = dataListLiveData.value?.size ?: 0

    private inner class ViewHolder(val binding: ItemDayOfWeekLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindData(data: ViewHolderData, position: Int) {
            binding.data = data
            binding.position = position
        }
    }

    fun updateList(itemList: ArrayList<ViewHolderData>) {
        this.dataListLiveData.value?.let {
            val diffCallback = DiffCallback(itemList, it)
            val diffResult = DiffUtil.calculateDiff(diffCallback)

            itemList.clear()
            for (i in it.indices) {
                itemList.add(it[i].clone())
            }

            diffResult.dispatchUpdatesTo(this)
        }
    }

    class ViewHolderData(val num: Int, val str: String, var selected: Boolean = false): Cloneable  {
        public override fun clone(): ViewHolderData {
            return ViewHolderData(num, str, selected)
        }
    }

    private class DiffCallback(oldList: List<ViewHolderData>, newList: List<ViewHolderData>): DiffUtil.Callback() {
        private val oldList: List<ViewHolderData> = oldList
        private val newList: List<ViewHolderData> = newList

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem.num == newItem.num && oldItem.str == newItem.str && oldItem.selected == newItem.selected
        }
    }

    fun setOnItemSelectedListener(listener: OnItemSelectedListener) {
        this.itemSelectedListener = listener
    }

    interface OnItemSelectedListener {
        fun onSelected(position: Int)
    }
}