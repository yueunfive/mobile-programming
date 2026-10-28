package com.example.mp0701

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    // 식에 표시할 숫자 이미지(num0~num9)와 선택지 버튼 이미지(btn0~btn9)
    val numImages = intArrayOf(
        R.drawable.num0, R.drawable.num1, R.drawable.num2, R.drawable.num3, R.drawable.num4,
        R.drawable.num5, R.drawable.num6, R.drawable.num7, R.drawable.num8, R.drawable.num9
    )
    val btnImages = intArrayOf(
        R.drawable.btn0, R.drawable.btn1, R.drawable.btn2, R.drawable.btn3, R.drawable.btn4,
        R.drawable.btn5, R.drawable.btn6, R.drawable.btn7, R.drawable.btn8, R.drawable.btn9
    )

    lateinit var ivA: ImageView
    lateinit var ivOp: ImageView
    lateinit var ivResultTens: ImageView
    lateinit var ivResultOnes: ImageView
    lateinit var choiceButtons: Array<ImageButton>

    var answer = 0                  // ? 자리에 들어갈 정답
    var choices = IntArray(3)       // 버튼 3개에 표시된 숫자

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ivA = findViewById(R.id.imageA)
        ivOp = findViewById(R.id.imageOp)
        ivResultTens = findViewById(R.id.imageResultTens)
        ivResultOnes = findViewById(R.id.imageResultOnes)
        choiceButtons = arrayOf(
            findViewById(R.id.btnChoice1),
            findViewById(R.id.btnChoice2),
            findViewById(R.id.btnChoice3)
        )

        // 선택한 버튼의 숫자가 정답인지 토스트로 출력
        for (i in choiceButtons.indices) {
            choiceButtons[i].setOnClickListener {
                val message = if (choices[i] == answer) "맞았습니다" else "틀렸습니다"
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
            }
        }

        makeQuestion("+")
    }

    // 옵션 메뉴 생성
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    // 선택한 메뉴의 연산자로 새 문제 출제
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuPlus -> makeQuestion("+")
            R.id.menuMinus -> makeQuestion("-")
            R.id.menuMultiply -> makeQuestion("*")
            R.id.menuRemainder -> makeQuestion("%")
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    // 'A 연산자 ? = 결과' 형태의 문제를 만들어 화면에 표시
    fun makeQuestion(op: String) {
        var a: Int
        var result: Int
        var wrongs: List<Int>
        do {
            // 곱하기/나머지는 0이면 정답이 여러 개가 되므로 1~9 사용
            a = if (op == "*" || op == "%") Random.nextInt(1, 10) else Random.nextInt(0, 10)
            answer = if (op == "%") Random.nextInt(1, a + 1) else Random.nextInt(0, 10)
            result = calculate(a, answer, op)
            // 오답 후보: 넣어도 같은 결과가 나오지 않는 숫자만
            wrongs = (0..9).filter { it != answer && !(op == "%" && it == 0) }
                .filter { calculate(a, it, op) != result }
        } while (wrongs.size < 2)

        ivA.setImageResource(numImages[a])
        ivOp.setImageResource(
            when (op) {
                "+" -> R.drawable.op1
                "-" -> R.drawable.op2
                "*" -> R.drawable.op3
                else -> R.drawable.op4
            }
        )
        showResult(result)

        // 정답 1개 + 오답 2개를 섞어 버튼에 배치
        val picked = wrongs.shuffled().take(2)
        choices = (picked + answer).shuffled().toIntArray()
        for (i in choiceButtons.indices) {
            choiceButtons[i].setBackgroundResource(btnImages[choices[i]])
        }
    }

    fun calculate(a: Int, b: Int, op: String): Int = when (op) {
        "+" -> a + b
        "-" -> a - b
        "*" -> a * b
        else -> a % b
    }

    // 결과를 두 자리로 표시 (음수는 앞자리에 '-', 한 자리 수는 앞자리 숨김)
    fun showResult(result: Int) {
        when {
            result < 0 -> {
                ivResultTens.visibility = View.VISIBLE
                ivResultTens.setImageResource(R.drawable.op2)
            }
            result < 10 -> ivResultTens.visibility = View.GONE
            else -> {
                ivResultTens.visibility = View.VISIBLE
                ivResultTens.setImageResource(numImages[result / 10])
            }
        }
        ivResultOnes.setImageResource(numImages[Math.abs(result) % 10])
    }
}
