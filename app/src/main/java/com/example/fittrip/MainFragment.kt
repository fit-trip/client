package com.example.fittrip

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class MainFragment : Fragment(), View.OnClickListener{


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        // Inflate the layout for this fragment

        val button = root.findViewById<Button>(R.id.createScheduleBtn)
        button.setOnClickListener(this)

        return root

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.createScheduleBtn -> {
                val intent = Intent(getActivity(), RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }
}

