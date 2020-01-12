package com.blackseal.propertyinspector.data

import com.blackseal.propertyinspector.model.Inspection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InspectionRepo(val dao: InspectionListDAO) {
    suspend fun addInspection(inspection: Inspection) =
        withContext(Dispatchers.IO) { dao.insertInspection(inspection) }

    fun loadList() = dao.getInspectionList()


    fun loadInspection(id:String) = dao.getInspectionById(id)
}