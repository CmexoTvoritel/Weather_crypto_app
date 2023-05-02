package com.example.weather_crypto_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.data.db.dbMenu.DbMenu
import com.example.weather_crypto_app.data.db.dbMenu.MenuViewModel
import com.example.weather_crypto_app.presentation.ui.adapters.EditMenuAdapter

class EditMenu : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var menuViewModel: MenuViewModel
    private lateinit var adapter: EditMenuAdapter
    private var itemTouchHelper: ItemTouchHelper? = null
    private lateinit var toolbar: Toolbar


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rv_edit_menu)

        toolbar = (activity as AppCompatActivity).findViewById(R.id.toolbar)
        val editItem = toolbar.menu.findItem(R.id.edit_button)

        val dbMenuList = mutableListOf<DbMenu>()
        menuViewModel = ViewModelProvider(this)[MenuViewModel::class.java]
        menuViewModel.readAllData.observe(viewLifecycleOwner) { menu ->
            menu.forEach { dbMenuList.add(it) }
            adapter = EditMenuAdapter(dbMenuList)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
        }


        itemTouchHelper =
            ItemTouchHelper(object: ItemTouchHelper.SimpleCallback (
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
                ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                adapter.moveItem(viewHolder.adapterPosition, target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            }
        })
        itemTouchHelper?.attachToRecyclerView(recyclerView)

        editItem.setOnMenuItemClickListener {
            val editAdapter = recyclerView.adapter as EditMenuAdapter
            var num = 1
            val dbList = editAdapter.getItems()

            dbList.forEach {
                menuViewModel.updateMenu(DbMenu(num, it.MenuName))
                num++
            }
            findNavController().navigate(R.id.mainMenu)
            return@setOnMenuItemClickListener true
        }
    }

}