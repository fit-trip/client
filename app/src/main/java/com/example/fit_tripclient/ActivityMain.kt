package com.example.fit_tripclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.fit_tripclient.databinding.ActivityMainBinding

class ActivityMain : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(fragment_main())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.fragment_main_navigationBottomView_home -> replaceFragment(fragment_main())
                R.id.fragment_main_navigationBottomView_my_schedule -> replaceFragment(fragment_mySchedule())
                R.id.fragment_main_navigationBottomView_share -> replaceFragment(frgement_share())
                R.id.fragment_main_navigationBottomView_my_page -> replaceFragment(fragment_my_page())

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