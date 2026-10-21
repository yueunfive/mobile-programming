package com.example.mp0604

import android.content.Intent
import android.net.Uri
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

        val	editText: EditText = findViewById(R.id.editText)
        val	button:	Button = findViewById(R.id.button);
        button.setOnClickListener {
            val	url = editText.text.toString() // EditText에 입력된 텍스트 가져오기
            // web browsing by implicit intent
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://" + url))
            startActivity(intent) // 웹 브라우저를 열고 지정된 URL을 표시하도록 인텐트를 실행
        }
    }
}