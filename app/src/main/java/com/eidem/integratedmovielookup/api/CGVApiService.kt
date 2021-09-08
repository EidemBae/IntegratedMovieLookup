package com.eidem.integratedmovielookup.api

import com.eidem.integratedmovielookup.constants.ApiConstant
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CGVApiService {
    @GET(ApiConstant.CGV_THEATER_LIST)
    fun requestTheaterList(): Call<String>

    @GET(ApiConstant.CGV_THEATER_LOCATION)
    fun requestTheaterLocation(@Query("tc") theaterCd: String, @Query("rc") areaCd: String): Call<String>
}