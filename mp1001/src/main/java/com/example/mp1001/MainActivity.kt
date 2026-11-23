package com.example.mp1001

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    val numImages = intArrayOf(
        R.drawable.num0, R.drawable.num1, R.drawable.num2, R.drawable.num3, R.drawable.num4,
        R.drawable.num5, R.drawable.num6, R.drawable.num7, R.drawable.num8, R.drawable.num9
    )
    lateinit var imageBg: ImageView
    lateinit var imageNum1: ImageView
    lateinit var imageNum2: ImageView
    lateinit var editAnswer: EditText
    lateinit var textMessage: TextView

    var total = 0   // 전체 문제 개수
    var correct = 0 // 정답 개수
    var answer = 0  // 현재 문제의 정답

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imageBg = findViewById(R.id.imageBg)
        imageNum1 = findViewById(R.id.imageNum1)
        imageNum2 = findViewById(R.id.imageNum2)
        editAnswer = findViewById(R.id.editAnswer)
        textMessage = findViewById(R.id.textMessage)
        val btnReset: Button = findViewById(R.id.btnReset)
        val btnResult: Button = findViewById(R.id.btnResult)

        loadScore()
        newQuestion()

        // 정답확인하기: 채점 후 문제 수/정답 수를 공유 환경 설정 파일에 저장
        btnResult.setOnClickListener {
            val input = editAnswer.text.toString()
            total++
            if (input.isNotEmpty() && input.toInt() == answer) {
                correct++
                imageBg.setImageResource(R.drawable.bg_ok)
                Toast.makeText(this, "맞았습니다", Toast.LENGTH_SHORT).show()
            } else {
                imageBg.setImageResource(R.drawable.bg_no)
                Toast.makeText(this, "틀렸습니다", Toast.LENGTH_SHORT).show()
            }
            getSharedPreferences("gamefile", Context.MODE_PRIVATE).edit()
                .putInt("total", total)
                .putInt("correct", correct)
                .apply()
            loadScore()
            editAnswer.text.clear()
            newQuestion()
        }

        // 다시시작하기: 공유 환경 설정 파일의 데이터 삭제
        btnReset.setOnClickListener {
            getSharedPreferences("gamefile", Context.MODE_PRIVATE).edit().clear().apply()
            imageBg.setImageResource(R.drawable.bg_quiz)
            editAnswer.text.clear()
            loadScore()
            newQuestion()
        }
    }

    // 0~9 사이 두 수로 곱셈 문제 출제
    fun newQuestion() {
        val a = Random.nextInt(10)
        val b = Random.nextInt(10)
        answer = a * b
        imageNum1.setImageResource(numImages[a])
        imageNum2.setImageResource(numImages[b])
    }

    // 공유 환경 설정 파일에서 점수를 읽어 정답률 출력
    fun loadScore() {
        val pref = getSharedPreferences("gamefile", Context.MODE_PRIVATE)
        total = pref.getInt("total", 0)
        correct = pref.getInt("correct", 0)
        textMessage.text = if (total > 0) {
            "당신의 정답률은 %.2f(%d/%d) %% 입니다.".format(correct * 100.0 / total, correct, total)
        } else {
            "당신의 정답률은 0% 입니다."
        }
    }
}
