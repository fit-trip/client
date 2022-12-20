package com.example.fittrip

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fittrip.databinding.ActivityMainBinding
import com.example.fittrip.schedule.fragment.MyScheduleFragment
import com.example.fittrip.schedule.fragment.SharedScheduleFragment
import java.security.MessageDigest


class ActivityMain : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        getAppKeyHash()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(MainFragment())

        val stringExtra = intent.getStringExtra("FragmentName")
        if (stringExtra == "sharedSchedule") {
            replaceFragment(SharedScheduleFragment())
            binding.bottomNavigationView.menu.findItem(R.id.fragment_main_navigationBottomView_home).isChecked = false
            binding.bottomNavigationView.menu.findItem(R.id.fragment_main_navigationBottomView_share).isChecked = true
        }

        if (stringExtra == "mySchedule") {
            replaceFragment(MyScheduleFragment())
            binding.bottomNavigationView.menu.findItem(R.id.fragment_main_navigationBottomView_home).isChecked = false
            binding.bottomNavigationView.menu.findItem(R.id.fragment_main_navigationBottomView_my_schedule).isChecked = true
        }

        if (intent.getParcelableArrayExtra("selectedPlaces") != null) {
            val myScheduleFragment = MyScheduleFragment()
            myScheduleFragment.arguments = intent.extras
            binding.bottomNavigationView.menu.findItem(R.id.fragment_main_navigationBottomView_home).isChecked = false
            binding.bottomNavigationView.menu.findItem(R.id.fragment_main_navigationBottomView_my_schedule).isChecked = true
            replaceFragment(myScheduleFragment)
        }



        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.fragment_main_navigationBottomView_home -> replaceFragment(MainFragment())
                R.id.fragment_main_navigationBottomView_my_schedule -> replaceFragment(MyScheduleFragment())
                R.id.fragment_main_navigationBottomView_share -> replaceFragment(SharedScheduleFragment())
                R.id.fragment_main_navigationBottomView_my_page -> replaceFragment(MyPageFragment())
                else -> {
                }
            }
            true
        }

        val FragmentName:String = intent.getStringExtra("FragmentName").toString()

        when(FragmentName){
            "FragmentMyPage" -> replaceFragment(MyPageFragment())
        }

    }

    private fun getAppKeyHash() {
        try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                var md: MessageDigest
                md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val something: String = String(android.util.Base64.encode(md.digest(), 0))
                Log.e("Hash key", something)
            }
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            Log.e("name not found", e.toString())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.activity_main_layout, fragment)
        fragmentTransaction.commit()
    }
}