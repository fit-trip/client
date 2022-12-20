package com.example.fittrip.schedule.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fittrip.databinding.FragmentMyScheduleBinding
import com.example.fittrip.schedule.ScheduleResponse
import com.example.fittrip.schedule.adapter.MyScheduleAdapter



class MyScheduleFragment : Fragment() {

    lateinit var binding: FragmentMyScheduleBinding
    var mySchedules = mutableListOf<ScheduleResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = FragmentMyScheduleBinding.inflate(inflater, container, false)
        binding.toolbar.title = "내 일정"

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MyScheduleAdapter(mySchedules)
        setAdaptorOnRecyclerView(adapter)
        adapter.refreshMySchedule()

        val selectedPlaces = arguments?.getParcelableArray("selectedPlaces")

        if (selectedPlaces != null) {
            adapter.addAndRefreshMySchedule(selectedPlaces)
        } else {
            adapter.refreshMySchedule()
        }
    }

    private fun setAdaptorOnRecyclerView(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        binding.myScheduleRecyclerview.adapter = adapter
        binding.myScheduleRecyclerview.layoutManager = LinearLayoutManager(this.context)
    }
}