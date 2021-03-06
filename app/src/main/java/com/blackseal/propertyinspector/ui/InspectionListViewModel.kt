package com.blackseal.propertyinspector.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.blackseal.propertyinspector.data.AppDatabase
import com.blackseal.propertyinspector.data.InspectionRepo

class InspectionListViewModel (application: Application): AndroidViewModel(application) {
    private val database = AppDatabase.getInstance(application)
    private val repo = InspectionRepo(database.inspectionListDao())

    fun loadList() = repo.loadList()

}
