package com.blackseal.propertyinspector.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Inspection")
data class Inspection(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    val address: String,
    val photo: String,
    val isSold: Boolean,
    @Embedded
    val houseShape: HouseShape,
    @Embedded
    val decoration: Decoration,
    @Embedded
    val houseLocation: HouseLocation

) {
    fun calculateRank(): Int {
        return 108 + (houseLocation.calculateRank() + decoration.calculateRank() + houseShape.calculateRank() + calculateSuburbPreference())*23/43
    }

    private fun calculateSuburbPreference() = when {
        address.contains("castle hill", true) -> 10
        address.contains("cherrybrook", true) -> 10
        address.contains("pennant hill", true) -> 15
        address.contains("baulkham hill", true) -> 5
        else -> 0
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

enum class DECORATION_CONDITION {
    BROKEN,
    OLD,
    USABLE,
    NEW
}

enum class BACK_YARD_SIZE {
    ABSENT,
    SMALL,
    MIDIUM,
    LARGE
}

enum class BACK_YARD_CONDITION {
    STEP,
    SLOPE,
    BUSHES,
    FLAT
}

enum class SWIMMING_POOL_CONDITION {
    ABSENT,
    BROKEN,
    USABLE,
    NEW
}

class Decoration(
    val hasGasSupply: Boolean,
    val hasAirCon: Boolean,
    val hasFloorWarmer: Boolean,
    val hasFirePlace: Boolean,
    val hasTimberFloor: Boolean,
    val hasAlfresco: Boolean,
    val kitchenDecoration: DECORATION_CONDITION,
    val backYardSize: BACK_YARD_SIZE,
    val backYardCondition: BACK_YARD_CONDITION,
    val swimmingPoolCondition: SWIMMING_POOL_CONDITION
) {
    fun calculateRank(): Int {
        return if (hasGasSupply) 0 else -5 + if (hasAirCon) 2 else 0 + if (hasFloorWarmer) 2 else 0 + if (hasFirePlace) 1 else 0 + if (hasTimberFloor) 2 else 1 + if (hasAlfresco) 1 else 0 +
                when (kitchenDecoration) {
                    DECORATION_CONDITION.BROKEN -> -4
                    DECORATION_CONDITION.OLD -> -2
                    DECORATION_CONDITION.USABLE -> 0
                    DECORATION_CONDITION.NEW -> 2
                } + when (backYardSize) {
            BACK_YARD_SIZE.ABSENT -> -5
            BACK_YARD_SIZE.SMALL -> -2
            BACK_YARD_SIZE.MIDIUM -> 0
            BACK_YARD_SIZE.LARGE -> 5
        } + when (backYardCondition) {
            BACK_YARD_CONDITION.STEP -> -1
            BACK_YARD_CONDITION.SLOPE -> -3
            BACK_YARD_CONDITION.FLAT -> 0
            BACK_YARD_CONDITION.BUSHES -> -5
        } + when (swimmingPoolCondition) {
            SWIMMING_POOL_CONDITION.ABSENT -> 0
            SWIMMING_POOL_CONDITION.BROKEN -> -3
            SWIMMING_POOL_CONDITION.USABLE -> 0
            SWIMMING_POOL_CONDITION.NEW -> 2
        }
    }
}

enum class LAND_SHAPE {
    SQUARE,
    TRIANGLE,
    AXE,
    LADDER,
}

enum class RAMPUS_SPACE {
    ABSENT,
    WITHOUT_DOOR,
    WITH_DOOR
}

enum class IN_LAW_SPACE {
    ABSENT,
    ATTACHED,
    SEPARATED
}


class HouseShape(
    val landSize: Int,
    val landShape: LAND_SHAPE,
    val bedroomNumber: Int,
    val masterRoomLength: Float,
    val masterRoomWidth: Float,
    val masterRoomBathRoom: DECORATION_CONDITION,
    val masterRoomWardrobe: DECORATION_CONDITION,
    val isMasterRoomFacingNorthWest: Boolean,
    val rampusSpace: RAMPUS_SPACE,
    val inLawSpace: IN_LAW_SPACE,
    val kitchenLength: Float,
    val kitchenWidth: Float,
    val rampusLength: Float,
    val rampusWidth: Float,
    val familyRoomLength: Float,
    val familyRoomWidth: Float,
    val dinningRoomLength: Float,
    val dinningRoomWidth: Float,
    val coverdCarSpace: Int,
    val driveWayCarSpace: Int
) {

    fun calculateRank(): Int {
        return landSize / 20 + when (landShape) {
            LAND_SHAPE.SQUARE -> 0
            LAND_SHAPE.TRIANGLE -> -5
            LAND_SHAPE.AXE -> -5
            LAND_SHAPE.LADDER -> -3
        } + ((masterRoomLength * masterRoomWidth) / 2
                + (kitchenLength * kitchenWidth) / 2
                + calculateLivingAreaSize()/3).toInt() + (bedroomNumber - 4) * 7 + when (masterRoomBathRoom) {
            DECORATION_CONDITION.BROKEN -> -3
            DECORATION_CONDITION.OLD -> -2
            DECORATION_CONDITION.USABLE -> 0
            DECORATION_CONDITION.NEW -> 1
        } + when (masterRoomWardrobe) {
            DECORATION_CONDITION.BROKEN -> -3
            DECORATION_CONDITION.OLD -> -1
            DECORATION_CONDITION.USABLE -> 0
            DECORATION_CONDITION.NEW -> 1
        } + if (isMasterRoomFacingNorthWest) 0 else -2 + when (rampusSpace) {
            RAMPUS_SPACE.ABSENT -> -2
            RAMPUS_SPACE.WITHOUT_DOOR -> 0
            RAMPUS_SPACE.WITH_DOOR -> 2
        } + when (inLawSpace) {
            IN_LAW_SPACE.ABSENT -> 0
            IN_LAW_SPACE.ATTACHED -> 10
            IN_LAW_SPACE.SEPARATED -> 15
        } + (coverdCarSpace - 1) * 5 + driveWayCarSpace * 2

    }

    fun calculateLivingAreaSize(): Float {
        return (rampusLength * rampusWidth) + (dinningRoomLength * dinningRoomWidth) + (familyRoomLength * familyRoomWidth)
    }
}
