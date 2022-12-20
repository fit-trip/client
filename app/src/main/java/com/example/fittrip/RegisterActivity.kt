package com.example.fittrip

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fittrip.databinding.ActivityRegisterBinding
import com.example.fittrip.login.LoginApi
import com.example.fittrip.user.UserApi
import com.example.fittrip.user.UserInfoDto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityRegisterBinding.inflate(layoutInflater)

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.confirmBtn.setOnClickListener {
            // 비밀번호와 비밀번호 재입력에서 입력한 값이 같은지 확인
            if (binding.pwInput.text.toString() != binding.pwInput2.text.toString()) {
                // 비밀번호와 비밀번호 재입력이 다른 경우
                // 비밀번호와 비밀번호 재입력을 다시 입력하라는 메시지 출력
                binding.pwInput.setText("")
                binding.pwInput2.setText("")
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            }

            // ID, PW가 빈 값이 아닌지 확인
            if (binding.idInput.text.toString() == "" || binding.pwInput.text.toString() == "") {
                Toast.makeText(this, "ID와 PW를 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                // ID, PW가 빈 값이 아니면 회원가입 API 호출
                val retrofit = Retrofit.Builder()
                    .baseUrl(UserApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val api = retrofit.create(UserApi::class.java)

                val call = api.register(
                    UserInfoDto(
                        id = binding.idInput.text.toString(),
                        name = binding.nameInput.text.toString(),
                        password = binding.pwInput.text.toString()
                    )
                )

                call.enqueue(object: retrofit2.Callback<Unit> {
                    override fun onResponse(call: retrofit2.Call<Unit>, response: retrofit2.Response<Unit>) {
                        if (response.isSuccessful) {
                            // 회원가입 성공
                            Toast.makeText(this@RegisterActivity, "회원가입 성공", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            // 회원가입 실패
                            Toast.makeText(this@RegisterActivity, "회원가입 실패", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<Unit>, t: Throwable) {
                        Toast.makeText(this@RegisterActivity, "회원가입 오류", Toast.LENGTH_SHORT).show()
                    }
                })


                // 로그인 화면으로 이동
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }

        }

        setContentView(binding.root)
    }
}