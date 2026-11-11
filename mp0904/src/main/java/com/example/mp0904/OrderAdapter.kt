package com.example.mp0904

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// 수량이 바뀌거나 행이 삭제될 때마다 onChanged를 호출해 액티비티가 총계를 다시 계산하게 함
class OrderAdapter(
    private val fruits: ArrayList<Fruit>,
    private val onChanged: () -> Unit
) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageFruit: ImageView = itemView.findViewById(R.id.imageFruit)
        val textPrice: TextView = itemView.findViewById(R.id.textPrice)
        val btnAdd: ImageButton = itemView.findViewById(R.id.btnAdd)
        val btnMinus: ImageButton = itemView.findViewById(R.id.btnMinus)
        val textCount: TextView = itemView.findViewById(R.id.textCount)
        val textSum: TextView = itemView.findViewById(R.id.textSum)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        val holder = ViewHolder(view)

        // 클릭 시점의 실제 위치(adapterPosition)로 해당 과일을 찾음
        holder.btnAdd.setOnClickListener {
            val pos = holder.adapterPosition
            if (pos == RecyclerView.NO_POSITION) return@setOnClickListener
            fruits[pos].count++
            notifyItemChanged(pos)
            onChanged()
        }
        holder.btnMinus.setOnClickListener {
            val pos = holder.adapterPosition
            if (pos == RecyclerView.NO_POSITION || fruits[pos].count == 0) return@setOnClickListener
            fruits[pos].count--
            notifyItemChanged(pos)
            onChanged()
        }
        holder.btnDelete.setOnClickListener {
            val pos = holder.adapterPosition
            if (pos == RecyclerView.NO_POSITION) return@setOnClickListener
            fruits.removeAt(pos)
            notifyItemRemoved(pos)
            onChanged()
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit = fruits[position]
        holder.imageFruit.setImageResource(fruit.image)
        holder.textPrice.text = fruit.price.toString()
        holder.textCount.text = "x " + fruit.count
        holder.textSum.text = fruit.sum.toString()
    }

    override fun getItemCount(): Int = fruits.size
}
