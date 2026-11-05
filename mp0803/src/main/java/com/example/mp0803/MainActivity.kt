package com.example.mp0803

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.round

class MainActivity : AppCompatActivity() {
    lateinit var tvExpression: TextView
    lateinit var tvInput: TextView

    // 연산자 앞의 숫자(또는 직전 계산 결과)와 선택된 연산자
    var firstNum: Double? = null
    var operator: String? = null
    // true면 다음 숫자 입력 시 입력 칸을 새로 시작
    var startNew = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnCalculator: Button = findViewById(R.id.btnCalculator)
        btnCalculator.setOnClickListener { showCalculatorDialog() }
    }

    // 사용자 정의 레이아웃으로 계산기 대화상자 표시
    fun showCalculatorDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_calculator, null)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .show()

        tvExpression = dialogView.findViewById(R.id.textExpression)
        tvInput = dialogView.findViewById(R.id.textInput)
        allClear()

        val numButtons = intArrayOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )
        for (i in numButtons.indices) {
            dialogView.findViewById<View>(numButtons[i]).setOnClickListener { inputNumber(i) }
        }
        dialogView.findViewById<View>(R.id.btnPlus).setOnClickListener { inputOperator("+") }
        dialogView.findViewById<View>(R.id.btnSubtract).setOnClickListener { inputOperator("-") }
        dialogView.findViewById<View>(R.id.btnMultiply).setOnClickListener { inputOperator("*") }
        dialogView.findViewById<View>(R.id.btnDivide).setOnClickListener { inputOperator("/") }
        dialogView.findViewById<View>(R.id.btnEqual).setOnClickListener { calculateResult() }
        dialogView.findViewById<View>(R.id.btnDot).setOnClickListener { inputDot() }
        dialogView.findViewById<View>(R.id.btnBack).setOnClickListener { deleteLast() }
        dialogView.findViewById<View>(R.id.btnClear).setOnClickListener { tvInput.text = "" }
        dialogView.findViewById<View>(R.id.btnAllClear).setOnClickListener { allClear() }
        dialogView.findViewById<Button>(R.id.btnClose).setOnClickListener { dialog.dismiss() }
    }

    fun inputNumber(num: Int) {
        if (startNew || tvInput.text.toString() == "0") {
            tvInput.text = ""
            startNew = false
        }
        tvInput.append(num.toString())
    }

    fun inputDot() {
        if (startNew || tvInput.text.isEmpty()) {
            tvInput.text = "0."
            startNew = false
        } else if (!tvInput.text.contains(".")) {
            tvInput.append(".")
        }
    }

    fun deleteLast() {
        val str = tvInput.text.toString()
        if (!startNew && str.isNotEmpty()) {
            tvInput.text = str.dropLast(1)
        }
    }

    fun allClear() {
        tvExpression.text = ""
        tvInput.text = ""
        firstNum = null
        operator = null
        startNew = true
    }

    // 연산자를 누르면 앞의 숫자를 저장하고, 이미 연산자가 있으면 지금까지의 결과로 이어서 계산
    fun inputOperator(op: String) {
        val input = tvInput.text.toString().toDoubleOrNull()
        if (!startNew && input != null) {
            val prev = firstNum
            val prevOp = operator
            firstNum = if (prev != null && prevOp != null) calculate(prev, prevOp, input) else input
        }
        val first = firstNum ?: return
        operator = op
        tvExpression.text = "${format(first)} $op"
        startNew = true
    }

    // '=' 버튼: 위 칸에 계산식과 결과를 출력하고, 결과는 다음 연산의 앞 숫자로 사용
    fun calculateResult() {
        val first = firstNum ?: return
        val op = operator ?: return
        val second = tvInput.text.toString().toDoubleOrNull() ?: return
        if (startNew) return
        if (op == "/" && second == 0.0) {
            Toast.makeText(this, "0으로 나눌 수 없습니다", Toast.LENGTH_SHORT).show()
            return
        }
        val result = calculate(first, op, second)
        tvExpression.text = "${format(first)} $op ${format(second)} = ${format(result)}"
        firstNum = result
        operator = null
        startNew = true
    }

    fun calculate(a: Double, op: String, b: Double): Double {
        val result = when (op) {
            "+" -> a + b
            "-" -> a - b
            "*" -> a * b
            else -> a / b
        }
        // 소수점 아래 9자리까지만 표시
        return round(result * 1000000000) / 1000000000
    }

    // 정수면 소수점 없이 표시
    fun format(num: Double): String {
        return if (num % 1.0 == 0.0) num.toLong().toString() else num.toString()
    }
}
