package com.example.mp0702

import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var signCat: ImageView
    lateinit var signDog: ImageView

    // 그림 속 정답 (고양이 4마리 > 3마리, 강아지 3마리 < 4마리)
    val catAnswer = R.drawable.sign_great
    val dogAnswer = R.drawable.sign_small

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        signCat = findViewById(R.id.signCat)
        signDog = findViewById(R.id.signDog)
        // 길게 누르면 컨텍스트 메뉴가 뜨도록 등록
        registerForContextMenu(signCat)
        registerForContextMenu(signDog)
    }

    // 누른 뷰에 따라 고양이/강아지 메뉴를 다르게 표시
    override fun onCreateContextMenu(
        menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        when (v?.id) {
            R.id.signCat -> {
                menu?.setHeaderTitle("고양이 비교 메뉴")
                menuInflater.inflate(R.menu.cat_menu, menu)
            }
            R.id.signDog -> {
                menu?.setHeaderTitle("강아지 비교 메뉴")
                menuInflater.inflate(R.menu.dog_menu, menu)
            }
        }
    }

    // 선택한 부등호를 원 자리에 표시하고 정답 여부 출력
    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.catGreater -> checkAnswer(signCat, R.drawable.sign_great, catAnswer)
            R.id.catLess -> checkAnswer(signCat, R.drawable.sign_small, catAnswer)
            R.id.catEqual -> checkAnswer(signCat, R.drawable.sign_equal, catAnswer)
            R.id.dogGreater -> checkAnswer(signDog, R.drawable.sign_great, dogAnswer)
            R.id.dogLess -> checkAnswer(signDog, R.drawable.sign_small, dogAnswer)
            R.id.dogEqual -> checkAnswer(signDog, R.drawable.sign_equal, dogAnswer)
            else -> return super.onContextItemSelected(item)
        }
        return true
    }

    fun checkAnswer(sign: ImageView, selected: Int, answer: Int) {
        sign.setImageResource(selected)
        val message = if (selected == answer) "맞았습니다" else "틀렸습니다"
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}
