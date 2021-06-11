package id.ac.unhas.roommvvmcrudapp

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ac.unhas.roommvvmcrudapp.db.Teman
import id.ac.unhas.roommvvmcrudapp.db.TemanRepository
import kotlinx.coroutines.launch

class TemanViewModel(private val repository: TemanRepository) : ViewModel(),Observable {

    val teman = repository.teman
    private var isUpdateOrDelete = false
    private lateinit var temanToUpdateOrDelete : Teman

    @Bindable
    val inputName = MutableLiveData<String>()
    @Bindable
    val inputEmail = MutableLiveData<String>()
    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()
    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()

    val messagge : LiveData<Event<String>>
        get() = statusMessage

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate() {
        if(isUpdateOrDelete){
            temanToUpdateOrDelete.name = inputName.value!!
            temanToUpdateOrDelete.email = inputEmail.value!!
            update(temanToUpdateOrDelete)
        }else {
            val name = inputName.value!!
            val email = inputEmail.value!!
            insert(Teman(0, name, email))
            inputName.value = ""
            inputEmail.value = ""
        }
    }

    fun clearAllOrDelete(){
        if(isUpdateOrDelete){
            delete(temanToUpdateOrDelete)
        }else{
            clearAll()
        }
    }

    fun  insert(teman: Teman) = viewModelScope.launch {
        repository.insert(teman)
        statusMessage.value = Event("Teman Berhasil Ditambahkan")
    }

    fun update(teman: Teman) = viewModelScope.launch {
        repository.update(teman)
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
        statusMessage.value = Event("Teman Berhasil Diubah")

    }

    fun delete(teman: Teman) = viewModelScope.launch {
        repository.delete(teman)
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
        statusMessage.value = Event("Teman Berhasil Dihapus")
    }

    fun clearAll() = viewModelScope.launch {
        repository.deleteAll()
        statusMessage.value = Event("Semua Teman Berhasil Dihapus")
    }

    fun initUpdateAndDelete(teman: Teman){
        inputName.value = teman.name
        inputEmail.value = teman.email
        isUpdateOrDelete = true
        temanToUpdateOrDelete = teman
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }


    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}