package com.eidem.integratedmovielookup.api

import com.eidem.integratedmovielookup.constants.ApiConstant
import com.eidem.integratedmovielookup.model.BoxOfficeModel
import retrofit2.Call
import retrofit2.http.GET

interface KobisApiService {
    @GET(ApiConstant.KOBIS_BOX_OFFICE)
    fun requestBoxOfficeList(): Call<List<BoxOfficeModel>>
}