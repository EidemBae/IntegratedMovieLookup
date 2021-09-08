package com.eidem.integratedmovielookup.api

import com.eidem.integratedmovielookup.api.converter.ToStringConverter
import com.eidem.integratedmovielookup.constants.ApiConstant
import com.eidem.integratedmovielookup.constants.AppConstant
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object ApiUtil {
    fun getKobisApiService(): KobisApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(AppConstant.NETWORK_HTTPS + ApiConstant.KOBIS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(KobisApiService::class.java)
    }

    fun getCGVTicketApiService(): CGVApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(AppConstant.NETWORK_HTTPS + ApiConstant.CGV_MOBILE)
            .client(provideOkHttpClient(AppInterceptor()))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(CGVApiService::class.java)
    }

    private fun provideOkHttpClient(interceptor: AppInterceptor): OkHttpClient = OkHttpClient.Builder().run {
        addInterceptor(interceptor)
        build()
    }

    private class AppInterceptor: Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {

            val builder = chain.request().newBuilder()
            val response = chain.proceed(builder.build())

            println("====> Request RUL : ${builder.build().url()}")

            return response
        }

    }
}