package com.example.mp0904

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    val fruits = arrayListOf(
        Fruit(R.drawable.fruit1, 30),
        Fruit(R.drawable.fruit2, 40),
        Fruit(R.drawable.fruit3, 60),
        Fruit(R.drawable.fruit4, 80),
        Fruit(R.drawable.fruit5, 100)
    )
    lateinit var textTotal: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textTotal = findViewById(R.id.textTotal)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = OrderAdapter(fruits) { updateTotal() }
        updateTotal()
    }

    // 남아 있는 모든 과일의 합계를 더해 총계 출력
    fun updateTotal() {
        textTotal.text = fruits.sumOf { it.sum }.toString()
    }
}
