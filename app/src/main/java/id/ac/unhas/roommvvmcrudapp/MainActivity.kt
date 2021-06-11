package id.ac.unhas.roommvvmcrudapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import id.ac.unhas.roommvvmcrudapp.databinding.ActivityMainBinding
import id.ac.unhas.roommvvmcrudapp.db.TemanDatabase
import id.ac.unhas.roommvvmcrudapp.db.TemanRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var temanViewModel: TemanViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val dao = TemanDatabase.getInstance(application).temanDAO
        val repository = TemanRepository(dao)
        val factory = TemanViewModelFactory(repository)
        temanViewModel = ViewModelProvider(this,factory).get(TemanViewModel::class.java)
        binding.myViewModel = temanViewModel
        binding.lifecycleOwner = this
    }
}
