package com.blackseal.propertyinspector.model

data class Inspection(
    val address: String,
    val photo: String,
    val isSold: Boolean,
    val houseShape: HouseShape,
    val decoration: Decoration,
    val houseLocation: HouseLocation

)

class HouseLocation {

}

class Decoration {

}

class HouseShape {

}
