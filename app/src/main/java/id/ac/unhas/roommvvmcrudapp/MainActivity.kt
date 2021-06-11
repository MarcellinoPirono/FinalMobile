package id.ac.unhas.roommvvmcrudapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.ac.unhas.roommvvmcrudapp.databinding.ActivityMainBinding
import id.ac.unhas.roommvvmcrudapp.db.Teman
import id.ac.unhas.roommvvmcrudapp.db.TemanDatabase
import id.ac.unhas.roommvvmcrudapp.db.TemanRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var temanViewModel: TemanViewModel
    private lateinit var adapter: MyRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val dao = TemanDatabase.getInstance(application).temanDAO
        val repository = TemanRepository(dao)
        val factory = TemanViewModelFactory(repository)
        temanViewModel = ViewModelProvider(this,factory).get(TemanViewModel::class.java)
        binding.myViewModel = temanViewModel
        binding.lifecycleOwner = this
        initRecyclerView()

        temanViewModel.messagge.observe(this, Observer {
            it.getContentIfNotHandled()?.let{
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

    }
    private fun initRecyclerView(){
    binding.temanRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyRecyclerViewAdapter({selectedItem:Teman->listItemClicked(selectedItem)})
        binding.temanRecyclerView.adapter = adapter
        displayTemanList()
    }

    private fun displayTemanList(){
        temanViewModel.teman.observe(this, Observer {
            Log.i("MYTAG",it.toString())
            adapter.setList(it)
            adapter.notifyDataSetChanged()

        })
    }

    private fun listItemClicked(teman: Teman){
        //Toast.makeText(this,"selected name is ${teman.name}",Toast.LENGTH_LONG).show()
        temanViewModel.initUpdateAndDelete(teman)
    }
}
