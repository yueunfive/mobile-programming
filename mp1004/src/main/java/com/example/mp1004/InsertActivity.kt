package com.example.mp1004

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar

class InsertActivity : AppCompatActivity() {
    lateinit var textDate: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_insert)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textDate = findViewById(R.id.textDate)
        val radioIncome: RadioButton = findViewById(R.id.radioIncome)
        val editUsage: EditText = findViewById(R.id.editUsage)
        val editAmount: EditText = findViewById(R.id.editAmount)
        val editMemo: EditText = findViewById(R.id.editMemo)
        val dbHelper = DBHelper(this)

        setToday()

        // 오늘: 오늘 날짜로 설정
        findViewById<Button>(R.id.btnToday).setOnClickListener { setToday() }

        // 달력: DatePickerDialog로 날짜 선택
        findViewById<Button>(R.id.btnCalendar).setOnClickListener {
            val (y, m, d) = textDate.text.split("-").map { it.toInt() }
            DatePickerDialog(this, { _, year, month, day ->
                setDate(year, month + 1, day)
            }, y, m - 1, d).show()
        }

        // 저장: 입력한 항목을 데이터베이스에 추가
        findViewById<Button>(R.id.btnSave).setOnClickListener {
            val usage = editUsage.text.toString()
            val amount = editAmount.text.toString()
            if (usage.isEmpty() || amount.isEmpty()) {
                Toast.makeText(this, "용도와 금액을 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val type = if (radioIncome.isChecked) "수입" else "지출"
            dbHelper.insert(type, usage, textDate.text.toString(), amount.toInt(), editMemo.text.toString())
            Toast.makeText(this, "저장했습니다", Toast.LENGTH_SHORT).show()
            editUsage.text.clear()
            editAmount.text.clear()
            editMemo.text.clear()
        }

        // 목록: 메인 화면으로 돌아가기
        findViewById<Button>(R.id.btnList).setOnClickListener { finish() }
    }

    fun setToday() {
        val c = Calendar.getInstance()
        setDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH))
    }

    // 날짜를 yyyy-MM-dd 형식으로 표시 (문자열 비교로 기간 검색이 가능하도록)
    fun setDate(year: Int, month: Int, day: Int) {
        textDate.text = "%04d-%02d-%02d".format(year, month, day)
    }
}
