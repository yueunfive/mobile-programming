package com.example.mp0703

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    // 크기가 작은 동물부터 큰 동물 순서의 이미지
    val animalImages = intArrayOf(
        R.drawable.image1, R.drawable.image2, R.drawable.image3,
        R.drawable.image4, R.drawable.image5, R.drawable.image6
    )

    lateinit var animalViews: List<ImageView>
    lateinit var ivArrow: ImageView
    lateinit var rightSpace: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        animalViews = listOf(
            findViewById(R.id.animal1), findViewById(R.id.animal2), findViewById(R.id.animal3),
            findViewById(R.id.animal4), findViewById(R.id.animal5), findViewById(R.id.animal6)
        )
        ivArrow = findViewById(R.id.imageArrow)
        rightSpace = findViewById(R.id.rightSpace)

        // 처음에는 동물 순서를 무작위로 섞어서 표시
        showAnimals(animalImages.toList().shuffled())

        val btnSort: Button = findViewById(R.id.btnSort)
        btnSort.setOnClickListener {
            val popupMenu = PopupMenu(this, btnSort)
            popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menuAscending -> {
                        showSorted(animalImages.toList(), R.drawable.bg_small2big)
                        Toast.makeText(this, "오름차순 정렬입니다", Toast.LENGTH_SHORT).show()
                    }
                    R.id.menuDescending -> {
                        showSorted(animalImages.reversed(), R.drawable.bg_big2small)
                        Toast.makeText(this, "내림차순 정렬입니다", Toast.LENGTH_SHORT).show()
                    }
                }
                true
            }
            popupMenu.show()
        }
    }

    fun showAnimals(images: List<Int>) {
        for (i in animalViews.indices) {
            animalViews[i].setImageResource(images[i])
        }
    }

    // 정렬된 순서로 표시하고 왼쪽에 정렬 방향 화살표 표시 (오른쪽 여백은 없애서 목록을 넓힘)
    fun showSorted(images: List<Int>, arrow: Int) {
        showAnimals(images)
        ivArrow.setImageResource(arrow)
        ivArrow.visibility = View.VISIBLE
        rightSpace.visibility = View.GONE
    }
}
