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


}
