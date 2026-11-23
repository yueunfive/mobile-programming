package com.example.mp1004

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AccountAdapter(var items: ArrayList<Account>) :
    RecyclerView.Adapter<AccountAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textId: TextView = view.findViewById(R.id.textId)
        val textType: TextView = view.findViewById(R.id.textType)
        val textUsage: TextView = view.findViewById(R.id.textUsage)
        val textDate: TextView = view.findViewById(R.id.textDate)
        val textAmount: TextView = view.findViewById(R.id.textAmount)
        val textMemo: TextView = view.findViewById(R.id.textMemo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.textId.text = item.id.toString()
        holder.textType.text = item.type
        holder.textUsage.text = item.usage
        holder.textDate.text = item.date
        holder.textAmount.text = item.amount.toString()
        holder.textMemo.text = item.memo
    }

    override fun getItemCount() = items.size

    // 검색 결과로 목록 교체
    fun updateItems(newItems: ArrayList<Account>) {
        items = newItems
        notifyDataSetChanged()
    }
}
