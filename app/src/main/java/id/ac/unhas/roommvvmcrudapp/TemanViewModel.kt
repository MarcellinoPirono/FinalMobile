package id.ac.unhas.roommvvmcrudapp

import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ac.unhas.roommvvmcrudapp.db.Teman
import id.ac.unhas.roommvvmcrudapp.db.TemanRepository
import kotlinx.coroutines.launch

class TemanViewModel(private val repository: TemanRepository) : ViewModel() {

    val teman = repository.teman

    @Bindable
    val inputName = MutableLiveData<String>()
    @Bindable
    val inputEmail = MutableLiveData<String>()
    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()
    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate() {
        val name = inputName.value!!
        val email = inputEmail.value!!
        insert(Teman(0, name, email))
        inputName.value = ""
        inputEmail.value = ""
    }

    fun clearAllOrDelete(){
        clearAll()
    }

    fun  insert(teman: Teman) = viewModelScope.launch {
        repository.insert(teman)
    }

    fun update(teman: Teman) = viewModelScope.launch {
        repository.update(teman)
    }

    fun delete(teman: Teman) = viewModelScope.launch {
        repository.delete(teman)
    }

    fun clearAll() = viewModelScope.launch {
        repository.deleteAll()
    }
}