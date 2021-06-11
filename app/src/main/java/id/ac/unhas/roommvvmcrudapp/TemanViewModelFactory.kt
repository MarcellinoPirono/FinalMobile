package id.ac.unhas.roommvvmcrudapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.ac.unhas.roommvvmcrudapp.db.TemanRepository
import java.lang.IllegalArgumentException

class TemanViewModelFactory(private val repository: TemanRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TemanViewModel::class.java)){
            return TemanViewModel(repository) as T
        }
            throw IllegalArgumentException("Unknown View Model class")
    }

}