package id.ac.unhas.roommvvmcrudapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import id.ac.unhas.roommvvmcrudapp.databinding.ListItemBinding
import id.ac.unhas.roommvvmcrudapp.db.Teman
import id.ac.unhas.roommvvmcrudapp.generated.callback.OnClickListener

class MyRecyclerViewAdapter(private val clickListener: (Teman)->Unit)
    : RecyclerView.Adapter<MyViewHolder>()
{
    private val temanList= ArrayList<Teman>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : ListItemBinding =
            DataBindingUtil.inflate(layoutInflater,R.layout.list_item,parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(temanList[position],clickListener)
    }

    override fun getItemCount(): Int {
        return temanList.size
    }

    fun setList(teman: List<Teman>){
        temanList.clear()
        temanList.addAll(teman)
    }

}

class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(teman: Teman,clickListener: (Teman)->Unit) {
        binding.nameTextView.text = teman.name
        binding.emailTextView.text = teman.email
        binding.listItemLayout.setOnClickListener{
            clickListener(teman)
        }
    }
}