package com.example.mp0903

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val cats = arrayListOf(
            MyItem("아비시니안", R.drawable.abyssinian),
            MyItem("샴", R.drawable.siamese),
            MyItem("앙고라", R.drawable.angora),
            MyItem("랙돌", R.drawable.ragdoll),
            MyItem("뱅갈", R.drawable.bengal),
            MyItem("스핑크스", R.drawable.sphinx),
            MyItem("페르시안", R.drawable.persian)
        )

        val spinner: Spinner = findViewById(R.id.spinner)
        val imageCat: ImageView = findViewById(R.id.imageCat)
        spinner.adapter = MyAdapter(cats)

        // 처음 표시될 때 호출되는 첫 항목 선택은 토스트 없이 그림만 출력
        var firstSelect = true
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                imageCat.setImageResource(cats[position].image)
                if (firstSelect) {
                    firstSelect = false
                } else {
                    Toast.makeText(this@MainActivity, cats[position].name, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
}
