package com.inlacou.exinputapp.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.inlacou.exinput.utils.extensions.getColorCompat

import com.inlacou.exinputapp.R

class ColorSimpleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {

    private var viewBaseLayoutSurface: View? = null
    private var tvTitle: TextView? = null

    var model: ColorSimpleViewMdl = ColorSimpleViewMdl("", -1)
        set(value) {
            field = value
            populate()
        }

    init {
        this.initialize()
        populate()
    }

    private fun initialize() {
        val rootView = View.inflate(context, R.layout.view_list_color, this)
        initialize(rootView)
    }

    private fun initialize(view: View) {
        viewBaseLayoutSurface = view.findViewById(R.id.view_base_layout_surface)
        tvTitle = view.findViewById(R.id.tv_title)
    }

    private fun populate() {
        tvTitle?.text = model.item
        if(model.color!=-1) viewBaseLayoutSurface?.setBackgroundColor(context.getColorCompat(model.color))
    }
}