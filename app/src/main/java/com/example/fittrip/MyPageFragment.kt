package com.example.fittrip

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.fittrip.databinding.FragmentMyPageBinding
import kotlin.math.log


class MyPageFragment : Fragment(), View.OnClickListener {


    lateinit var userInfo: TextView
    lateinit var reason: Button
    lateinit var howToUse: Button
    lateinit var authorization: Button
    lateinit var ask: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

//        val root = inflater.inflate(R.layout.fragment_my_page, container, false)
        val binding = FragmentMyPageBinding.inflate(inflater, container, false)


        userInfo = binding.userInfo
        reason = binding.reason
        howToUse = binding.howToUse
        authorization = binding.authorization
        ask = binding.ask

        userInfo.setOnClickListener(this)
        reason.setOnClickListener(this)
        userInfo.setOnClickListener(this)
        howToUse.setOnClickListener(this)
        authorization.setOnClickListener(this)
        ask.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(view: View) {
        when (view.id) {

            R.id.userInfo -> {
//                Intent(activity, RegisterActivity::class.java).apply {
//                    putExtra("title", title)
//                    startActivity(this)
//                }
                Log.i("Fit-Trip", "userInfo")
            }

            R.id.reason -> {
//                Intent(activity, RegisterActivity::class.java).apply {
//                    putExtra("title", title)
//                    startActivity(this)
//                }
                Log.i("Fit-Trip", "reason")
            }

            R.id.howToUse -> {
//                Intent(activity, RegisterActivity::class.java).apply {
//                    putExtra("title", title)
//                    startActivity(this)
//                }
                Log.i("Fit-Trip", "HowToUse")
            }

            R.id.authorization -> {
//                Intent(activity, RegisterActivity::class.java).apply {
//                    putExtra("title", title)
//                    startActivity(this)
//                }
                Log.i("Fit-Trip", "Authorization")
            }

            R.id.ask -> {
//                Intent(activity, RegisterActivity::class.java).apply {
//                    putExtra("title", title)
//                    startActivity(this)
//                }
                Log.i("Fit-Trip", "Ask")
            }

        }
    }

}