package com.eidem.integratedmovielookup.splash

import android.graphics.PointF
import com.eidem.integratedmovielookup.api.ApiUtil
import com.eidem.integratedmovielookup.data.TheaterData
import com.eidem.integratedmovielookup.model.CGVTheaterData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TheaterInfoLoader {
    fun requestCGVTheaterData() {
        ApiUtil.getCGVTicketApiService()
            .requestTheaterList()
            .enqueue(object: Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    parseTheaterInfo(response.body().toString())
                }

                override fun onFailure(call: Call<String>, t: Throwable) {}

            })
    }

    private fun requestCGVTheaterLocation(data: CGVTheaterData, success: (Float, Float) -> Unit) {
        ApiUtil.getCGVTicketApiService()
            .requestTheaterLocation(data.theaterCd, data.areaCd)
            .enqueue(object: Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val body = response.body().toString()
                    val location = parseLocation(body)
                    success.invoke(location.x, location.y)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {

                }

                private fun parseLocation(str: String): PointF {
                    val tag_lat = "lat=\" + \""
                    var startIdx = str.indexOf(tag_lat)
                    val location = PointF()
                    if (startIdx > -1) {
                        startIdx += tag_lat.length
                        val lat = str.substring(startIdx, str.indexOf("\"", startIdx))
                        location.x = lat.toFloat()
                    }
                    val tag_lng = "lng=\" + \""
                    startIdx = str.indexOf(tag_lng)
                    if (startIdx > -1) {
                        startIdx += tag_lng.length
                        val lng = str.substring(startIdx, str.indexOf("\"", startIdx))
                        location.y = lng.toFloat()
                    }
                    return location
                }
            })
    }

    private fun parseTheaterInfo(source: String) {
        var startIdx = source.indexOf("<li data-gubun='AREA'", 0)
        while (startIdx > -1) {
            val endIdx = source.indexOf(">", startIdx+1)
            val str = source.substring(startIdx, endIdx)
            parseDataModel(str)?.let {
                requestCGVTheaterLocation(it) { lat, lng ->
                    it.lat = lat
                    it.lng = lng
                    TheaterData.getInstance().addCGVTheaterData(it)
                    println("===> ${it.theaterNm} ${it.lat}, ${it.lng}")
                }
            }
            startIdx = source.indexOf("<li data-gubun='AREA'", endIdx)
        }
    }

    private fun parseDataModel(str: String): CGVTheaterData? {
        val areaCd = parseTag("data-area", str)
        val theaterCd = parseTag("data-cd", str)
        val theaterNm = parseTag("data-name", str)

        return if (areaCd != null && theaterCd != null && theaterNm != null)
            CGVTheaterData(theaterCd, theaterNm, areaCd)
        else
            null
    }

    private fun parseTag(tag: String, source: String): String? {
        val startIdx = source.indexOf(tag).run {
            if (this > -1) this + tag.length + 2 else this
        }
        if (startIdx == -1) return null
        val endIdx = source.indexOf("'", startIdx + 2)
        return if (startIdx > -1 && endIdx > -1)
            source.substring(startIdx, endIdx)
        else
            null
    }
}