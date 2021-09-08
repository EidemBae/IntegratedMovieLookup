package com.eidem.integratedmovielookup.data

class LocationD {
    var lat: Double = 0.0
    var lng: Double = 0.0

    constructor()

    constructor(lat: Double, lng: Double) {
        this.lat = lat
        this.lng = lng
    }
}