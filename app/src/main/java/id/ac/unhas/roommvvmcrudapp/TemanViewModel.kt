package id.ac.unhas.roommvvmcrudapp

import android.util.Patterns
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

        if(inputName.value==null){
            statusMessage.value = Event("Silahkan Masukkan Nama Teman")
        }else if(inputEmail.value==null){
            statusMessage.value = Event("Silahkan Masukkan Email Teman")
        }else if(!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()){
            statusMessage.value = Event("Silahkan Masukkan Email dengan Benar")
        }else{
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
    }

    fun clearAllOrDelete(){
        if(isUpdateOrDelete){
            delete(temanToUpdateOrDelete)
        }else{
            clearAll()
        }
    }

    fun  insert(teman: Teman) = viewModelScope.launch {
        val newRowId:Long = repository.insert(teman)
        if(newRowId>-1){
            statusMessage.value = Event("Teman Berhasil Ditambahkan $newRowId")
        }else {
            statusMessage.value = Event("Error")
        }
    }

    fun update(teman: Teman) = viewModelScope.launch {
        val noOfRows = repository.update(teman)
        if(noOfRows>0){
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
        statusMessage.value = Event("$noOfRows Teman Berhasil Diubah")
        }else{
            statusMessage.value = Event("Error")
        }
    }

    fun delete(teman: Teman) = viewModelScope.launch {
        val noOfRowsDeleted = repository.delete(teman)

        if(noOfRowsDeleted>0) {
            inputName.value = null
            inputEmail.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("$noOfRowsDeleted Teman Berhasil Dihapus")
        }else{
            statusMessage.value = Event("Error")
        }
    }

    fun clearAll() = viewModelScope.launch {
        val noOfRowsDeleted = repository.deleteAll()
        if(noOfRowsDeleted>0) {
            statusMessage.value = Event("$noOfRowsDeleted Teman Berhasil Dihapus")
        }else{
            statusMessage.value = Event("Error")

        }
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