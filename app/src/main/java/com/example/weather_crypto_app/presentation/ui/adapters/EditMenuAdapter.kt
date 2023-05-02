package com.example.weather_crypto_app.presentation.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.data.db.dbMenu.DbMenu
import com.example.weather_crypto_app.databinding.EditMenuItemLayoutBinding
import com.example.weather_crypto_app.presentation.ui.viewholders.EditMenuViewHolder

class EditMenuAdapter(private val menuList: MutableList<DbMenu>): RecyclerView.Adapter<EditMenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditMenuViewHolder {
        val binding = EditMenuItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EditMenuViewHolder(binding)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: EditMenuViewHolder, position: Int) {
        with(holder) {
            with(menuList[position]) {
                binding.nameEditMenu.text = this.MenuName
            }
        }
        holder.itemView.setOnTouchListener { _, event ->
            if(event.actionMasked == MotionEvent.ACTION_DOWN) {
                itemTouchHelper?.startDrag(holder)
            }
            false
        }
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    fun getItems(): MutableList<DbMenu> {
        return menuList
    }

    fun moveItem(from: Int, to: Int) {
        val item = menuList.removeAt(from)
        menuList.add(to, item)
        notifyItemMoved(from, to)
    }

    private var itemTouchHelper: ItemTouchHelper? = null

    fun setItemTouchHelper(itemTouchHelper: ItemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper
    }

}