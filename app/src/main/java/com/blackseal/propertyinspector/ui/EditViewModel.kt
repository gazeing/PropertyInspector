package com.blackseal.propertyinspector.ui

import android.app.Application
import androidx.lifecycle.*
import com.blackseal.propertyinspector.data.AppDatabase
import com.blackseal.propertyinspector.data.InspectionRepo
import com.blackseal.propertyinspector.model.Inspection
import io.reactivex.Flowable
import kotlinx.coroutines.launch

class EditViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getInstance(application)
    private val repo = InspectionRepo(database.inspectionListDao())
    fun saveInspection(inspection: Inspection) {
        viewModelScope.launch {
            repo.addInspection(inspection)
        }
    }

    val idLiveData = MutableLiveData<String>()

    val inspectionLiveData: LiveData<Inspection> = idLiveData.switchMap { id ->
        repo.loadInspection(id)
    }


    fun getInspection(id: String) {
        idLiveData.value = id
    }

}


fun <T> LiveData<T>.toFlowable(owner: LifecycleOwner): Flowable<T> =
    Flowable.fromPublisher(LiveDataReactiveStreams.toPublisher(owner, this))

fun <T> Flowable<T>.toLiveData(): LiveData<T> = LiveDataReactiveStreams.fromPublisher(this)

fun <T, R> LiveData<T>.switchMap(f: (T) -> LiveData<R>): LiveData<R> =
    Transformations.switchMap(this, f)