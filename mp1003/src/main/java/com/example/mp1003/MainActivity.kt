package com.example.mp1003

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File

class MainActivity : AppCompatActivity() {
    lateinit var imagePicture: ImageView
    lateinit var textNumber: TextView

    var pictures: List<File> = emptyList() // 외부 저장소에 있는 이미지 파일 목록
    var current = 0                        // 현재 보여주는 그림의 위치

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imagePicture = findViewById(R.id.imagePicture)
        textNumber = findViewById(R.id.textNumber)
        val btnPrev: Button = findViewById(R.id.btnPrev)
        val btnNext: Button = findViewById(R.id.btnNext)

        // 앱 전용 외부 저장소의 MyFileStorage 폴더에서 이미지 파일 목록 읽기
        val folder = getExternalFilesDir("MyFileStorage")
        pictures = folder?.listFiles { file -> file.isFile && file.extension.lowercase() in listOf("png", "jpg", "jpeg") }
            ?.sortedBy { it.name } ?: emptyList()

        if (pictures.isEmpty()) {
            Toast.makeText(this, "외부 저장소에 그림이 없습니다", Toast.LENGTH_SHORT).show()
        }
        showPicture()

        // 이전 그림: 첫 그림이면 마지막 그림으로
        btnPrev.setOnClickListener {
            if (pictures.isNotEmpty()) {
                current = if (current == 0) pictures.size - 1 else current - 1
                showPicture()
            }
        }

        // 다음 그림: 마지막 그림이면 첫 그림으로
        btnNext.setOnClickListener {
            if (pictures.isNotEmpty()) {
                current = (current + 1) % pictures.size
                showPicture()
            }
        }
    }

    // 현재 위치의 이미지 파일을 읽어 출력하고 "번호/전체" 표시
    fun showPicture() {
        if (pictures.isEmpty()) {
            textNumber.text = "0/0"
            return
        }
        imagePicture.setImageBitmap(BitmapFactory.decodeFile(pictures[current].absolutePath))
        textNumber.text = "${current + 1}/${pictures.size}"
    }
}
