package com.example.mp0401

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
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

        val imageViewDiceA = findViewById<ImageView>(R.id.imageViewDiceA)
        val imageViewDiceB = findViewById<ImageView>(R.id.imageViewDiceB)
        val editTextSum = findViewById<EditText>(R.id.editTextSum)
        val buttonResult = findViewById<Button>(R.id.buttonResult)

        // 주사위 이미지 리소스 배열 선언
        val diceNumber = intArrayOf(R.drawable.dice1, R.drawable.dice2,
                                    R.drawable.dice3, R.drawable.dice4,
                                    R.drawable.dice5, R.drawable.dice6)

        // 주사위 값 랜덤으로 설정 (1~6)
        val numA = (Math.random() * 6).toInt() + 1
        val numB = (Math.random() * 6).toInt() + 1

        imageViewDiceA.setImageResource(diceNumber[numA - 1])
        imageViewDiceB.setImageResource(diceNumber[numB - 1])

        buttonResult.setOnClickListener {
            val answer = editTextSum.text.toString().toIntOrNull()
            if (answer == numA + numB) {
                Toast.makeText(this, "맞았습니다", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "틀렸습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
