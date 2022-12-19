package com.example.fittrip

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val title = intent.getStringExtra("title")
        println(title)
    }
}