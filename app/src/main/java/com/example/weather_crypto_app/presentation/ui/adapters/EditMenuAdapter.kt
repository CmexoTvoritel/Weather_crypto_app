package com.example.weather_crypto_app.presentation.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.R
import com.example.weather_crypto_app.data.db.dbMenu.DbMenu
import com.example.weather_crypto_app.presentation.ui.viewholders.EditMenuViewHolder
import kotlinx.android.synthetic.main.edit_menu_item_layout.view.*

class EditMenuAdapter(val menuList: MutableList<DbMenu>): RecyclerView.Adapter<EditMenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditMenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.edit_menu_item_layout, parent, false)
        return EditMenuViewHolder(view)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: EditMenuViewHolder, position: Int) {
        val item = menuList[position]
        holder.itemView.name_edit_menu.text = menuList[position].MenuName
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