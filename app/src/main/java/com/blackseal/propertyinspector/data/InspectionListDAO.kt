package com.blackseal.propertyinspector.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.blackseal.propertyinspector.model.Inspection

@Dao
interface InspectionListDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Inspection>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInspection(inspection: Inspection)

    @Query("SELECT * FROM Inspection ORDER BY id DESC")
    fun getInspectionList(): LiveData<List<Inspection>>

}