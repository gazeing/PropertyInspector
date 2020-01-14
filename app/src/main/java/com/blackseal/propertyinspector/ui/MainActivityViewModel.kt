package com.blackseal.propertyinspector.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blackseal.propertyinspector.data.AppDatabase
import com.blackseal.propertyinspector.data.InspectionRepo
import com.blackseal.propertyinspector.model.Inspection

class MainActivityViewModel(application: Application):AndroidViewModel(application) {
    private val database = AppDatabase.getInstance(application)
    private val repo = InspectionRepo(database.inspectionListDao())

    val idLiveData = MutableLiveData<String>()

    val inspectionListLiveData: LiveData<List<Inspection>> = idLiveData.switchMap { id ->
        repo.loadList()
    }

    fun export(email:String){
        idLiveData.value = email
    }

}
