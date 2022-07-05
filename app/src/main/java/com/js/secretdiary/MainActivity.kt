package com.js.secretdiary

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.js.secretdiary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var changePasswordMode =false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNumberPicker()
        initOpenButton()
        initChangePasswordButton()
    }

    private fun initNumberPicker() {
        binding.firstNumberPicker.apply {
            minValue = 0
            maxValue = 9
        }

        binding.secondNumberPicker.apply {
            minValue = 0
            maxValue = 9
        }

        binding.thirdNumberPicker.apply {
            minValue = 0
            maxValue = 9
        }
    }

    private fun initChangePasswordButton() {
        binding.changePasswordButton.setOnClickListener {
            val password = "${binding.firstNumberPicker.value}${binding.secondNumberPicker.value}${binding.thirdNumberPicker.value}"

            if(changePasswordMode){
                MyApp.prefs.setString("password", password)
                changePasswordMode = false
                binding.changePasswordButton.setBackgroundColor(Color.BLACK)
            }else{
                 if(password != MyApp.prefs.getString("password", "000")){
                     showErrorPopup()
                     return@setOnClickListener
                 }

                Toast.makeText(this,"변경할 비밀번호를 입력하고 다시 눌러주세요", Toast.LENGTH_SHORT).show()
                changePasswordMode = true
                binding.changePasswordButton.setBackgroundColor(Color.RED)
            }
        }
    }

    private fun initOpenButton(){
        binding.openButton.setOnClickListener {
            if (changePasswordMode) {
                Toast.makeText(this,
                    "비밀번호 변경 모드입니다.",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val password = "${binding.firstNumberPicker.value}${binding.secondNumberPicker.value}${binding.thirdNumberPicker.value}"

            if (password ==
                MyApp.prefs.getString("password","000")) {
                startActivity(
                    Intent(this, DiaryActivity::class.java
                    )
                )
            } else {
                showErrorPopup()
            }
        }
    }

    private fun showErrorPopup() {
        AlertDialog.Builder(this)
            .setTitle("실패")
            .setMessage("비밀번호가 잘못되었습니다.")
            .setPositiveButton("확인") { _, _ -> }
            .create()
            .show()
    }
}