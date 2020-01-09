package com.blackseal.propertyinspector.model

data class Inspection(
    val address: String,
    val photo: String,
    val isSold: Boolean,
    val houseShape: HouseShape,
    val decoration: Decoration,
    val houseLocation: HouseLocation

) {
    fun calculateRank(): Int {
        return 146 + houseLocation.calculateRank()
    }
}

enum class ROAD_CONDITION {
    MAIN_ROAD,
    NORMAL_ROAD,
    QUIET_ROAD,
    NO_THROUGH_ROAD
}

enum class COMMUTE {
    TRAIN,
    EXPRESS_BUS,
    BUS,
    DRIVE_ONLY
}

enum class SHOPPING {
    MALL,
    VILLAGE,
    BUS,
    DRIVE_ONLY
}

enum class CATCHMENT {
    WEAK,
    OK,
    GOOD,
    GREAT
}

data class HouseLocation(
    val roadCondition: ROAD_CONDITION,
    val commute: COMMUTE,
    val shopping: SHOPPING,
    val catchment: CATCHMENT,

    val hasOutOfBow: Boolean,
    val hasRoadOnFace: Boolean,
    val hasLowerThanRoad: Boolean,
    val hasElecTower: Boolean
) {
    fun calculateRank(): Int {
        return if (hasElecTower) -100 else 0 + if (hasLowerThanRoad) -20 else 0 + if (hasRoadOnFace) -3 else 0 + if (hasOutOfBow) -3 else 0 + when (roadCondition) {
            ROAD_CONDITION.MAIN_ROAD -> -10
            ROAD_CONDITION.NORMAL_ROAD -> -2
            ROAD_CONDITION.QUIET_ROAD -> 0
            ROAD_CONDITION.NO_THROUGH_ROAD -> 3
        } + when (commute) {
            COMMUTE.TRAIN -> 5
            COMMUTE.EXPRESS_BUS -> 4
            COMMUTE.BUS -> 0
            COMMUTE.DRIVE_ONLY -> -5
        } + when (shopping) {
            SHOPPING.MALL -> 10
            SHOPPING.VILLAGE -> 4
            SHOPPING.BUS -> 0
            SHOPPING.DRIVE_ONLY -> -5
        } + when (catchment) {
            CATCHMENT.WEAK -> -5
            CATCHMENT.OK -> 0
            CATCHMENT.GOOD -> 5
            CATCHMENT.GREAT -> 10
        }
    }
}

class Decoration()


class HouseShape() {

}
