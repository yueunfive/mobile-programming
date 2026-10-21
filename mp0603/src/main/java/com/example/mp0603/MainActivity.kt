package com.example.mp0603

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    // 액티비티 결과를 처리할 ActivityResultLauncher 객체
    lateinit var launcher: ActivityResultLauncher<Intent>

    // 도형 리소스 ID 배열
    val	drawableId = intArrayOf(
        R.drawable.shape01, R.drawable.shape02, R.drawable.shape03,
        R.drawable.shape04, R.drawable.shape05, R.drawable.shape06,
        R.drawable.shape07, R.drawable.shape08, R.drawable.shape09
    )
    // 이미지 뷰 ID 배열
    val	imageviewId = intArrayOf(
        R.id.image01, R.id.image02, R.id.image03
    )
    lateinit var rdObj1: RadioButton
    lateinit var rdObj2: RadioButton
    // 선택된 도형을 표시하기 위한 이미지 뷰 객체 배열
    var	ivObj : Array<ImageView?> = arrayOfNulls<ImageView>(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rdObj1 = findViewById(R.id.radioButton1)
        rdObj2 = findViewById(R.id.radioButton2)
        for (i in 0..2)
            ivObj[i] = findViewById(imageviewId[i])
        val	btnObj : Button = findViewById(R.id.button)

        btnObj.setOnClickListener {
            // 선택된 도형에 따라 게임 액티비티로 전달할 인텐트 생성
            val	intent = Intent(applicationContext,	GameActivity::class.java)
            // 원을 선택한 경우
            if (rdObj1.isChecked == true)
                intent.putExtra("shape",	1)
            // 사각형을 선택한 경우
            else if (rdObj2.isChecked == true)
                intent.putExtra("shape",	2)
            // 게임 액티비티로 인텐트를 실행
            launcher.launch(intent)
        }
        // 액티비티 결과를 처리할 ActivityResultLauncher 객체 초기화
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            // 게임 액티비티에서 반환된 결과 처리
            if (result.resultCode == Activity.RESULT_OK) {
                // 선택된 이미지의 상태를 가져오기
                var	resId = result.data!!.getIntArrayExtra("selectNum")!!

                var	num = 0
                // 선택된 이미지를 레이아웃에 추가
                for (i in 0..8) {
                    if (resId[i] >= 1) {
                        ivObj[num]?.setImageResource(drawableId[i])
                        num++
                    }
                }
                var	flag = true
                // 선택된 이미지의 정답 여부를 확인
                for (i in 0..8) {
                    // 원을 선택한 경우
                    if (rdObj1.isChecked) {
                        if (resId[i] == 1 && i > 2)	flag = false
                    }
                    // 사각형을 선택한 경우
                    else if(rdObj2.isChecked) {
                        if  ((resId[i]  ==  1  &&  i  <=  2) || (resId[i]  ==  1  && i >= 6)) flag = false
                    }
                }
                // 결과 레이아웃 및 텍스트 표시
                val	layoutResultOjb : LinearLayout = findViewById(R.id.layoutResult)
                layoutResultOjb.setVisibility(View.VISIBLE)
                val	tvObj : TextView = findViewById(R.id.textViewResult)
                tvObj.setVisibility(View.VISIBLE)
                if (flag) tvObj.setText("맞았습니다")
                else tvObj.setText("틀렸습니다")
            }
        }
    }
}