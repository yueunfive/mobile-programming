package com.example.mp0503

import android.os.Bundle
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit	var	rbObj1:	RadioButton
    lateinit	var	rbObj2:	RadioButton
    lateinit	var	rbObj3:	RadioButton
    lateinit	var	rbObj4:	RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rbObj1=findViewById(R.id.radioButton1)
        rbObj2=findViewById(R.id.radioButton2)
        rbObj3=findViewById(R.id.radioButton3)
        rbObj4=findViewById(R.id.radioButton4)

        val	listener = CompoundButton.OnCheckedChangeListener{ buttonView, isChecked	->
            // 선택이 해제될 때도 호출되므로 선택된 경우에만 정답 여부를 출력
            if (isChecked) {
                if (buttonView.id == R.id.radioButton2) {
                    Toast.makeText(applicationContext, "맞았습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "틀렸습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
        rbObj1.setOnCheckedChangeListener(listener)
        rbObj2.setOnCheckedChangeListener(listener)
        rbObj3.setOnCheckedChangeListener(listener)
        rbObj4.setOnCheckedChangeListener(listener)
    }
}
