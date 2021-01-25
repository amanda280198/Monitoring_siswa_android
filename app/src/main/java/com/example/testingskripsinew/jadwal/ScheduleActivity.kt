package com.example.testingskripsinew.jadwal

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.testingskripsinew.R
import com.example.testingskripsinew.adapter.ListAdapter
import com.example.testingskripsinew.databinding.ActivityScheduleBinding
import com.example.testingskripsinew.model.DataMatKul
import com.example.testingskripsinew.utils.Data
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class ScheduleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScheduleBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var myRef: DatabaseReference
    lateinit var listAdapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = FirebaseDatabase.getInstance()
        setSupportActionBar(binding.toolbar)

        initData()
        onGetData()
        onShowTime()

        if (Data.status == "asdos") {
            binding.textJudul.text = getString(R.string.text_title)
        }
    }

    private fun initData() {
        val animationType = R.anim.layout_animation_fall_down
        val animation = AnimationUtils.loadLayoutAnimation(this, animationType)
        with(binding) {
            rvListjadwal.layoutAnimation = animation
            rvListjadwal.adapter?.notifyDataSetChanged()
            rvListjadwal.scheduleLayoutAnimation()
            rvListjadwal.setHasFixedSize(true)
        }
    }

    private fun onGetData() {
        myRef = database.getReference(Data.JADWAL_DATA)
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dataList: ArrayList<DataMatKul> = arrayListOf()
                for (dataSnapshot1 in snapshot.children) {
                    val data = dataSnapshot1.getValue(DataMatKul::class.java)
                    if (Data.status != "asdos") {
                        if (data?.kelas.equals(Data.idKelas, ignoreCase = true)) {
                            data?.let { dataList.add(it) }
                        }
                    } else {
                        data?.let { dataList.add(it) }
                    }
                }
                onShowData(dataList)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun onShowData(listData: ArrayList<DataMatKul>) {
        listAdapter = ListAdapter(listData)
        binding.rvListjadwal.adapter = listAdapter

        listAdapter.setOnItemClickCallback(object : ListAdapter.OnItemClickCallback {
            override fun onClicked(dataMatKul: DataMatKul) {
                val intent = Intent(this@ScheduleActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DATA_MATKUL, dataMatKul)
                startActivity(intent)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val itemMenu = menu?.findItem(R.id.action_search) as MenuItem
        val searchView = itemMenu.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                listAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    listAdapter.filter.filter(newText)
                }
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun onShowTime() {
        val thread = object : Thread() {
            override fun run() {
                try {
                    while (!isInterrupted) {
                        sleep(1000)
                        runOnUiThread {
                            val date = System.currentTimeMillis()
                            val sdf =
                                SimpleDateFormat("EEEE, dd MMMM yyyy, h:mm a", Locale.getDefault())
                            val dateString = sdf.format(date)
                            binding.textView7.text = dateString
                        }
                    }
                } catch (e: InterruptedException) {
                }
            }
        }
        thread.start()
    }

}