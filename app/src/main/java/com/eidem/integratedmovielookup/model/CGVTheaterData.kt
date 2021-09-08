package com.eidem.integratedmovielookup.model

data class CGVTheaterData(val theaterCd: String,
                          val theaterNm: String,
                          val areaCd: String,
                          var lat: Double? = null,
                          var lng: Double? = null)