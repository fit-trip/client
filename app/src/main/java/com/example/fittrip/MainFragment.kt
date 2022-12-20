package com.example.fittrip

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fittrip.databinding.FragmentMainBinding
import com.example.fittrip.map.activity.SelectLocationActivity


class MainFragment : Fragment(), View.OnClickListener {

    lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.btnStartSchedule.setOnClickListener(this)
        binding.btnCreateSchedule.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.btn_start_schedule -> {
                binding.btnStartSchedule.visibility = View.GONE
                binding.formScheduleName.visibility = View.VISIBLE
            }

            R.id.btn_create_schedule -> {
                val title = binding.editScheduleName.text.toString()

                Intent(activity, SearchActivity::class.java).apply {
                    SelectLocationActivity.selectedPlaces.clear()
                    putExtra("scheduleName", title)
                    startActivity(this)
                }
            }

        }
    }
}

