package com.example.fittrip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.fittrip.databinding.ActivityMainBinding

class ActivityMain : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(MainFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.fragment_main_navigationBottomView_home -> replaceFragment(MainFragment())
                R.id.fragment_main_navigationBottomView_my_schedule -> replaceFragment(MyScheduleFragment())
                R.id.fragment_main_navigationBottomView_share -> replaceFragment(ShareFragment())
                R.id.fragment_main_navigationBottomView_my_page -> replaceFragment(MyPageFragment())

                else -> {

                }

            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.activity_main_layout, fragment)
        fragmentTransaction.commit()
    }
}