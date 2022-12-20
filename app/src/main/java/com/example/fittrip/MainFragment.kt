package com.example.fittrip

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView


class MainFragment : Fragment(), View.OnClickListener {

    lateinit var InputEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        // Inflate the layout for this fragment

        InputEditText = root.findViewById<EditText>(R.id.InputEditText)
        InputEditText.setOnClickListener(this)

        return root
    }

    override fun onClick(view: View) {
        when(view.id) {

            R.id.InputEditText -> {
                val title = InputEditText.text.toString()
                println(title)

                Intent(activity, SearchActivity::class.java).apply {
                    putExtra("title", title)
                    startActivity(this)
                }

            }

        }
    }
}

