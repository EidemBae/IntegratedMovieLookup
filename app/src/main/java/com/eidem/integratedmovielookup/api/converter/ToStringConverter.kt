package com.eidem.integratedmovielookup.api.converter

import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class ToStringConverter: Converter.Factory() {

    val MEDIA_TYPE = MediaType.parse("text/plain")

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        if (String.javaClass == type) {
            Converter<ResponseBody, String> {
                it.string()
            }
        }
        return null
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        if (String.javaClass == type) {
            return Converter<String, RequestBody> {
                RequestBody.create(MEDIA_TYPE, it)
            }
        }
        return null
    }
}