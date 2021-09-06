package com.eidem.integratedmovielookup.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eidem.integratedmovielookup.R
import com.eidem.integratedmovielookup.constants.ApiConstant
import com.eidem.integratedmovielookup.constants.AppConstant
import com.eidem.integratedmovielookup.model.BoxOfficeModel

class BoxOfficeAdapter(private val list: List<BoxOfficeModel>): RecyclerView.Adapter<BoxOfficeAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.image)

        fun bind(url: String) {
            println("====> Bind $url")
            Glide.with(image).load(url).into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_box_office_view, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getBoxOfficePosterUrl(list[position].thumbUrl))
    }

    override fun getItemCount(): Int = list.size

    private fun getBoxOfficePosterUrl(url: String): String {
        return AppConstant.NETWORK_HTTPS + ApiConstant.KOBIS + url.replace("/thumb/","/")
            .replace("/thn_","/")
    }

    fun getItem(position: Int): BoxOfficeModel = list[position]
}