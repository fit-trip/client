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
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fittrip.databinding.FragmentMyPageBinding
import kotlin.math.log


class MyPageFragment : Fragment(), View.OnClickListener {


    lateinit var userInfo: TextView
    lateinit var reason: Button
    lateinit var howToUse: Button
    lateinit var authorization: Button
    lateinit var ask: Button
    lateinit var appVersion: Button
    lateinit var deleteUser: Button
    lateinit var logout: Button


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
        appVersion = binding.appVersion
        deleteUser = binding.deleteUser
        logout = binding.logout

        userInfo.setOnClickListener(this)
        reason.setOnClickListener(this)
        userInfo.setOnClickListener(this)
        howToUse.setOnClickListener(this)
        authorization.setOnClickListener(this)
        ask.setOnClickListener(this)
        appVersion.setOnClickListener(this)
        deleteUser.setOnClickListener(this)
        logout.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(view: View) {
        when (view.id) {

            //리니어 레이아웃 1
            R.id.userInfo -> {
                Intent(activity, DetailOfMyPageActivity::class.java).apply {
                    startActivity(this)
                }
                Log.i("Fit-Trip", "userInfo")
            }

            R.id.reason -> {
                Intent(activity, DetailOfMyPageActivity::class.java).apply {
                    startActivity(this)
                }
                Log.i("Fit-Trip", "reason")
            }

            R.id.howToUse -> {
                Intent(activity, DetailOfMyPageActivity::class.java).apply {
                    startActivity(this)
                }
                Log.i("Fit-Trip", "HowToUse")
            }

            R.id.authorization -> {
                Intent(activity, DetailOfMyPageActivity::class.java).apply {
                    startActivity(this)
                }
                Log.i("Fit-Trip", "Authorization")
            }

            R.id.ask -> {
                Intent(activity, DetailOfMyPageActivity::class.java).apply {
                    startActivity(this)
                }
                Log.i("Fit-Trip", "Ask")
            }

            //리니어 레이아웃 2
            R.id.appVersion -> {
                Toast.makeText(activity, "현재 최신 버전입니다.", Toast.LENGTH_SHORT).show()
            }

            R.id.deleteUser -> {
                Toast.makeText(activity, "회원 탈퇴가 완료되었습니다.", Toast.LENGTH_SHORT).show()
            }

            R.id.logout -> {
                Toast.makeText(activity, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
            }

        }
    }

}