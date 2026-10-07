package com.example.mp0306

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
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

        val textView1 = findViewById<TextView>(R.id.textView1)
        val imageView1 = findViewById<ImageView>(R.id.imageView1)
        val button1 = findViewById<ImageButton>(R.id.button1)

        val images = arrayOf(R.drawable.cat1, R.drawable.cat2, R.drawable.cat3, R.drawable.cat4, R.drawable.cat5)
        val names = arrayOf("cat1.png", "cat2.png", "cat3.png", "cat4.png", "cat5.png")
        var index = 0

        button1.setOnClickListener {
            index++
            // 마지막 이미지 다음에는 첫 번째 이미지로 돌아감
            if (index >= images.size) {
                index = 0
            }
            imageView1.setImageResource(images[index])
            textView1.text = names[index]
        }
    }
}
