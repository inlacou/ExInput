package com.inlacou.exinput.spinner.popups

import android.content.Context
import android.widget.AdapterView
import android.widget.ListAdapter
import android.widget.ListView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.inlacou.exinput.spinner.MaterialSpinner
import com.inlacou.exinput.spinner.SpinnerPopup

internal class BottomSheetPopup(
    val context: Context,
    private var prompt: CharSequence? = null,
    private val spinner: MaterialSpinner
) : SpinnerPopup {

    private var popup: BottomSheetDialog? = null
    private var adapter: ListAdapter? = null
    private var listener: SpinnerPopup.OnDismissListener? = null

    override fun setAdapter(adapter: ListAdapter?) {
        this.adapter = adapter
    }

    override fun setPromptText(hintText: CharSequence?) {
        prompt = hintText
    }

    override fun getPrompt(): CharSequence? {
        return prompt
    }

    override fun show(position: Int) {
        if (adapter == null) {
            return
        }

        popup = BottomSheetDialog(context).apply {
            prompt?.let { prompt ->
                setTitle(prompt)
            }
            setContentView(ListView(context).apply {
                adapter = this@BottomSheetPopup.adapter

                onItemClickListener =
                    AdapterView.OnItemClickListener { parent, v, position, id ->
                        spinner.selected = position
                        onItemClickListener?.let {
                            spinner.performItemClick(
                                v,
                                position,
                                adapter?.getItemId(position) ?: 0L
                            )
                        }
                        dismiss()
                    }
            })
            setOnDismissListener { listener?.onDismiss() }
        }.also {
            it.show()
        }
    }

    override fun setOnDismissListener(listener: SpinnerPopup.OnDismissListener?) {
        this.listener = listener
    }

    override fun getItem(position: Int): Any? {
        return adapter?.getItem(position)
    }

    override fun getItemId(position: Int): Long {
        return adapter?.getItemId(position) ?: -1
    }

    override fun isShowing() = popup?.isShowing == true
}