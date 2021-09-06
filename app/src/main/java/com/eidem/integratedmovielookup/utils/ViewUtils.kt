package com.eidem.integratedmovielookup.utils

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

object ViewUtils {
    fun setHorizontalRecyclerView(context: Context, recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(context).apply {
            this.orientation = LinearLayoutManager.HORIZONTAL
        }
    }
}