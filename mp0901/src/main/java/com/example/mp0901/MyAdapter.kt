package com.example.mp0901

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

    // 재사용 가능한 뷰(convertView)가 있으면 다시 쓰고, 없으면 listview_item.xml을 인플레이트
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView
            ?: LayoutInflater.from(parent.context).inflate(R.layout.listview_item, parent, false)
        val item = items[position]

        view.findViewById<ImageView>(R.id.imageCat).setImageResource(item.image)
        view.findViewById<TextView>(R.id.textName).text = item.name
        view.findViewById<TextView>(R.id.textWeight).text = item.weight
        view.findViewById<TextView>(R.id.textCharacter).text = item.character
        return view
    }
}
