package com.example.fittrip

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fittrip.databinding.FragmentMyPageBinding


class MyPageFragment : Fragment(), View.OnClickListener {


    lateinit var reason: TextView
    lateinit var howToUse: TextView
    lateinit var authorization: TextView
    lateinit var ask: TextView
    lateinit var appVersion: TextView
    lateinit var deleteUser: TextView
    lateinit var logout: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

//        val root = inflater.inflate(R.layout.fragment_my_page, container, false)
        val binding = FragmentMyPageBinding.inflate(inflater, container, false)

        binding.textUsername.text = TokenManager.name + " 님의 마이페이지"
        reason = binding.reason
        howToUse = binding.howToUse
        authorization = binding.authorization
        ask = binding.ask
        appVersion = binding.appVersion
        deleteUser = binding.deleteUser
        logout = binding.logout

        reason.setOnClickListener(this)
        howToUse.setOnClickListener(this)
        authorization.setOnClickListener(this)
        ask.setOnClickListener(this)
        appVersion.setOnClickListener(this)
        deleteUser.setOnClickListener(this)
        logout.setOnClickListener(this)

        return binding.root
    }

    fun createDialogBuilder(title: String, message: String): AlertDialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("확인") { dialog, which -> }
            .create()
    }
    override fun onClick(view: View) {
        when (view.id) {
            R.id.reason -> {
                Log.i("Fit-Trip", "reason")
                createDialogBuilder("Fit-Trip 이란?", """
                    Fit-Trip 은 기존의 여행 일정 관리 서비스와는 달리
                    알고리즘에 기반한 가장 최적의 여행 일정을 추천해주는 서비스입니다.
                """.trimIndent()).show()
            }

            R.id.howToUse -> {
                Log.i("Fit-Trip", "HowToUse")
                createDialogBuilder("Fit-Trip 사용법", """
                    1. 여행 장소를 선택합니다.
                    2. 알고리즘에 기반한 일정을 생성합니다.
                    3. 최적의 여행 일정을 확인합니다.
                """.trimIndent()).show()
            }

            R.id.authorization -> {
                Log.i("Fit-Trip", "Authorization")

                createDialogBuilder("Fit-Trip 개인정보처리방침", """
                    Fit-Trip 은 여행 일정을 생성하기 위해
                    여행 장소에 대한 정보만을 수집합니다.
                    수집한 정보는 알고리즘에 기반한 여행 일정 생성에만 사용됩니다.
                """.trimIndent()).show()
            }

            R.id.ask -> {
                Log.i("Fit-Trip", "Ask")

                createDialogBuilder("Fit-Trip 문의하기", """
                    Fit-Trip 의 개발자에게 문의하고 싶으시다면
                    아래의 이메일로 연락해주세요.
                    sanghyun-dev@naver.com
                    """.trimIndent()).show()
            }

            //리니어 레이아웃 2
            R.id.appVersion -> {
                Toast.makeText(activity, "현재 최신 버전입니다.", Toast.LENGTH_SHORT).show()
            }

            R.id.deleteUser -> {
                Toast.makeText(activity, "관리자에게 문의해주세요", Toast.LENGTH_SHORT).show()
            }

            R.id.logout -> {
                this.activity?.finish()
                Intent(context, LoginActivity::class.java).apply {
                    startActivity(this)
                }
                Toast.makeText(activity, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
            }

        }
    }

}