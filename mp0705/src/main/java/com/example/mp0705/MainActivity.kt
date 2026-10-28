package com.example.mp0705

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    // 과일 이미지와 각 과일의 개수 (체리=3, 레몬=6, 사과=8, 바나나=1)
    val fruitImages = intArrayOf(R.drawable.fruit1, R.drawable.fruit2, R.drawable.fruit3, R.drawable.fruit4)
    val fruitCounts = intArrayOf(3, 6, 8, 1)

    lateinit var fruitViews: List<ImageView>
    lateinit var opViews: List<TextView>
    lateinit var editAnswer: EditText

    var answer = 0          // 현재 문제의 정답
    var yesCount = 0
    var noCount = 0
    // 툴바의 카운트 배지 (메뉴가 만들어진 뒤에 연결됨)
    var tvYesCount: TextView? = null
    var tvNoCount: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 레이아웃의 툴바를 액션바로 지정
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        fruitViews = listOf(findViewById(R.id.fruitA), findViewById(R.id.fruitB), findViewById(R.id.fruitC))
        opViews = listOf(findViewById(R.id.opA), findViewById(R.id.opB))
        editAnswer = findViewById(R.id.editAnswer)

        val btnRestart: Button = findViewById(R.id.btnRestart)
        val btnResult: Button = findViewById(R.id.btnResult)

        // 다시 시작하기: 입력을 지우고 새 문제 출제
        btnRestart.setOnClickListener {
            editAnswer.text.clear()
            makeQuestion()
        }
        // 결과보기: 입력한 답을 채점하고 툴바의 정답/오답 횟수 갱신
        btnResult.setOnClickListener {
            val input = editAnswer.text.toString().toIntOrNull()
            when {
                input == null ->
                    Toast.makeText(applicationContext, "답을 입력하세요", Toast.LENGTH_SHORT).show()
                input == answer -> {
                    yesCount++
                    Toast.makeText(applicationContext, "맞았습니다", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    noCount++
                    Toast.makeText(applicationContext, "틀렸습니다", Toast.LENGTH_SHORT).show()
                }
            }
            showCounts()
        }

        makeQuestion()
    }

    // 과일 4개 중 3개를 골라 +, - 를 무작위로 섞은 식을 만들고 정답 계산
    fun makeQuestion() {
        val picked = fruitImages.indices.shuffled().take(3)
        for (i in fruitViews.indices) {
            fruitViews[i].setImageResource(fruitImages[picked[i]])
        }

        answer = fruitCounts[picked[0]]
        for (i in opViews.indices) {
            val plus = Random.nextBoolean()
            opViews[i].text = if (plus) "+" else "-"
            val count = fruitCounts[picked[i + 1]]
            answer += if (plus) count else -count
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        // actionLayout으로 지정한 정답/오답 레이아웃에서 카운트 TextView 가져오기
        tvYesCount = menu?.findItem(R.id.menuYes)?.actionView?.findViewById(R.id.countYes)
        tvNoCount = menu?.findItem(R.id.menuNo)?.actionView?.findViewById(R.id.countNo)
        showCounts()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // 초기화: 정답/오답 횟수를 0으로
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
