package com.example.fittrip

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.fittrip.databinding.ActivityLoginBinding
import com.example.fittrip.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.bind(layoutInflater.inflate(R.layout.activity_login, null))
        setContentView(binding.root)

        val registerBtn = findViewById<Button>(R.id.registerBtn)
        registerBtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val LoginBtn = findViewById<Button>(R.id.loginBtn)
        LoginBtn.setOnClickListener {
            val intent = Intent(this, ActivityMain::class.java)
            startActivity(intent)
        }
    }
}