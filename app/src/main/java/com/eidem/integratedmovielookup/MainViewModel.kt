package com.eidem.integratedmovielookup

import android.app.Activity
import com.bumptech.glide.Glide
import com.eidem.integratedmovielookup.adapter.BoxOfficeAdapter
import com.eidem.integratedmovielookup.api.ApiUtil
import com.eidem.integratedmovielookup.constants.ApiConstant
import com.eidem.integratedmovielookup.constants.AppConstant
import com.eidem.integratedmovielookup.databinding.ActivityMainBinding
import com.eidem.integratedmovielookup.model.BoxOfficeModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(activity: Activity, val binding: ActivityMainBinding) {
    private var boxOfficeModelList: List<BoxOfficeModel>? = null

    init {

    }

    private fun getBoxOfficePosterUrl(url: String): String {
        return url.replace("/thumb/","/")
            .replace("/thn_","/")
    }

    fun requestBoxOfficeList() {
        ApiUtil.getKobisApiService()
            .requestBoxOfficeList()
            .enqueue(object: Callback<List<BoxOfficeModel>> {
                override fun onResponse(
                    call: Call<List<BoxOfficeModel>>,
                    response: Response<List<BoxOfficeModel>>
                ) {
                    boxOfficeModelList = response.body()

                    boxOfficeModelList?.let {
                        val url = AppConstant.NETWORK_HTTPS + ApiConstant.KOBIS + getBoxOfficePosterUrl(it[0].thumbUrl)
                        println("===> $url")
//                        Glide.with(binding.image).load(url).into(binding.image)
                        setBoxOffice(it)
                        for (i in it.indices) {
                            println("====> ${it[i].movieNm}")
                        }
                    }
                }

                override fun onFailure(call: Call<List<BoxOfficeModel>>, t: Throwable) {
                }
            })
    }

    private fun setBoxOffice(itemList: List<BoxOfficeModel>) {
        println("======> BoxOffice ${itemList.size}")
        binding.recyclerView.adapter = BoxOfficeAdapter(itemList)
        binding.recyclerView.set3DItem(true)
        binding.recyclerView.setAlpha(true)

    }
}