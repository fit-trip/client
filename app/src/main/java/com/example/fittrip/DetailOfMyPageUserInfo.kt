package com.example.fittrip

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.fittrip.databinding.FragmentDetailOfMyPageUserInfoBinding
import com.example.fittrip.databinding.FragmentMyPageBinding

class DetailOfMyPageUserInfo : Fragment(), View.OnClickListener {

    lateinit var cancelBtn: Button
    lateinit var editBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentDetailOfMyPageUserInfoBinding.inflate(inflater, container, false)

        cancelBtn = binding.cancelBtn
        editBtn = binding.editBtn
        cancelBtn.setOnClickListener(this)
        editBtn.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.editBtn -> {
                Intent(activity, ActivityMain::class.java).apply {
                    putExtra("FragmentName", "FragmentMyPage")
                    Toast.makeText(activity, "수정되었습니다.", Toast.LENGTH_SHORT).show()
                    startActivity(this)
                }
            }

            R.id.cancelBtn -> {
                Intent(activity, ActivityMain::class.java).apply {
                    putExtra("FragmentName", "FragmentMyPage")
                    Toast.makeText(activity, "취소되었습니다.", Toast.LENGTH_SHORT).show()
                    startActivity(this)
                }
            }
        }
    }


}