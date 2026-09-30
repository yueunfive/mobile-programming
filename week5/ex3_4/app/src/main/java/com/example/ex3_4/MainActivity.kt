package com.example.ex3_4

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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

        val editName = findViewById<EditText>(R.id.editName)
        val editPassword = findViewById<EditText>(R.id.editPassword)
        val editEmail = findViewById<EditText>(R.id.editEmail)
        val editBirth = findViewById<EditText>(R.id.editBirth)
        val editPhone = findViewById<EditText>(R.id.editPhone)
        val buttonResult = findViewById<Button>(R.id.buttonResult)
        val textResult = findViewById<TextView>(R.id.textResult)

        buttonResult.setOnClickListener {
            textResult.text = "성명 - " + editName.text.toString() + "\n" +
                    "비밀번호 - " + editPassword.text.toString() + "\n" +
                    "이메일 - " + editEmail.text.toString() + "\n" +
                    "생년월일 - " + editBirth.text.toString() + "\n" +
                    "연락처 - " + editPhone.text.toString()
        }
    }
}
