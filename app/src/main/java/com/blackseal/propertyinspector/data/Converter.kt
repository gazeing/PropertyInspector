package com.blackseal.propertyinspector.data

import androidx.room.TypeConverter
import com.blackseal.propertyinspector.model.*

class Converter {
    @TypeConverter fun roadConditionToTnt(value: ROAD_CONDITION) = value.toInt()
    @TypeConverter
    fun intToRoadCondition(value: Int) = value.toEnum<ROAD_CONDITION>()

    @TypeConverter fun myEnumToTnt(value: COMMUTE) = value.toInt()
    @TypeConverter
    fun intToMyEnum(value: Int) = value.toEnum<COMMUTE>()

    @TypeConverter fun myEnumToTnt1(value: SHOPPING) = value.toInt()
    @TypeConverter
    fun intToMyEnum1(value: Int) = value.toEnum<SHOPPING>()


    @TypeConverter fun myEnumToTnt2(value: CATCHMENT) = value.toInt()
    @TypeConverter
    fun intToMyEnum2(value: Int) = value.toEnum<CATCHMENT>()


    @TypeConverter fun myEnumToTnt3(value: DECORATION_CONDITION) = value.toInt()
    @TypeConverter
    fun intToMyEnum3(value: Int) = value.toEnum<DECORATION_CONDITION>()


    @TypeConverter fun myEnumToTnt4(value: BACK_YARD_SIZE) = value.toInt()
    @TypeConverter
    fun intToMyEnum4(value: Int) = value.toEnum<BACK_YARD_SIZE>()


    @TypeConverter fun myEnumToTnt5(value: BACK_YARD_CONDITION) = value.toInt()
    @TypeConverter
    fun intToMyEnum5(value: Int) = value.toEnum<BACK_YARD_CONDITION>()


    @TypeConverter fun myEnumToTnt6(value: SWIMMING_POOL_CONDITION) = value.toInt()
    @TypeConverter
    fun intToMyEnum6(value: Int) = value.toEnum<SWIMMING_POOL_CONDITION>()


    @TypeConverter fun myEnumToTnt7(value: LAND_SHAPE) = value.toInt()
    @TypeConverter
    fun intToMyEnum7(value: Int) = value.toEnum<LAND_SHAPE>()


    @TypeConverter fun myEnumToTnt8(value: RAMPUS_SPACE) = value.toInt()
    @TypeConverter
    fun intToMyEnum8(value: Int) = value.toEnum<RAMPUS_SPACE>()


    @TypeConverter fun myEnumToTnt9(value: IN_LAW_SPACE) = value.toInt()
    @TypeConverter
    fun intToMyEnum9(value: Int) = value.toEnum<IN_LAW_SPACE>()
}

@Suppress("NOTHING_TO_INLINE")
private inline fun <T : Enum<T>> T.toInt(): Int = this.ordinal

private inline fun <reified T : Enum<T>> Int.toEnum(): T = enumValues<T>()[this]