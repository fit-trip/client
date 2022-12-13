package com.example.fittrip.schedule.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fittrip.databinding.ActivityMyScheduleBinding
import com.example.fittrip.schedule.MyScheduleResponse
import com.example.fittrip.schedule.adapter.MyScheduleAdapter

class MyScheduleActivity : AppCompatActivity() {
    var mySchedules = mutableListOf<MyScheduleResponse>()
    lateinit var binding: ActivityMyScheduleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "내 일정"

        val adapter = MyScheduleAdapter(mySchedules)
        setAdaptorOnRecyclerView(adapter)

        val selectedPlaces = intent?.getParcelableArrayExtra("selectedPlaces")

        if (selectedPlaces != null) {
            adapter.addAndRefreshMySchedule(selectedPlaces)
        } else {
            adapter.refreshMySchedule()
        }

    }

    private fun setAdaptorOnRecyclerView(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        binding.myScheduleRecyclerview.adapter = adapter
        binding.myScheduleRecyclerview.layoutManager = LinearLayoutManager(this)
    }
}