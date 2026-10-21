package com.example.mp0602

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible

class GameActivity : AppCompatActivity() {
    // 고양이 이미지 ID 배열(각각 이미지 뷰를 나타냄)
    var	imageviewCatId	=	intArrayOf(
        R.id.ivCheck11, R.id.ivCheck12, R.id.ivCheck13,
        R.id.ivCheck14, R.id.ivCheck15
    )
    // 개 이미지 ID 배열(각각 이미지 뷰를 나타냄)
    var	imageviewDogId	=	intArrayOf(
        R.id.ivCheck21, R.id.ivCheck22, R.id.ivCheck23,
        R.id.ivCheck24, R.id.ivCheck25
    )
    // 선택된 이미지를 저장할 ImageView 객체 배열
    var	checkObj : Array<ImageView?> = arrayOfNulls<ImageView>(5)
    var	mImage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.game))	{	v,	insets	->
            val	systemBars	=	insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left,	systemBars.top,	systemBars.right,	systemBars.bottom)
            insets
        }

        // 인텐트로부터 'image' 값을 받아오기(고양이 or 개)
        mImage = intent.getIntExtra("image", 0)

        // 고양이와 개 레이아웃을 설정
        val	imageObj01 : ImageView = findViewById(R.id.imageView01)
        val	imageObj02 : ImageView = findViewById(R.id.imageView02)
        val	catLayout : ConstraintLayout = findViewById(R.id.catLayout)
        val	dogLayout : ConstraintLayout = findViewById(R.id.dogLayout)

        // 고양이 이미지가 선택된 경우
        if	(mImage == 1)	{
            imageObj01.setImageResource(R.drawable.cat01)
            imageObj02.setImageResource(R.drawable.cat02)
            for	(i	in	0..4)	{
                checkObj[i]	=	findViewById(imageviewCatId[i])
            }
            catLayout.isVisible	=	true
            dogLayout.isVisible	=	false
        }
        // 개 이미지가 선택된 경우
        else if	(mImage == 2)	{
            imageObj01.setImageResource(R.drawable.dog01)
            imageObj02.setImageResource(R.drawable.dog02)
            for	(i	in	0..4)	{
                checkObj[i]	=	findViewById(imageviewDogId[i])
            }
            catLayout.isVisible	=	false
            dogLayout.isVisible	=	true
        }

        var num = 0
        for (i in 0..4)	{
            checkObj[i]?.setOnClickListener{
                if	(num	<5)	{
                    num++
                    checkObj[i]?.setImageResource(R.drawable.ok_select)
                    Toast.makeText(applicationContext,	"맞았습니다",
                        Toast.LENGTH_SHORT).show()
                }
                // 5개 모두 맞힌 경우
                if(num	==	5)	Toast.makeText(applicationContext,	"5개 모두 선택했습니다.",
                    Toast.LENGTH_SHORT).show()
            }
        }

        // 첫 번째 이미지 뷰 클릭 시
        imageObj01.setOnClickListener{
            Toast.makeText(applicationContext,	"틀렸습니다.",	Toast.LENGTH_SHORT).show()
        }

        // 종료 버튼 클릭 시
        val	button	: Button =	findViewById(R.id.button)
        button.setOnClickListener	{
            finish()
        }
    }
}