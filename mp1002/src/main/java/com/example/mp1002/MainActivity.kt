package com.example.mp1002

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    lateinit var editSchedule: EditText
    lateinit var btnSave: Button

    var fileName = "" // 선택한 날짜의 파일 이름 (예: 2025_12_25.txt)
    var dateText = "" // 토스트에 표시할 날짜 (예: 2025/12/25)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val calendarView: CalendarView = findViewById(R.id.calendarView)
        editSchedule = findViewById(R.id.editSchedule)
        btnSave = findViewById(R.id.btnSave)
        val btnDelete: Button = findViewById(R.id.btnDelete)

        // 처음에는 오늘 날짜의 일정 표시
        val today = Calendar.getInstance()
        selectDate(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH))

        // 날짜를 선택하면 해당 날짜의 일정 파일 읽기
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectDate(year, month, dayOfMonth)
        }

        // 저장하기/수정하기: 입력한 일정을 내부 저장소 파일에 쓰기
        btnSave.setOnClickListener {
            openFileOutput(fileName, Context.MODE_PRIVATE).use {
                it.write(editSchedule.text.toString().toByteArray())
            }
            btnSave.text = "수정하기"
            Toast.makeText(this, "$dateText 일정을 저장했습니다", Toast.LENGTH_SHORT).show()
        }

        // 삭제하기: 선택한 날짜의 일정 파일 삭제
        btnDelete.setOnClickListener {
            if (deleteFile(fileName)) {
                editSchedule.text.clear()
                btnSave.text = "저장하기"
                Toast.makeText(this, "$dateText 일정을 삭제했습니다", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "삭제할 일정이 없습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 선택한 날짜의 파일이 있으면 내용을 읽어 출력, 없으면 빈 칸
    fun selectDate(year: Int, month: Int, day: Int) {
        fileName = "${year}_${month + 1}_${day}.txt"
        dateText = "$year/${month + 1}/$day"
        val file = File(filesDir, fileName)
        if (file.exists()) {
            editSchedule.setText(openFileInput(fileName).bufferedReader().use { it.readText() })
            btnSave.text = "수정하기"
        } else {
            editSchedule.text.clear()
            btnSave.text = "저장하기"
        }
    }
}
