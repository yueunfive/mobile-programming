package com.example.mp0704

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var yesCount = 0
    var noCount = 0
    // 액션바의 카운트 배지 (메뉴가 만들어진 뒤에 연결됨)
    var tvYesCount: TextView? = null
    var tvNoCount: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnYes: Button = findViewById(R.id.btnYes)
        val btnNo: Button = findViewById(R.id.btnNo)
        // 버튼을 누를 때마다 액션바의 찬성/반대 횟수 증가
        btnYes.setOnClickListener {
            yesCount++
            showCounts()
        }
        btnNo.setOnClickListener {
            noCount++
            showCounts()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        // actionLayout으로 지정한 찬성/반대 레이아웃에서 카운트 TextView 가져오기
        tvYesCount = menu?.findItem(R.id.menuYes)?.actionView?.findViewById(R.id.countYes)
        tvNoCount = menu?.findItem(R.id.menuNo)?.actionView?.findViewById(R.id.countNo)
        showCounts()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // 초기화: 찬성/반대 횟수를 0으로
            R.id.menuReset -> {
                yesCount = 0
                noCount = 0
                showCounts()
            }
            // 종료: 액티비티 종료
            R.id.menuClose -> finish()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    fun showCounts() {
        tvYesCount?.text = yesCount.toString()
        tvNoCount?.text = noCount.toString()
    }
}
