package com.example.testingskripsinew.asdos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.SearchView
import com.example.testingskripsinew.R
import com.example.testingskripsinew.adapter.ClassListAdapter
import com.example.testingskripsinew.databinding.ActivityMonitoringKelasBinding
import com.example.testingskripsinew.jadwal.DetailActivity
import com.example.testingskripsinew.model.DataKelas
import com.example.testingskripsinew.model.DataMatKul
import com.example.testingskripsinew.utils.Data
import com.google.firebase.database.*

class MonitoringKelasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMonitoringKelasBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var myRef: DatabaseReference
    private lateinit var myRef1: DatabaseReference
    lateinit var classListAdapter: ClassListAdapter

    companion object {
        const val EXTRA_DATA_PERTEMUAN = "data_pertemuan"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMonitoringKelasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        database = FirebaseDatabase.getInstance()
        myRef = database.getReference(Data.KELAS_DATA)
        myRef1 = database.getReference(Data.KELAS_STATUS)

        initData()
    }

    private fun initData() {
        val item = intent.getParcelableExtra<DataMatKul>(DetailActivity.EXTRA_DATA_MATKUL)
        val pertemuan = intent.getStringExtra(EXTRA_DATA_PERTEMUAN)

        val animationType = R.anim.layout_animation_fall_down
        val animation = AnimationUtils.loadLayoutAnimation(this, animationType)
        with(binding) {
            rvListMasuk.layoutAnimation = animation
            rvListMasuk.adapter?.notifyDataSetChanged()
            rvListMasuk.scheduleLayoutAnimation()
            rvListMasuk.setHasFixedSize(true)
        }

        onGetData(item, pertemuan)
    }

    private fun onGetData(item: DataMatKul?, pertemuan: String?) {
        myRef.child(item?.kode.toString()).child("pertemuan$pertemuan")
//        myRef.child(item?.kode.toString())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val dataList: ArrayList<DataKelas> = arrayListOf()
                    for (dataSnapshot1 in snapshot.children) {
                        val data = dataSnapshot1.getValue(DataKelas::class.java)
                        data?.let { dataList.add(it) }
                    }
                    onShowData(dataList, pertemuan)
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

        binding.btnKelasSelesai.setOnClickListener {
            myRef1.child(item?.kode.toString()).child("pertemuan$pertemuan").setValue("1")
                .addOnSuccessListener {
                    finish()
                }
        }
    }


    private fun onShowData(dataList: ArrayList<DataKelas>, pertemuan: String?) {
        classListAdapter = ClassListAdapter(dataList)
        binding.rvListMasuk.adapter = classListAdapter
        val totalMhs = "Total Mahasiswa ${dataList.size}"
        binding.textTotal.text = totalMhs

        classListAdapter.setOnItemClickCallback(object : ClassListAdapter.OnItemClickCallback {
            override fun onSlide(data: DataKelas, konfirmasi: String) {
                myRef.child(data.kode.toString()).child("pertemuan$pertemuan")
//                myRef.child(data.kode.toString())
                    .child(data.npm.toString()).child("izin")
                    .setValue(konfirmasi)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val itemMenu = menu?.findItem(R.id.action_search) as MenuItem
        val searchView = itemMenu.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                classListAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    classListAdapter.filter.filter(newText)
                }
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}