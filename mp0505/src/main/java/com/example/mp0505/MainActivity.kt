package com.example.mp0505

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit	var	sObj1: Switch
    lateinit	var	sObj2:	Switch
    lateinit	var	tvObj: TextView
    lateinit	var	ivObj1: ImageView
    lateinit	var	ivObj2:	ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        sObj1=findViewById(R.id.switch1)
        sObj2=findViewById(R.id.switch2)
        sObj2.textOn = ""
        sObj2.textOff = ""
        tvObj=findViewById(R.id.textView2)
        ivObj1=findViewById(R.id.imageView1)
        ivObj2=findViewById(R.id.imageView2)

        sObj1.setOnCheckedChangeListener{	buttonView,	isChecked->
            // 고양이 스위치: 왼쪽(O)이면 창문에 고양이 표시, 오른쪽(X)이면 숨김
            if (isChecked) {
                ivObj1.visibility = View.INVISIBLE
            } else {
                ivObj1.visibility = View.VISIBLE
            }
        }
        sObj2.setOnCheckedChangeListener{	buttonView,		isChecked->
            // 라이트 스위치: ON이면 회색 막을 걷고 켜진 조명으로, OFF이면 원래대로
            if (isChecked) {
                tvObj.visibility = View.INVISIBLE
                ivObj2.setImageResource(R.drawable.light32)
                sObj2.setThumbResource(R.drawable.on3)
            } else {
                tvObj.visibility = View.VISIBLE
                ivObj2.setImageResource(R.drawable.light31)
                sObj2.setThumbResource(R.drawable.off3)
            }
        }
    }
}
