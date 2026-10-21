package com.example.mp0605

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val	etObj01 : EditText = findViewById(R.id.editText1)
        val	etObj02 : EditText = findViewById(R.id.editText2)
        val	etObj03 : EditText = findViewById(R.id.editText3)
        // 이메일, 제목, 내용 데이터를 문자열로 가져오기
        val	email = etObj01.text.toString()
        val	subject = etObj02.text.toString()
        val	content = etObj03.text.toString()
        val	button : Button = findViewById(R.id.button)
        button.setOnClickListener {
            val	intent = Intent(Intent.ACTION_SEND) //이메일	보내는	인텐트	생성
            intent.type = "message/rfc822" //이메일	전송	타입	지정
            // 이메일, 제목, 내용을 인텐트에 추가
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(etObj01.text.toString()))
            intent.putExtra(Intent.EXTRA_SUBJECT, etObj02.text.toString())
            intent.putExtra(Intent.EXTRA_TEXT, etObj03.text.toString())
            // 이메일 보낼 앱을 선택할 수 있도록 다이얼로그를 띄우는 인텐트 생성
            val	intent2 = Intent.createChooser(intent, "앱을 선택하세요")
            // 사용자가 선택한 앱으로 이메일 전송
            startActivity(intent2)
        }

    }
}