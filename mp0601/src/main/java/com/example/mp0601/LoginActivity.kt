package com.example.mp0601

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    lateinit var etId: EditText
    lateinit var etPasswd: EditText
    lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        etId = findViewById(R.id.editText01)
        etPasswd = findViewById(R.id.editText02)
        btnLogin = findViewById(R.id.button)

        btnLogin.setOnClickListener {
            val id = etId.text.toString()
            val passwd = etPasswd.text.toString()
            Toast.makeText(applicationContext, "아이디 : $id\n비밀번호 : $passwd", Toast.LENGTH_SHORT).show()
        }
    }
}
