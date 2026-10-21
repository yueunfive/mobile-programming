package com.example.mp0603

import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GameActivity : AppCompatActivity() {
    // 이미지 뷰를 나타내는 ID 배열
    var	imageviewId = intArrayOf(
        R.id.image01, R.id.image02, R.id.image03,
        R.id.image04, R.id.image05, R.id.image06,
        R.id.image07, R.id.image08, R.id.image09
    )
    // 프레임 레이아웃을 나타내는 ID 배열
    var	framelayoutId = intArrayOf(
        R.id.frame_layout01, R.id.frame_layout02, R.id.frame_layout03,
        R.id.frame_layout04, R.id.frame_layout05, R.id.frame_layout06,
        R.id.frame_layout07, R.id.frame_layout08, R.id.frame_layout09
    )
    var	ivObj : Array<ImageView?> = arrayOfNulls<ImageView>(9)
    var	flayoutObj : Array<FrameLayout?> = arrayOfNulls<FrameLayout>(9)

    // 이미지 배열 및 선택 여부를 기록하는 배열
    var	imgArray : Array<ImageView?> = arrayOfNulls<ImageView>(9)
    var	selectNum = IntArray(9)
    var	mShape = 0 // 액티비티에서 전송받은 도형 타입

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.game)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Intent로 전달된 데이터를 받아오기
        mShape = intent.getIntExtra("shape", 0)
        val	shapeObj : ImageView = findViewById(R.id.imageView)

        // 도형에 맞는 이미지를 설정
        if (mShape == 1)
            shapeObj.setImageResource(R.drawable.circle)
        else if (mShape == 2)
            shapeObj.setImageResource(R.drawable.square)

        // 이미지 뷰와 프레임 레이아웃 객체 초기화
        for (i in 0..8) {
            selectNum[i] = 0
            ivObj[i] = findViewById(imageviewId[i])
            flayoutObj[i] = findViewById(framelayoutId[i])
        }
        var	num	=	0

        // 각 이미지 뷰 클릭 리스너 설정
        for (i in	0..8)	{
            ivObj[i]?.setOnClickListener {
                // 이미지를 이미 선택한 경우
                if (selectNum[i] == 1) {
                    selectNum[i] = 0
                    --num
                    flayoutObj[i]?.removeView(imgArray[i])
                }
                // 이미지를 선택하지 않은 경우
                else {
                    if (num < 3) {
                        selectNum[i] = 1
                        num++
                        imgArray[i] = ImageView(this);
                        imgArray[i]?.setImageResource(R.drawable.ok_check)
                        flayoutObj[i]?.addView(imgArray[i])
                    } else Toast.makeText(
                        SubActivity@this,
                        "3개 까지만 선택 가능합니다",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // 완료 버튼 클릭 리스너 설정
        val	btnObj: Button = findViewById(R.id.button)
        btnObj.setOnClickListener {
            // retutn intent
            intent.putExtra("selectNum", selectNum)

            setResult(RESULT_OK, intent) //결과	전달
            finish() //현재	액티비티	종료
        }

    }
}