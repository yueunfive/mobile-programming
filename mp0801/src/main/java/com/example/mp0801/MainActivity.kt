package com.example.mp0801

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    val pizzaNames = arrayOf("마르게리타 피자", "불고기 피자", "양송이 피자", "하와이안 피자")
    val pizzaImages = intArrayOf(
        R.drawable.pizza1, R.drawable.pizza2, R.drawable.pizza3, R.drawable.pizza4
    )
    val toppingNames = arrayOf("양송이", "불고기", "바질", "베이컨", "해물")

    // 현재 선택된 피자 번호와 토핑 체크 상태 (대화상자를 다시 열면 이전 선택이 유지됨)
    var pizzaIndex = 0
    val toppingChecked = BooleanArray(toppingNames.size)

    lateinit var ivBg: ImageView
    lateinit var ivPizza: ImageView
    lateinit var toppingViews: List<ImageView>
    lateinit var tvPizza: TextView
    lateinit var tvTopping: TextView
    lateinit var btnTopping: Button
    lateinit var btnOrder: Button
    lateinit var ivDelivery: ImageView
    lateinit var tvDelivery: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ivBg = findViewById(R.id.imageBg)
        ivPizza = findViewById(R.id.imagePizza)
        toppingViews = listOf(
            findViewById(R.id.topping1), findViewById(R.id.topping2), findViewById(R.id.topping3),
            findViewById(R.id.topping4), findViewById(R.id.topping5)
        )
        tvPizza = findViewById(R.id.textPizza)
        tvTopping = findViewById(R.id.textTopping)
        btnTopping = findViewById(R.id.btnTopping)
        btnOrder = findViewById(R.id.btnOrder)
        ivDelivery = findViewById(R.id.imageDelivery)
        tvDelivery = findViewById(R.id.textDelivery)

        val btnPizza: Button = findViewById(R.id.btnPizza)
        btnPizza.setOnClickListener { showPizzaDialog() }
        btnTopping.setOnClickListener { showToppingDialog() }
        btnOrder.setOnClickListener { showOrderDialog() }
    }

    // 라디오 버튼 목록 대화상자: 피자 하나 선택
    fun showPizzaDialog() {
        var selected = pizzaIndex
        AlertDialog.Builder(this)
            .setIcon(R.drawable.icon_pizza)
            .setTitle("주문할 피자를 선택하세요")
            .setSingleChoiceItems(pizzaNames, pizzaIndex) { _, which ->
                selected = which
                Toast.makeText(this, pizzaNames[which], Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton("예") { _, _ ->
                pizzaIndex = selected
                ivBg.visibility = View.INVISIBLE
                ivPizza.setImageResource(pizzaImages[pizzaIndex])
                ivPizza.visibility = View.VISIBLE
                tvPizza.text = pizzaNames[pizzaIndex]
                // 피자를 새로 고르면 토핑과 배송 상태는 초기화
                toppingChecked.fill(false)
                showToppings()
                hideDelivery()
                btnTopping.isEnabled = true
                btnOrder.isEnabled = true
            }
            .setNegativeButton("아니오", null)
            .show()
    }

    // 체크박스 목록 대화상자: 토핑 여러 개 선택
    fun showToppingDialog() {
        val checked = toppingChecked.copyOf()
        AlertDialog.Builder(this)
            .setIcon(R.drawable.icon_pizza)
            .setTitle("추가할 토핑을 선택하세요")
            .setMultiChoiceItems(toppingNames, checked) { _, which, isChecked ->
                checked[which] = isChecked
                Toast.makeText(this, toppingNames[which], Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton("예") { _, _ ->
                checked.copyInto(toppingChecked)
                showToppings()
                hideDelivery()
            }
            .setNegativeButton("아니오", null)
            .show()
    }

    // 주문 확인 대화상자: '예'를 누르면 배송중 표시
    fun showOrderDialog() {
        AlertDialog.Builder(this)
            .setIcon(R.drawable.icon_pizza)
            .setTitle("주문 확인")
            .setMessage("${tvPizza.text}를 주문하시겠습니까?")
            .setPositiveButton("예") { _, _ ->
                ivDelivery.visibility = View.VISIBLE
                tvDelivery.visibility = View.VISIBLE
            }
            .setNegativeButton("아니오", null)
            .show()
    }

    // 체크된 토핑만 피자 위에 표시하고 토핑 이름을 텍스트로 출력
    fun showToppings() {
        var text = ""
        for (i in toppingNames.indices) {
            if (toppingChecked[i]) {
                toppingViews[i].visibility = View.VISIBLE
                text += toppingNames[i] + " "
            } else {
                toppingViews[i].visibility = View.INVISIBLE
            }
        }
        tvTopping.text = if (text.isEmpty()) "" else "토핑추가 : $text"
    }

    fun hideDelivery() {
        ivDelivery.visibility = View.INVISIBLE
        tvDelivery.visibility = View.INVISIBLE
    }
}
