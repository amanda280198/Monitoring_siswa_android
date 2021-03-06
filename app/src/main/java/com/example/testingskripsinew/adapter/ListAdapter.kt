package com.example.testingskripsinew.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.testingskripsinew.R
import com.example.testingskripsinew.databinding.ItemJadwalBinding
import com.example.testingskripsinew.model.DataMatKul
import com.example.testingskripsinew.utils.Data
import java.util.*

class ListAdapter(private var dataList: ArrayList<DataMatKul>) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>(), Filterable {
    var userFilterList: ArrayList<DataMatKul> = arrayListOf()

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ItemJadwalBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userFilterList[position])
    }

    override fun getItemCount(): Int {
        return userFilterList.size
    }

    inner class ViewHolder(var binding: ItemJadwalBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(dataMatkul: DataMatKul) {
            with(binding) {
                namaMatkul.text = dataMatkul.nama
                kelas.text = dataMatkul.kelas
                hari.text = dataMatkul.hari
                jam.text = dataMatkul.jam
                pengajar1.text = dataMatkul.pengajar1
                pengajar2.text = dataMatkul.pengajar2
                npmPengajar1.text = dataMatkul.npmPengajar1
                npmPengajar2.text = dataMatkul.npmPengajar2

                if (Data.npmAsdos == dataMatkul.npmPengajar1 || Data.npmAsdos == dataMatkul.npmPengajar2)
                    contenerItem.setBackgroundResource(R.color.colorCopyButton)
                else
                    contenerItem.setBackgroundResource(R.color.colorDark)
            }
            itemView.setOnClickListener {
                onItemClickCallback.onClicked(dataMatkul)
            }
        }
    }

    interface OnItemClickCallback {
        fun onClicked(data: DataMatKul)
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
                val resultList: ArrayList<DataMatKul> = arrayListOf()
                for (data in dataList) {
                    if (data.kelas?.toLowerCase(Locale.ROOT)?.contains(charSearch.toLowerCase(Locale.ROOT)) == true) {
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
            userFilterList = filterResults.values as ArrayList<DataMatKul>
            notifyDataSetChanged()
        }
    }

    init {
        userFilterList = dataList
    }
}