package com.example.fittrip

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fittrip.login.LoginApi
import com.example.fittrip.login.LoginRequestDto
import com.example.fittrip.login.LoginResponseDto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val registerBtn = findViewById<Button>(R.id.registerBtn)
        registerBtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val loginBtn = findViewById<Button>(R.id.loginBtn)
        loginBtn.setOnClickListener {
            val intent = Intent(this, ActivityMain::class.java)

            var id = findViewById<EditText>(R.id.idInput).text.toString()
            var password = findViewById<EditText>(R.id.pwInput).text.toString()

            Log.d("ryu", "id: $id, password: $password")

            login(id, password, intent)

        }
    }

    // 로그인 성공 시 TokenManager.token에 토큰 저장 후 액티비티 전환
    private fun login(id: String, password: String, intent: Intent) {
        val retrofit = Retrofit.Builder()
            .baseUrl(LoginApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(LoginApi::class.java)

        val call = api.login(LoginRequestDto(id, "name", password))

        call.enqueue(object : retrofit2.Callback<LoginResponseDto> {
            override fun onResponse(
                call: retrofit2.Call<LoginResponseDto>,
                response: retrofit2.Response<LoginResponseDto>
            ) {
                Log.d("ryu ", "login response: ${response.body()}")

                val token = response.body()?.accessToken
                val name = response.body()?.username
                if (token != null) {
                    TokenManager.name = name ?: "Anonymous User"
                    TokenManager.token = token
                    Log.d("ryu", "token: ${TokenManager.token}")
                    Toast.makeText(this@LoginActivity, "로그인 성공!", Toast.LENGTH_SHORT).show()
                    finish()
                    startActivity(intent)
                }
                else {
                    Toast.makeText(this@LoginActivity, "로그인 실패! 계정 정보를 확인하세요", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<LoginResponseDto>, t: Throwable) {
                Log.d("ryu ", "error: ${t.message}")
                // 토스트 메시지 출력
                val myToast = Toast.makeText(applicationContext, "로그인 오류", Toast.LENGTH_SHORT)
                myToast.show()
            }
        })
    }
}
