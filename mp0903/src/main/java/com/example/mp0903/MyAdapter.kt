package com.example.mp0903

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class MyAdapter(private val items: ArrayList<MyItem>) : BaseAdapter() {
    override fun getCount(): Int = items.size

    override fun getItem(position: Int): MyItem = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    // 스피너에 선택된 항목과 드롭다운 목록 모두 spinner_item.xml로 표시
    // (BaseAdapter의 getDropDownView()는 기본적으로 getView()를 그대로 사용)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView
            ?: LayoutInflater.from(parent.context).inflate(R.layout.spinner_item, parent, false)
        val item = items[position]

        view.findViewById<TextView>(R.id.textName).text = item.name
        view.findViewById<ImageView>(R.id.imageIcon).setImageResource(item.image)
        return view
    }
}
