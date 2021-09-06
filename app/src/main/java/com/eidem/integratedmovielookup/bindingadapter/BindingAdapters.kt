package com.eidem.integratedmovielookup.bindingadapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("setTextColor")
    fun setTextColor(view: TextView, @ColorInt color: Int) {
        view.setTextColor(color)
    }

    @JvmStatic
    @SuppressLint("ResourceAsColor")
    @BindingAdapter("setBackground")
    fun setBackground(view: View, resId: Int) {
        when (resId) {
            0 -> view.setBackgroundColor(android.R.color.transparent)
            else -> view.setBackgroundResource(resId)
        }
    }

//    @SuppressLint("NotifyDataSetChanged")
//    @JvmStatic
//    @BindingAdapter("items")
//    fun setBindItem(view: RecyclerView, items: MutableLiveData<ArrayList<DayOfWeekAdapter.ViewHolderData>>) {
//        view.adapter?.run {
//            if (this is DayOfWeekAdapter) {
//                this.dataList = items.value!!
//                this.notifyDataSetChanged()
//            }
//        }
//    }
}