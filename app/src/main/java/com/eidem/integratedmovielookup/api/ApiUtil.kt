package com.eidem.integratedmovielookup.api

import com.eidem.integratedmovielookup.constants.ApiConstant
import com.eidem.integratedmovielookup.constants.AppConstant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiUtil {
    fun getKobisApiService(): KobisApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(AppConstant.NETWORK_HTTPS + ApiConstant.KOBIS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(KobisApiService::class.java)
    }
}