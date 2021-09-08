package com.eidem.integratedmovielookup.data

import com.eidem.integratedmovielookup.model.CGVTheaterData

class TheaterData private constructor() {
    companion object {
        private val instance = TheaterData()
        fun getInstance(): TheaterData {
            return instance
        }
    }

    private val cgvTheaterDataList = arrayListOf<CGVTheaterData>()

    fun addCGVTheaterData(data: CGVTheaterData) {
        cgvTheaterDataList.add(data)
    }
}