package com.example.mp0602

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var rdObj1: RadioButton
    lateinit var rdObj2: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        rdObj1 = findViewById(R.id.radioButton1)
        rdObj2 = findViewById(R.id.radioButton2)
        val	btnObj : Button = findViewById(R.id.button)
        
        btnObj.setOnClickListener	{
            // GameActivity로 이동하기 위한 인텐트 생성
            val intent = Intent(this, GameActivity::class.java)
            // 라디오 버튼에서 선택된 값에 따라 인텐트에 값을 추가
            if (rdObj1.isChecked) intent.putExtra("image", 1)
            else if (rdObj2.isChecked) intent.putExtra("image", 2)
            // GameActivity로 인텐트 전송 및 액티비티 시작
            startActivity(intent)
        }
    }
}