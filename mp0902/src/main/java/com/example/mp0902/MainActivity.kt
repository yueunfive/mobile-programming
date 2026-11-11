package com.example.mp0902

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var gridView: GridView
    lateinit var adapter: MyAdapter
    val cats = ArrayList<MyItem>()
    // 선택한 항목의 위치 (-1이면 선택 없음)
    var selectedPos = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        cats.add(MyItem("아비시니안", "활발하고 온순한 성격", "3~5kg", R.drawable.abyssinian))
        cats.add(MyItem("샴", "놀기 좋아하고 온순한 성격", "3.6~5.4kg", R.drawable.siamese))
        cats.add(MyItem("앙고라", "예민하고 상냥한 성격", "3.7~7kg", R.drawable.angora))
        cats.add(MyItem("랙돌", "느긋하고 온순한 성격", "5~9kg", R.drawable.ragdoll))
        cats.add(MyItem("뱅갈", "충성심 강하고 상냥한 성격", "5~8kg", R.drawable.bengal))
        cats.add(MyItem("스핑크스", "활발하고 사교성 좋은 성격", "4~5kg", R.drawable.sphinx))
        cats.add(MyItem("페르시안", "조용하고 온순한 성격", "3.6~5.4kg", R.drawable.persian))

        gridView = findViewById(R.id.gridView)
        adapter = MyAdapter(cats)
        gridView.adapter = adapter

        // 항목을 누르면 선택 위치 저장 (choiceMode가 singleChoice라 선택 항목 배경이 회색으로 유지됨)
        gridView.setOnItemClickListener { _, _, position, _ ->
            selectedPos = position
        }

        findViewById<Button>(R.id.btnInsert).setOnClickListener { insertItem() }
        findViewById<Button>(R.id.btnModify).setOnClickListener { modifyItem() }
        findViewById<Button>(R.id.btnDelete).setOnClickListener { deleteItem() }
    }

    // 마지막 행에 러시안블루 항목을 추가하고 추가한 항목을 선택
    fun insertItem() {
        cats.add(MyItem("러시안블루" + (cats.size + 1), "온순하고 애교 많은 성격", "3.5~5kg", R.drawable.russianblue))
        adapter.notifyDataSetChanged()
        selectedPos = cats.size - 1
        gridView.setItemChecked(selectedPos, true)
        gridView.smoothScrollToPosition(selectedPos)
    }

    // 선택한 항목의 몸무게를 대화상자에서 입력받아 변경
    fun modifyItem() {
        if (!checkSelected()) return
        val pos = selectedPos
        val dialogView = layoutInflater.inflate(R.layout.dialog_layout, null)
        val dialog = AlertDialog.Builder(this).setView(dialogView).show()

        dialogView.findViewById<TextView>(R.id.textDialogTitle).text = "고양이 : " + cats[pos].name
        dialogView.findViewById<View>(R.id.layoutModify).visibility = View.VISIBLE
        dialogView.findViewById<View>(R.id.textDeleteMessage).visibility = View.GONE
        val editWeight: EditText = dialogView.findViewById(R.id.editWeight)
        val btnYes: Button = dialogView.findViewById(R.id.btnYes)
        val btnNo: Button = dialogView.findViewById(R.id.btnNo)
        btnYes.text = "수정"
        btnNo.text = "취소"

        btnYes.setOnClickListener {
            val weight = editWeight.text.toString().trim()
            if (weight.isEmpty()) {
                Toast.makeText(this, "몸무게를 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            cats[pos].weight = "몸무게 " + weight + "kg"
            adapter.notifyDataSetChanged()
            dialog.dismiss()
        }
        btnNo.setOnClickListener { dialog.dismiss() }
    }

    // 선택한 항목을 확인 대화상자를 거쳐 삭제
    fun deleteItem() {
        if (!checkSelected()) return
        val pos = selectedPos
        val dialogView = layoutInflater.inflate(R.layout.dialog_layout, null)
        val dialog = AlertDialog.Builder(this).setView(dialogView).show()

        dialogView.findViewById<TextView>(R.id.textDialogTitle).text = "고양이 : " + cats[pos].name
        dialogView.findViewById<View>(R.id.layoutModify).visibility = View.GONE
        dialogView.findViewById<View>(R.id.textDeleteMessage).visibility = View.VISIBLE
        val btnYes: Button = dialogView.findViewById(R.id.btnYes)
        val btnNo: Button = dialogView.findViewById(R.id.btnNo)
        btnYes.text = "예"
        btnNo.text = "아니오"

        btnYes.setOnClickListener {
            cats.removeAt(pos)
            gridView.clearChoices()
            selectedPos = -1
            adapter.notifyDataSetChanged()
            dialog.dismiss()
        }
        btnNo.setOnClickListener { dialog.dismiss() }
    }

    // 선택한 항목이 없으면 안내 토스트 출력
    fun checkSelected(): Boolean {
        if (selectedPos < 0 || selectedPos >= cats.size) {
            Toast.makeText(this, "항목을 선택하세요", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}
