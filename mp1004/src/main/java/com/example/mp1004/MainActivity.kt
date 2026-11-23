package com.example.mp1004

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class MainActivity : AppCompatActivity() {
    lateinit var dbHelper: DBHelper
    lateinit var adapter: AccountAdapter
    lateinit var textPeriod: TextView

    var from: String? = null // 검색 시작일 (null이면 전체)
    var to: String? = null   // 검색 종료일

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbHelper = DBHelper(this)
        textPeriod = findViewById(R.id.textPeriod)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AccountAdapter(ArrayList())
        recyclerView.adapter = adapter

        // 추가: 가계부 작성 화면으로 이동
        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            startActivity(Intent(this, InsertActivity::class.java))
        }

        // 검색: 일자 또는 기간을 선택하여 해당 기간의 가계부 출력
        findViewById<Button>(R.id.btnSearch).setOnClickListener {
            val picker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("검색 기간 선택")
                .build()
            picker.addOnPositiveButtonClickListener { range ->
                val format = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
                format.timeZone = TimeZone.getTimeZone("UTC") // 피커는 UTC 기준 밀리초를 반환
                from = format.format(Date(range.first))
                to = format.format(Date(range.second))
                loadList()
            }
            picker.show(supportFragmentManager, "range")
        }
    }

    // 작성 화면에서 돌아올 때마다 목록 새로고침
    override fun onResume() {
        super.onResume()
        loadList()
    }

    fun loadList() {
        adapter.updateItems(dbHelper.search(from, to))
        textPeriod.text = if (from != null) "검색 기간  $from ~ $to" else "검색 기간  전체"
    }
}
