package com.blackseal.propertyinspector.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blackseal.propertyinspector.data.AppDatabase
import com.blackseal.propertyinspector.data.InspectionRepo
import com.blackseal.propertyinspector.model.Inspection
import kotlinx.coroutines.launch

class EditViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getInstance(application)
    private val repo = InspectionRepo(database.inspectionListDao())
    fun saveInspection(inspection: Inspection) {
        viewModelScope.launch {
            repo.addInspection(inspection)
        }
    }

}
