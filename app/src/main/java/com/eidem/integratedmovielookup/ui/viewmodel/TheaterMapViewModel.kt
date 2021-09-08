package com.eidem.integratedmovielookup.ui.viewmodel

import androidx.appcompat.app.AppCompatActivity
import com.eidem.integratedmovielookup.data.TheaterData
import com.eidem.integratedmovielookup.databinding.ActivityTheaterMapBinding
import com.google.gson.Gson
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class TheaterMapViewModel(private val activity: AppCompatActivity, private val binding: ActivityTheaterMapBinding) {

    private val mapView = MapView(activity)

    init {
        initMap()
        addCGVPoI()
    }

    private fun initMap() {
        binding.mapView.addView(mapView)
    }

    private fun addCGVPoI() {
        TheaterData.getInstance().getCGVDataList().forEach {
            val marker = MapPOIItem()
            marker.itemName = it.theaterNm
            marker.tag = 1
            marker.isShowCalloutBalloonOnTouch = false
            if (it.lat != null && it.lng != null)
                marker.mapPoint = MapPoint.mapPointWithGeoCoord(it.lat!!, it.lng!!)
            marker.markerType = MapPOIItem.MarkerType.CustomImage

//                    marker.customImageResourceId = R.drawable.marker_bicycle
//            marker.customImageBitmap = createCustomMarkerView(markerInfo.percent)
            marker.markerType = MapPOIItem.MarkerType.BluePin
            marker.isCustomImageAutoscale = false
            marker.setCustomImageAnchor(0.5f, 1.0f)
            mapView.addPOIItem(marker)
        }
    }
}