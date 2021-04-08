package com.example.testingskripsinew.jadwal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.testingskripsinew.R
import com.example.testingskripsinew.databinding.ActivityPertemuanBinding
import com.example.testingskripsinew.model.DataKelas
import com.example.testingskripsinew.model.DataMatKul
import com.example.testingskripsinew.model.DataPertemuan
import com.example.testingskripsinew.utils.Data
import com.google.firebase.database.*

class PertemuanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPertemuanBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var myRef: DatabaseReference
    private lateinit var myRef1: DatabaseReference

    companion object {
        const val EXTRA_DATA_MATKUL = "data_matkul"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPertemuanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()
        myRef = database.getReference(Data.KELAS_STATUS)
        myRef1 = database.getReference(Data.KELAS_DATA)

        val item = intent.getParcelableExtra<DataMatKul>(EXTRA_DATA_MATKUL)

        val matkul = item?.nama
        val pengajar1 = item?.pengajar1
        val pengajar2 = item?.pengajar2
        val npmPengajar1 = item?.npmPengajar1
        val npmPengajar2 = item?.npmPengajar2
        val hari = item?.hari
        val jam = item?.jam
        val kelas = item?.kelas

        binding.mataKuliah.text = matkul
        binding.pengajar1.text = pengajar1
        binding.pengajar2.text = pengajar2
        binding.npmPengajar1.text = npmPengajar1
        binding.npmPengajar2.text = npmPengajar2
        binding.hari.text = hari
        binding.jamTgl.text = jam
        binding.kelas.text = "Kelas $kelas"

        onGetData(item)
    }

    private fun onGetData(item: DataMatKul?) {
        myRef.child(item?.kode.toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.getValue(DataPertemuan::class.java)
                if (data != null) {
                    onPertemuan(item, data)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

    private fun onPertemuan(item: DataMatKul?, data: DataPertemuan) {
        val nonActive = ContextCompat.getColor(this, R.color.colorCopyButton)
        val active = ContextCompat.getColor(this, R.color.colorDark)

        if (Data.status == Data.ASDOS) {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA_MATKUL, item)

            // Pertemuan 1
            if (data.pertemuan1 == "0") {
                binding.hadir1.text = getString(R.string.txt_kelas_belum)
                binding.btnPer1.setCardBackgroundColor(active)
            } else {
                binding.hadir1.text = getString(R.string.txt_kelas_sudah)
                binding.btnPer1.setCardBackgroundColor(nonActive)
            }
            binding.btnPer1.setOnClickListener {
                intent.putExtra(DetailActivity.EXTRA_DATA_PERTEMUAN, "1")
                startActivity(intent)
            }

            // Pertemuan 2
            if (data.pertemuan2 == "0") {
                binding.hadir2.text = getString(R.string.txt_kelas_belum)
                binding.btnPer2.setCardBackgroundColor(active)
            } else {
                binding.hadir2.text = getString(R.string.txt_kelas_sudah)
                binding.btnPer2.setCardBackgroundColor(nonActive)
            }
            binding.btnPer2.setOnClickListener {
                intent.putExtra(DetailActivity.EXTRA_DATA_PERTEMUAN, "2")
                startActivity(intent)
            }

            // Pertemuan 3
            if (data.pertemuan3 == "0") {
                binding.hadir3.text = getString(R.string.txt_kelas_belum)
                binding.btnPer3.setCardBackgroundColor(active)
            } else {
                binding.hadir3.text = getString(R.string.txt_kelas_sudah)
                binding.btnPer3.setCardBackgroundColor(nonActive)
            }
            binding.btnPer3.setOnClickListener {
                intent.putExtra(DetailActivity.EXTRA_DATA_PERTEMUAN, "3")
                startActivity(intent)
            }

            // Pertemuan 4
            if (data.pertemuan4 == "0") {
                binding.hadir4.text = getString(R.string.txt_kelas_belum)
                binding.btnPer4.setCardBackgroundColor(active)
            } else {
                binding.hadir4.text = getString(R.string.txt_kelas_sudah)
                binding.btnPer4.setCardBackgroundColor(nonActive)
            }
            binding.btnPer4.setOnClickListener {
                intent.putExtra(DetailActivity.EXTRA_DATA_PERTEMUAN, "4")
                startActivity(intent)
            }

            // Pertemuan 5
            if (data.pertemuan5 == "0") {
                binding.hadir5.text = getString(R.string.txt_kelas_belum)
                binding.btnPer5.setCardBackgroundColor(active)
            } else {
                binding.hadir5.text = getString(R.string.txt_kelas_sudah)
                binding.btnPer5.setCardBackgroundColor(nonActive)
            }
            binding.btnPer5.setOnClickListener {
                intent.putExtra(DetailActivity.EXTRA_DATA_PERTEMUAN, "5")
                startActivity(intent)
            }

            // Pertemuan 6
            if (data.pertemuan6 == "0") {
                binding.hadir6.text = getString(R.string.txt_kelas_belum)
                binding.btnPer6.setCardBackgroundColor(active)
            } else {
                binding.hadir6.text = getString(R.string.txt_kelas_sudah)
                binding.btnPer6.setCardBackgroundColor(nonActive)
            }
            binding.btnPer6.setOnClickListener {
                intent.putExtra(DetailActivity.EXTRA_DATA_PERTEMUAN, "6")
                startActivity(intent)
            }

            // Pertemuan 7
            if (data.pertemuan7 == "0") {
                binding.hadir7.text = getString(R.string.txt_kelas_belum)
                binding.btnPer7.setCardBackgroundColor(active)
            } else {
                binding.hadir7.text = getString(R.string.txt_kelas_sudah)
                binding.btnPer7.setCardBackgroundColor(nonActive)
            }
            binding.btnPer7.setOnClickListener {
                intent.putExtra(DetailActivity.EXTRA_DATA_PERTEMUAN, "7")
                startActivity(intent)
            }

            // Pertemuan 8
            if (data.pertemuan8 == "0") {
                binding.hadir8.text = getString(R.string.txt_kelas_belum)
                binding.btnPer8.setCardBackgroundColor(active)
            } else {
                binding.hadir8.text = getString(R.string.txt_kelas_sudah)
                binding.btnPer8.setCardBackgroundColor(nonActive)
            }
            binding.btnPer8.setOnClickListener {
                intent.putExtra(DetailActivity.EXTRA_DATA_PERTEMUAN, "8")
                startActivity(intent)
            }

            // Pertemuan 9
            if (data.pertemuan9 == "0") {
                binding.hadir9.text = getString(R.string.txt_kelas_belum)
                binding.btnPer9.setCardBackgroundColor(active)
            } else {
                binding.hadir9.text = getString(R.string.txt_kelas_sudah)
                binding.btnPer9.setCardBackgroundColor(nonActive)
            }
            binding.btnPer9.setOnClickListener {
                intent.putExtra(DetailActivity.EXTRA_DATA_PERTEMUAN, "9")
                startActivity(intent)
            }

            // Pertemuan 10
            if (data.pertemuan10 == "0") {
                binding.hadir10.text = getString(R.string.txt_kelas_belum)
                binding.btnPer10.setCardBackgroundColor(active)
            } else {
                binding.hadir10.text = getString(R.string.txt_kelas_sudah)
                binding.btnPer10.setCardBackgroundColor(nonActive)
            }
            binding.btnPer10.setOnClickListener {
                intent.putExtra(DetailActivity.EXTRA_DATA_PERTEMUAN, "10")
                startActivity(intent)
            }

        } else {

            myRef1.child(item?.kode.toString()).child("pertemuan1").child(Data.npmUser.toString())
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val status = snapshot.getValue(DataKelas::class.java)
                        if (status?.pertemuan == "1") {
                            if (status.izin == "0") {
                                binding.hadir1.text = getString(R.string.txt_hadir)
                                binding.btnPer1.setCardBackgroundColor(nonActive)
                            } else {
                                binding.hadir1.text = getString(R.string.text_tidak_hadir)
                                binding.btnPer1.setCardBackgroundColor(active)
                            }
                        } else {
                            binding.hadir1.text = getString(R.string.text_tidak_hadir)
                            binding.btnPer1.setCardBackgroundColor(active)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })

            myRef1.child(item?.kode.toString()).child("pertemuan2").child(Data.npmUser.toString())
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val status = snapshot.getValue(DataKelas::class.java)
                        if (status?.pertemuan == "2") {
                            if (status.izin == "0") {
                                binding.hadir2.text = getString(R.string.txt_hadir)
                                binding.btnPer2.setCardBackgroundColor(nonActive)
                            } else {
                                binding.hadir2.text = getString(R.string.text_tidak_hadir)
                                binding.btnPer2.setCardBackgroundColor(active)
                            }
                        } else {
                            binding.hadir2.text = getString(R.string.text_tidak_hadir)
                            binding.btnPer2.setCardBackgroundColor(active)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })

            myRef1.child(item?.kode.toString()).child("pertemuan3").child(Data.npmUser.toString())
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val status = snapshot.getValue(DataKelas::class.java)
                        if (status?.pertemuan == "3") {
                            if (status.izin == "0") {
                                binding.hadir3.text = getString(R.string.txt_hadir)
                                binding.btnPer3.setCardBackgroundColor(nonActive)
                            } else {
                                binding.hadir3.text = getString(R.string.text_tidak_hadir)
                                binding.btnPer3.setCardBackgroundColor(active)
                            }
                        } else {
                            binding.hadir3.text = getString(R.string.text_tidak_hadir)
                            binding.btnPer3.setCardBackgroundColor(active)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })

            myRef1.child(item?.kode.toString()).child("pertemuan4").child(Data.npmUser.toString())
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val status = snapshot.getValue(DataKelas::class.java)
                        if (status?.pertemuan == "4") {
                            if (status.izin == "0") {
                                binding.hadir4.text = getString(R.string.txt_hadir)
                                binding.btnPer4.setCardBackgroundColor(nonActive)
                            } else {
                                binding.hadir4.text = getString(R.string.text_tidak_hadir)
                                binding.btnPer4.setCardBackgroundColor(active)
                            }
                        } else {
                            binding.hadir4.text = getString(R.string.text_tidak_hadir)
                            binding.btnPer4.setCardBackgroundColor(active)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })

            myRef1.child(item?.kode.toString()).child("pertemuan5").child(Data.npmUser.toString())
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val status = snapshot.getValue(DataKelas::class.java)
                        if (status?.pertemuan == "5") {
                            if (status.izin == "0") {
                                binding.hadir5.text = getString(R.string.txt_hadir)
                                binding.btnPer5.setCardBackgroundColor(nonActive)
                            } else {
                                binding.hadir5.text = getString(R.string.text_tidak_hadir)
                                binding.btnPer5.setCardBackgroundColor(active)
                            }
                        } else {
                            binding.hadir5.text = getString(R.string.text_tidak_hadir)
                            binding.btnPer5.setCardBackgroundColor(active)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })

            myRef1.child(item?.kode.toString()).child("pertemuan6").child(Data.npmUser.toString())
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val status = snapshot.getValue(DataKelas::class.java)
                        if (status?.pertemuan == "6") {
                            if (status.izin == "0") {
                                binding.hadir6.text = getString(R.string.txt_hadir)
                                binding.btnPer6.setCardBackgroundColor(nonActive)
                            } else {
                                binding.hadir6.text = getString(R.string.text_tidak_hadir)
                                binding.btnPer6.setCardBackgroundColor(active)
                            }
                        } else {
                            binding.hadir6.text = getString(R.string.text_tidak_hadir)
                            binding.btnPer6.setCardBackgroundColor(active)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })

            myRef1.child(item?.kode.toString()).child("pertemuan7").child(Data.npmUser.toString())
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val status = snapshot.getValue(DataKelas::class.java)
                        if (status?.pertemuan == "7") {
                            if (status.izin == "0") {
                                binding.hadir7.text = getString(R.string.txt_hadir)
                                binding.btnPer7.setCardBackgroundColor(nonActive)
                            } else {
                                binding.hadir7.text = getString(R.string.text_tidak_hadir)
                                binding.btnPer7.setCardBackgroundColor(active)
                            }
                        } else {
                            binding.hadir7.text = getString(R.string.text_tidak_hadir)
                            binding.btnPer7.setCardBackgroundColor(active)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })

            myRef1.child(item?.kode.toString()).child("pertemuan8").child(Data.npmUser.toString())
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val status = snapshot.getValue(DataKelas::class.java)
                        if (status?.pertemuan == "8") {
                            if (status.izin == "0") {
                                binding.hadir8.text = getString(R.string.txt_hadir)
                                binding.btnPer8.setCardBackgroundColor(nonActive)
                            } else {
                                binding.hadir8.text = getString(R.string.text_tidak_hadir)
                                binding.btnPer8.setCardBackgroundColor(active)
                            }
                        } else {
                            binding.hadir8.text = getString(R.string.text_tidak_hadir)
                            binding.btnPer8.setCardBackgroundColor(active)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })

            myRef1.child(item?.kode.toString()).child("pertemuan9").child(Data.npmUser.toString())
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val status = snapshot.getValue(DataKelas::class.java)
                        if (status?.pertemuan == "9") {
                            if (status.izin == "0") {
                                binding.hadir9.text = getString(R.string.txt_hadir)
                                binding.btnPer9.setCardBackgroundColor(nonActive)
                            } else {
                                binding.hadir9.text = getString(R.string.text_tidak_hadir)
                                binding.btnPer9.setCardBackgroundColor(active)
                            }
                        } else {
                            binding.hadir9.text = getString(R.string.text_tidak_hadir)
                            binding.btnPer9.setCardBackgroundColor(active)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })

            myRef1.child(item?.kode.toString()).child("pertemuan10").child(Data.npmUser.toString())
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val status = snapshot.getValue(DataKelas::class.java)
                        if (status?.pertemuan == "10") {
                            if (status.izin == "0") {
                                binding.hadir10.text = getString(R.string.txt_hadir)
                                binding.btnPer10.setCardBackgroundColor(nonActive)
                            } else {
                                binding.hadir10.text = getString(R.string.text_tidak_hadir)
                                binding.btnPer10.setCardBackgroundColor(active)
                            }
                        } else {
                            binding.hadir10.text = getString(R.string.text_tidak_hadir)
                            binding.btnPer10.setCardBackgroundColor(active)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })

            binding.btnPer1.isClickable = false
            binding.btnPer2.isClickable = false
            binding.btnPer3.isClickable = false
            binding.btnPer4.isClickable = false
            binding.btnPer5.isClickable = false
            binding.btnPer6.isClickable = false
            binding.btnPer7.isClickable = false
            binding.btnPer8.isClickable = false
            binding.btnPer9.isClickable = false
            binding.btnPer10.isClickable = false
        }
    }

    fun btnBack(view: View) {
        finish()
    }


}