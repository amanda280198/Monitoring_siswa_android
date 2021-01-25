package com.example.testingskripsinew.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.testingskripsinew.databinding.ItemHadirBinding
import com.example.testingskripsinew.model.DataKelas
import java.util.*

class ClassListAdapter(private var dataList: ArrayList<DataKelas>) :
    RecyclerView.Adapter<ClassListAdapter.ViewHolder>(), Filterable {
    var userFilterList: ArrayList<DataKelas> = arrayListOf()

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

                switchIzin.isOn = dataKelas.izin != "0"

                switchIzin.setOnToggledListener { _, isOn ->
                    if (isOn) {
                        Toast.makeText(itemView.context, "izin", Toast.LENGTH_SHORT).show()
                        onItemClickCallback.onSlide(dataKelas, "1")
                    } else {
                        Toast.makeText(itemView.context, "tidak izin", Toast.LENGTH_SHORT).show()
                        onItemClickCallback.onSlide(dataKelas, "0")
                    }
                }
            }
        }
    }

    override fun getFilter(): Filter {
        return myFilter
    }

    private var myFilter: Filter = object : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val charSearch = charSequence.toString()
            userFilterList = if (charSequence.isEmpty()) {
                dataList
            } else {
                val resultList: ArrayList<DataKelas> = arrayListOf()
                for (data in dataList) {
                    if (data.nama?.toLowerCase(Locale.ROOT)?.contains(charSearch.toLowerCase(Locale.ROOT)) == true) {
                        resultList.add(data)
                    }
                }
                resultList
            }
            val filterResults = FilterResults()
            filterResults.values = userFilterList
            return filterResults
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
            userFilterList = filterResults.values as ArrayList<DataKelas>
            notifyDataSetChanged()
        }
    }

    init {
        userFilterList = dataList
    }

    interface OnItemClickCallback {
        fun onSlide(data: DataKelas, konfirmasi: String)
    }
}