package com.inlacou.exinputapp.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.inlacou.exinputapp.ui.views.ColorSimpleView
import com.inlacou.exinputapp.ui.views.ColorSimpleViewMdl

class ColorArrayAdapter(
    private val context: Context, //Context
    private val items: List<ColorSimpleViewMdl> //Item list
): BaseAdapter() {
    override fun getItemId(position: Int): Long = position.toLong()
    override fun getCount(): Int = items.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return ColorSimpleView(context).apply {
            model = items[position]
        }
    }

    override fun getItem(position: Int): ColorSimpleViewMdl {
        return items[position]
    }
}