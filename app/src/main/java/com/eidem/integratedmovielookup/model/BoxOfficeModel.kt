package com.eidem.integratedmovielookup.model

data class BoxOfficeModel(
    val movieCd: String,
    val thumbUrl: String,
    val movieNm: String,
    val movieNmEn: String,
    val synop: String,
    val showTm: String,
    val director: String,
    val dtNm: String,
    val repNationCd: String,
    val genre: String,
    val watchGradeNm: String
)