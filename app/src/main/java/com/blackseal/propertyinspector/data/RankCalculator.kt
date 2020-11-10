package com.blackseal.propertyinspector.data

import com.blackseal.propertyinspector.model.*

class RankCalculator(val prefs: Prefs, val inspection: Inspection) {

    fun calculateRank(): Int {
        return (prefs.getBaseRankPref() +
                calculateSuburbPreference() +
                calculateDecorationRank() +
                calculateHouseLocationRank() +
                calculateHouseShapeRank()).toInt()
    }

    private fun calculateSuburbPreference() = when {
        inspection.address.contains("castle hill", true) -> 10
        inspection.address.contains("cherrybrook", true) -> 10
        inspection.address.contains("pennant hill", true) -> 15
        inspection.address.contains("baulkham hill", true) -> 5
        inspection.address.contains("kellyville", true) -> 0
        inspection.address.contains("thornleigh", true) -> 5
        else -> 0
    } * prefs.getSuburbPrefRate()

    fun calculateHouseLocationRank(): Float {
        return (if (inspection.houseLocation.hasElecTower) -100 else 0 +
                if (inspection.houseLocation.hasLowerThanRoad) -20 else 0 +
                        if (inspection.houseLocation.hasRoadOnFace) -3 else 0 +
                                if (inspection.houseLocation.hasOutOfBow) -3 else 0 +
                                        when (inspection.houseLocation.roadCondition) {
                                            ROAD_CONDITION.MAIN_ROAD -> -10
                                            ROAD_CONDITION.NORMAL_ROAD -> -2
                                            ROAD_CONDITION.QUIET_ROAD -> 0
                                            ROAD_CONDITION.NO_THROUGH_ROAD -> 3
                                        } + when (inspection.houseLocation.commute) {
                                    COMMUTE.TRAIN -> 5
                                    COMMUTE.EXPRESS_BUS -> 4
                                    COMMUTE.BUS -> 0
                                    COMMUTE.DRIVE_ONLY -> -5
                                } + when (inspection.houseLocation.shopping) {
                                    SHOPPING.MALL -> 10
                                    SHOPPING.VILLAGE -> 4
                                    SHOPPING.BUS -> 0
                                    SHOPPING.DRIVE_ONLY -> -5
                                } + when (inspection.houseLocation.catchment) {
                                    CATCHMENT.WEAK -> -5
                                    CATCHMENT.OK -> 0
                                    CATCHMENT.GOOD -> 5
                                    CATCHMENT.GREAT -> 10
                                }) * prefs.getLocationPrefRate()
    }

    fun calculateDecorationRank(): Float {
        return (if (inspection.decoration.hasGasSupply) 0 else -5 +
                if (inspection.decoration.hasAirCon) 2 else 0 +
                        if (inspection.decoration.hasFloorWarmer) 2 else 0 +
                                if (inspection.decoration.hasFirePlace) 1 else 0 +
                                        if (inspection.decoration.hasTimberFloor) 2 else 1 +
                                                if (inspection.decoration.hasAlfresco) 1 else 0 +
                                                        when (inspection.decoration.kitchenDecoration) {
                                                            DECORATION_CONDITION.BROKEN -> -4
                                                            DECORATION_CONDITION.OLD -> -2
                                                            DECORATION_CONDITION.USABLE -> 0
                                                            DECORATION_CONDITION.NEW -> 2
                                                        } + when (inspection.decoration.backYardSize) {
                                                    BACK_YARD_SIZE.ABSENT -> -5
                                                    BACK_YARD_SIZE.SMALL -> -2
                                                    BACK_YARD_SIZE.MIDIUM -> 0
                                                    BACK_YARD_SIZE.LARGE -> 5
                                                } + when (inspection.decoration.backYardCondition) {
                                                    BACK_YARD_CONDITION.STEP -> -1
                                                    BACK_YARD_CONDITION.SLOPE -> -3
                                                    BACK_YARD_CONDITION.FLAT -> 0
                                                    BACK_YARD_CONDITION.BUSHES -> -5
                                                } + when (inspection.decoration.swimmingPoolCondition) {
                                                    SWIMMING_POOL_CONDITION.ABSENT -> 0
                                                    SWIMMING_POOL_CONDITION.BROKEN -> -3
                                                    SWIMMING_POOL_CONDITION.USABLE -> 0
                                                    SWIMMING_POOL_CONDITION.NEW -> 2
                                                }) * prefs.getDecorationPrefRate()

    }

    fun calculateHouseShapeRank(): Float {
        return (inspection.houseShape.landSize / 20 + when (inspection.houseShape.landShape) {
            LAND_SHAPE.SQUARE -> 0
            LAND_SHAPE.TRIANGLE -> -5
            LAND_SHAPE.AXE -> -5
            LAND_SHAPE.LADDER -> -3
        } + ((inspection.houseShape.masterRoomLength * inspection.houseShape.masterRoomWidth) / 2
                + (inspection.houseShape.kitchenLength * inspection.houseShape.kitchenWidth) / 2
                + inspection.houseShape.calculateLivingAreaSize() / 3).toInt() + (inspection.houseShape.bedroomNumber - 4) * 7 + when (inspection.houseShape.masterRoomBathRoom) {
            DECORATION_CONDITION.BROKEN -> -3
            DECORATION_CONDITION.OLD -> -2
            DECORATION_CONDITION.USABLE -> 0
            DECORATION_CONDITION.NEW -> 1
        } + when (inspection.houseShape.masterRoomWardrobe) {
            DECORATION_CONDITION.BROKEN -> -3
            DECORATION_CONDITION.OLD -> -1
            DECORATION_CONDITION.USABLE -> 0
            DECORATION_CONDITION.NEW -> 1
        } + if (inspection.houseShape.isMasterRoomFacingNorthWest) 0 else -2 + when (inspection.houseShape.rampusSpace) {
            RAMPUS_SPACE.ABSENT -> -2
            RAMPUS_SPACE.WITHOUT_DOOR -> 0
            RAMPUS_SPACE.WITH_DOOR -> 2
        } + when (inspection.houseShape.inLawSpace) {
            IN_LAW_SPACE.ABSENT -> 0
            IN_LAW_SPACE.ATTACHED -> 10
            IN_LAW_SPACE.SEPARATED -> 15
        } + (inspection.houseShape.coverdCarSpace - 1) * 5 + inspection.houseShape.driveWayCarSpace * 2) * prefs.getShapePrefRate()
    }


}

fun HouseShape.calculateLivingAreaSize(): Float {
    return (rampusLength * rampusWidth) + (dinningRoomLength * dinningRoomWidth) + (familyRoomLength * familyRoomWidth)
}