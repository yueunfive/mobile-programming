package com.example.mp0403

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var ivObjCrown: ImageView
    lateinit var ivObjNecklace: ImageView
    lateinit var ivObjDress: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ivObjCrown = findViewById(R.id.ivObjCrown)
        ivObjNecklace = findViewById(R.id.ivObjNecklace)
        ivObjDress = findViewById(R.id.ivObjDress)
    }

    // 아이템을 선택했을 때 호출되는 메서드
    fun onClickChoice(view: View) {
        when (view.id) {
            // 드레스를 선택한 경우
            R.id.imageViewDress1 -> {
                ivObjDress.visibility = View.VISIBLE
                ivObjDress.setImageResource(R.drawable.dress1)
            }
            R.id.imageViewDress2 -> {
                ivObjDress.visibility = View.VISIBLE
                ivObjDress.setImageResource(R.drawable.dress2)
            }
            R.id.imageViewDress3 -> {
                ivObjDress.visibility = View.VISIBLE
                ivObjDress.setImageResource(R.drawable.dress3)
            }

            // 왕관을 선택한 경우
            R.id.imageViewCrown1 -> {
                ivObjCrown.visibility = View.VISIBLE
                ivObjCrown.setImageResource(R.drawable.crown1)
            }
            R.id.imageViewCrown2 -> {
                ivObjCrown.visibility = View.VISIBLE
                ivObjCrown.setImageResource(R.drawable.crown2)
            }

            // 목걸이를 선택한 경우
            R.id.imageViewNecklace1 -> {
                ivObjNecklace.visibility = View.VISIBLE
                ivObjNecklace.setImageResource(R.drawable.necklace1)
            }
            R.id.imageViewNecklace2 -> {
                ivObjNecklace.visibility = View.VISIBLE
                ivObjNecklace.setImageResource(R.drawable.necklace2)
            }

            // 다시하기를 선택한 경우 모든 아이템을 숨겨 초기화
            R.id.buttonReset -> {
                ivObjCrown.visibility = View.INVISIBLE
                ivObjNecklace.visibility = View.INVISIBLE
                ivObjDress.visibility = View.INVISIBLE
            }
        }
    }
}
