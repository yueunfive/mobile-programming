package com.example.mp0404

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var etObjA: EditText
    lateinit var etObjB: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etObjA = findViewById(R.id.etObjA)
        etObjB = findViewById(R.id.etObjB)
    }

    // 버튼 클릭 시 호출되는 메서드
    fun onClickChoice(view: View?) {
        // 입력된 값을 정수로 변환하여 변수에 저장
        val partA = etObjA.text.toString().toIntOrNull()
        val partB = etObjB.text.toString().toIntOrNull()

        if (partA == 2 && partB == 5) {
            Toast.makeText(this, "맞았습니다", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "틀렸습니다", Toast.LENGTH_SHORT).show()
        }
    }
}
