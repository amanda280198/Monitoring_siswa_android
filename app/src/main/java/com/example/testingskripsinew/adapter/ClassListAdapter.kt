package com.example.testingskripsinew.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.testingskripsinew.databinding.ItemHadirBinding
import com.example.testingskripsinew.model.DataKelas
import java.util.*

class ClassListAdapter(private var dataList: ArrayList<DataKelas>) :
    RecyclerView.Adapter<ClassListAdapter.ViewHolder>(), Filterable {
    val userListFull: ArrayList<DataKelas> = arrayListOf()

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ItemHadirBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(var binding: ItemHadirBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(dataKelas: DataKelas) {
            with(binding) {
                namaMhs.text = dataKelas.nama
                txtNpm.text = dataKelas.npm
                jamMasuk.text = dataKelas.jamMasuk
                jamKeluar.text = dataKelas.jamKeluar
                jamKeluar.text = dataKelas.jamKeluar
            }
            itemView.setOnClickListener {
                onItemClickCallback.onClicked(dataKelas)
            }
        }
    }

    interface OnItemClickCallback {
        fun onClicked(data: DataKelas)
    }

    override fun getFilter(): Filter {
        return myFilter
    }

    private var myFilter: Filter = object : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val filteredList: ArrayList<DataKelas> = arrayListOf()
            if (charSequence.isEmpty()) {
                filteredList.addAll(userListFull)
            } else {
                for (data in userListFull) {
                    if (
                        data.nama?.toLowerCase(Locale.getDefault())?.contains(
                            charSequence.toString().toLowerCase(Locale.getDefault())
                        ) == true
                    ) {
                        filteredList.add(data)
                    }
                }
            }
            val filterResults = FilterResults()
            filterResults.values = filteredList
            return filterResults
        }

        //Automatic on UI thread
        override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
            dataList.clear()
            dataList.addAll((filterResults.values as Collection<DataKelas>))
            notifyDataSetChanged()
        }
    }

    init {
        userListFull.addAll(dataList)
    }
}