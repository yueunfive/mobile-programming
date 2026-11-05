package com.example.mp0802

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    val questionImages = intArrayOf(
        R.drawable.question1, R.drawable.question2, R.drawable.question3,
        R.drawable.question4, R.drawable.question5
    )
    // 문제별 정답 시간 그림 (오전/오후)
    val answerAmImages = intArrayOf(
        R.drawable.answer11, R.drawable.answer12, R.drawable.answer13,
        R.drawable.answer14, R.drawable.answer15
    )
    val answerPmImages = intArrayOf(
        R.drawable.answer21, R.drawable.answer22, R.drawable.answer23,
        R.drawable.answer24, R.drawable.answer25
    )
    // 문제별 정답 시각 (오전 기준 시, 분)
    val answerHours = intArrayOf(9, 2, 10, 11, 3)
    val answerMinutes = intArrayOf(15, 25, 35, 40, 10)

    var question = 0
    var isPm = false

    lateinit var ivQuestion: ImageView
    lateinit var ivAnswer: ImageView
    lateinit var ivAmPm: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ivQuestion = findViewById(R.id.imageQuestion)
        ivAnswer = findViewById(R.id.imageAnswer)
        ivAmPm = findViewById(R.id.imageAmPm)

        newQuestion()

        val btnTime: Button = findViewById(R.id.btnTime)
        btnTime.setOnClickListener { showTimePicker() }

        val btnRestart: Button = findViewById(R.id.btnRestart)
        btnRestart.setOnClickListener { newQuestion() }
    }

    // 시계 문제와 오전/오후를 무작위로 골라 표시
    fun newQuestion() {
        question = Random.nextInt(questionImages.size)
        isPm = Random.nextBoolean()
        ivQuestion.setImageResource(questionImages[question])
        ivAmPm.setImageResource(if (isPm) R.drawable.time_pm else R.drawable.time_am)
        ivAnswer.visibility = View.INVISIBLE
    }

    // 테마를 적용한 24시간 형식의 시간 선택 대화상자 표시
    fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val listener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            // 오후 문제는 12를 더한 시각이 정답
            val answerHour = if (isPm) answerHours[question] + 12 else answerHours[question]
            if (hourOfDay == answerHour && minute == answerMinutes[question]) {
                ivAnswer.setImageResource(
                    if (isPm) answerPmImages[question] else answerAmImages[question]
                )
                ivAnswer.visibility = View.VISIBLE
                Toast.makeText(this, "맞았습니다", Toast.LENGTH_SHORT).show()
            } else {
                ivAnswer.visibility = View.INVISIBLE
                Toast.makeText(this, "틀렸습니다", Toast.LENGTH_SHORT).show()
            }
        }
        TimePickerDialog(
            this, R.style.TimePickerTheme, listener,
            calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true
        ).show()
    }
}
